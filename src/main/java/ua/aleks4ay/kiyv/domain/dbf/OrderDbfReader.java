package ua.aleks4ay.kiyv.domain.dbf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.kiyv.domain.dao.UtilDao;
import ua.aleks4ay.kiyv.domain.model.Order;
import ua.aleks4ay.kiyv.domain.util.TimeConverter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ua.aleks4ay.kiyv.log.ClassNameUtil.getCurrentClassName;

public class OrderDbfReader implements OrderDbf {

    private static Connection connDbf;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String SQL_GET_JOURNAL = "SELECT IDDOC, DOCNO, DATE, TIME " +
            "from 1SJOURN WHERE YEAR (DATE) > 2018 AND IDDOCDEF = ' 1GQ';";
    private static final String SQL_GET_ORDER = "SELECT IDDOC, SP1899, SP14836, SP14695, SP14680, SP14684 " +
            "from DH1898 WHERE SP14694 = 1;";

    public OrderDbfReader() {
        connDbf = UtilDao.getConnDbf();
        log.debug("Get connection to 'dbf-files' 1C from {}.", OrderDbfReader.class);
    }

    @Override
    public List<Order> getAll() {

        long start = System.currentTimeMillis();
        System.out.print("write  'O R D E R S', ");

        Map<String, Order> mapOrder = new HashMap<>();
        List<Order> result = new ArrayList<>();

        try (Statement st = connDbf.createStatement()) {
            ResultSet rs1 = st.executeQuery(SQL_GET_JOURNAL); //IDDOC, DOCNO, DATE, TIME
            log.debug("Select all rows 'Journal' from 1C. SQL = {}.", SQL_GET_JOURNAL);
            while (rs1.next()) {
                String kod = rs1.getString("IDDOC");
                byte[] bytes = rs1.getBytes("DOCNO");
                String docNumber = new String(bytes, "Windows-1251");
                LocalDate dateCreate = rs1.getDate("DATE").toLocalDate();
                LocalTime timeCreate = TimeConverter.convertStrToLocalTime(rs1.getString("TIME"));

                Order order = new Order(kod, docNumber, null, null, null, 0d, 0, dateCreate, timeCreate, null, null);

                mapOrder.put(kod, order);
            }
            log.debug("Was read {} temp rows from 1C Journal.", mapOrder.size());

        } catch (Exception e) {
            log.warn("Exception during reading all rows 'Journal'. SQL = {}.", SQL_GET_JOURNAL, e);
        }

        try (Statement st = connDbf.createStatement()) {
            ResultSet rs2 = st.executeQuery(SQL_GET_ORDER); //SP1899, SP14836, SP14695, SP14680, SP14684
            log.debug("Select all rows 'Journal' from 1C. SQL = {}.", SQL_GET_ORDER);
            while (rs2.next()) {
                String kod = rs2.getString("IDDOC");
                if (mapOrder.containsKey(kod)) {
                    Order order = mapOrder.get(kod);
                    order.setIdClient(rs2.getString("SP1899"));
                    order.setDateToFactory(rs2.getDate("SP14836").toLocalDate());
                    order.setDurationTime(rs2.getInt("SP14695"));
                    order.setIdManager(rs2.getString("SP14680"));
                    double price = rs2.getDouble("SP14684");
                    order.setPrice(price);
                }
            }
            for (Order order : mapOrder.values()) {
                if (order.getIdClient() != null) {
                    result.add(order);
                }
            }
            log.debug("Was filtered {} orders from 1C 'DH1898'.", result.size());

        } catch (Exception e) {
            log.warn("Exception during reading all rows 'DH1898'. SQL = {}.", SQL_GET_ORDER, e);
        }

        long end = System.currentTimeMillis();
        System.out.println("t = " + (double)(end-start)/1000 + " c" );

        return result;
    }
}
