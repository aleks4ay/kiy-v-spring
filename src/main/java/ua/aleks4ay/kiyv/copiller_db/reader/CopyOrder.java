package ua.aleks4ay.kiyv.copiller_db.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.kiyv.domain.dao.OrderDao;
import ua.aleks4ay.kiyv.domain.dao.OrderDaoJdbc;
import ua.aleks4ay.kiyv.domain.dao.UtilDao;
import ua.aleks4ay.kiyv.domain.dbf.OrderDbf;
import ua.aleks4ay.kiyv.domain.dbf.OrderDbfReader;
import ua.aleks4ay.kiyv.domain.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ua.aleks4ay.kiyv.log.ClassNameUtil.getCurrentClassName;

public class CopyOrder {

    private static final CopyOrder copyOrder = new CopyOrder();
    private static final OrderDao orderDao = new OrderDaoJdbc(UtilDao.getConnPostgres());
    private static final OrderDbf orderDbfReader = new OrderDbfReader();
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    private CopyOrder() {
    }

    public static CopyOrder getInstance() {
        return copyOrder;
    }

    public static void main(String[] args) {

        CopyOrder.getInstance().doCopyNewRecord();

    }

    public void doCopyNewRecord() {
        long start = System.currentTimeMillis();
        log.info("Start writing 'O R D E R S'.");

        List<Order> listNewOrder = new ArrayList<>();
        List<Order> listUpdatingOrder = new ArrayList<>();

        Map<String, Order> oldOrder = orderDao.getAll()
                .stream()
                .collect(Collectors.toMap(Order::getId, Order::getOrder));
        List<Order> listOrderFrom1C = orderDbfReader.getAll();

        for (Order order : listOrderFrom1C) {
            String idComparedOrder = order.getId();
            if (!oldOrder.containsKey(idComparedOrder)) {
                listNewOrder.add(order);
            } else if (!oldOrder.get(idComparedOrder).equals(order)) {
                listUpdatingOrder.add(order);
                oldOrder.remove(idComparedOrder);
            }
            else {
                oldOrder.remove(idComparedOrder);
            }
        }

        if (listNewOrder.size() > 0) {
            log.debug("Save to DataBase. Must be added {} new Orders.", listNewOrder.size());
            orderDao.saveAll(listNewOrder);
        }
        if (listUpdatingOrder.size() > 0) {
            log.debug("Write change to DataBase. Must be updated {} Orders.", listUpdatingOrder.size());
            orderDao.updateAll(listUpdatingOrder);
        }
        if (oldOrder.size() > 0) {
            log.debug("Delete old orders from DataBase. Must be deleted {} Orders.", oldOrder.size());
            orderDao.deleteAll(oldOrder.keySet());
        }

        long end = System.currentTimeMillis();
        log.info("End writing 'O R D E R S'. Time = {} c.", (double)(end-start)/1000);
    }
}
