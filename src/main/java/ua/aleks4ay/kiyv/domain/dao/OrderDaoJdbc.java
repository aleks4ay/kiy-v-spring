package ua.aleks4ay.kiyv.domain.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.kiyv.domain.model.Order;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static ua.aleks4ay.kiyv.log.ClassNameUtil.getCurrentClassName;

public class OrderDaoJdbc implements OrderDao {

    private static Connection connPostgres;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String SQL_GET_ONE = "SELECT * FROM orders WHERE id = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM orders;";
    private static final String SQL_DELETE = "DELETE FROM orders WHERE id = ?;";
    private static final String SQL_SAVE = "INSERT INTO orders (docno, client_id, manager_id, designer_id, duration," +
            " d_create, t_create, d_factory, d_end, price, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE = "UPDATE orders SET docno = ?, client_id =?, manager_id=?, designer_id=?, duration=?," +
            "d_create=?, t_create=?, d_factory=?, d_end=?, price=?  WHERE id = ?;";

    public OrderDaoJdbc(Connection conn) {
        connPostgres = conn;
        log.debug("Get connection to PostgreSQL from {}.", UtilDao.class);
    }

    @Override
    public Order getById(String id) {
        try {
            PreparedStatement statement = connPostgres.prepareStatement(SQL_GET_ONE);
            statement.setString(1, id);
            log.debug("Select 'Order'. SQL = {}. id = {}.", SQL_GET_ONE, id);
            ResultSet rs = statement.executeQuery();

            rs.next();
            log.debug("return new 'Order'. id = {}.", id);
            LocalDate dateCreate = rs.getDate("d_create").toLocalDate();
            LocalTime timeCreate = rs.getTime("t_create").toLocalTime();
            LocalDate dateToFactory = rs.getDate("d_factory").toLocalDate();
            LocalDate dateEnd = rs.getDate("d_end").toLocalDate();

            return new Order(id, rs.getString("docno"), rs.getString("manager_id"), rs.getString("designer_id"),
                    rs.getString("client_id"), rs.getDouble("price"), rs.getInt("duration"),
                    dateCreate, timeCreate, dateToFactory, dateEnd);

        } catch (SQLException e) {
            log.warn("Exception during reading 'Order' with id = {}.", id, e);
        }
        log.debug("Order with id = {} not found.", id);
        return null;
    }

    @Override
    public List<Order> getAll() {
        List<Order> result = new ArrayList<>();
        try {
            Statement statement = connPostgres.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            log.debug("Select all 'Orders'. SQL = {}.", SQL_GET_ALL);

            while (rs.next()) {
                String id = rs.getString("id");
                log.debug("return new 'Order'. id = {}.", id);
                LocalDate dateCreate = rs.getDate("d_create").toLocalDate();
                LocalTime timeCreate = rs.getTime("t_create").toLocalTime();
                LocalDate dateToFactory = rs.getDate("d_factory").toLocalDate();
                LocalDate dateEnd = rs.getDate("d_end").toLocalDate();

                Order order = new Order(id, rs.getString("docno"), rs.getString("manager_id"), rs.getString("designer_id"),
                        rs.getString("client_id"), rs.getDouble("price"), rs.getInt("duration"),
                        dateCreate, timeCreate, dateToFactory, dateEnd);

                result.add(order);
            }
            log.debug("Was read {} Orders.", result.size());
            return result;
        } catch (SQLException e) {
            log.warn("Exception during reading all 'Order'.", e);
        }
        log.debug("Orders not found.");
        return null;
    }

    private boolean saveOrUpdateAll(List<Order> orderList, String sql) {
        try {
            int result = 0;
            for (Order order : orderList) {
                PreparedStatement ps = connPostgres.prepareStatement(sql);
                log.debug("Prepared new 'Order' to batch. SQL = {}. Order = {}.", sql, order);

                ps.setString(11, order.getId());
                ps.setString(1, order.getDocNumber());
                ps.setString(2, order.getIdClient());
                ps.setString(3, order.getIdManager());
                ps.setString(4, order.getIdDesigner());

                int duration = order.getDurationTime();
                LocalDate dateCreate = order.getDateCreate();
                LocalDate dateToFactory = order.getDateToFactory();
                Time timeCreate = Time.valueOf(order.getTimeCreate());

                ps.setInt(5, duration);
                ps.setDate(6, Date.valueOf(dateCreate));
                ps.setTime(7, timeCreate);
                ps.setDate(8, Date.valueOf(dateToFactory));
                ps.setDouble(10, order.getPrice());

                LocalDate dateEnd = order.getDateEnd();
                if (dateEnd == null) {
                    LocalDate maximum = dateCreate.isAfter(dateToFactory) ? dateCreate : dateToFactory;
                    dateEnd = maximum.plusDays(duration);
                }
                ps.setDate(9, Date.valueOf(dateEnd));


                ps.addBatch();
                int[] numberOfUpdates = ps.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
            }
            if (result == orderList.size()) {
                log.debug("Try commit");
                connPostgres.commit();
                log.debug("Commit - OK. {} Orders Saved. SQL = {}.", result, sql);
                return true;
            }
            else {
                log.debug("Saved {}, but need to save {} Order. Not equals!!!", result, orderList.size());
                connPostgres.rollback();
            }
        } catch (SQLException e) {
            log.warn("Exception during saving {} new 'Order'. SQL = {}.", orderList.size() , sql, e);
        }
        return false;
    }

    @Override
    public boolean saveAll(List<Order> orderList) {
        return saveOrUpdateAll(orderList, SQL_SAVE);
    }

    @Override
    public boolean updateAll(List<Order> orderList) {
        return saveOrUpdateAll(orderList, SQL_UPDATE);
    }

    @Override
    public boolean deleteAll(Collection<String> orderList) {
        try {
            int result = 0;
            for (String id : orderList) {
                log.debug("Prepared old 'Order' for delete to batch. SQL = {}. Id = {}.", SQL_DELETE, id);
                PreparedStatement ps = connPostgres.prepareStatement(SQL_DELETE);
                ps.setString(1, id);
                ps.addBatch();
                int[] numberOfUpdates = ps.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
            }
            if (result == orderList.size()) {
                log.debug("Try commit");
                connPostgres.commit();
                log.debug("Commit - OK. {} Order updated. SQL = {}.", result, SQL_DELETE);
                return true;
            }
            else {
                connPostgres.rollback();
                log.debug("Deleted {}, but need to delete {} Order. Not equals!!!", result, orderList.size());
            }
        } catch (SQLException e) {
            log.warn("Exception during delete {} old 'Order'. SQL = {}.", orderList.size() , SQL_DELETE, e);
        }
        return false;
    }

}
