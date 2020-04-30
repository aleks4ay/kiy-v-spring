package ua.aleks4ay.kiyv.domain.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import ua.aleks4ay.kiyv.domain.model.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class OrderDaoJdbcTest {

    private static Connection connPostgresTest = UtilDao.getConnTest();
    private OrderDao orderDao = new OrderDaoJdbc(connPostgresTest);

    Order order1, order2, order3, order4;
    List<Order> orderList = new ArrayList<>();

    @Before
    public void setUp() {
        try {
            connPostgresTest.createStatement().executeUpdate("DELETE FROM worker;");
            connPostgresTest.createStatement().executeUpdate("DELETE FROM client;");
            connPostgresTest.createStatement().executeUpdate("DELETE FROM orders;");
            connPostgresTest.createStatement().executeUpdate("INSERT INTO worker(id, name) VALUES ('w_1', 'worker name 1'), " +
                    "('w_2', 'worker name 2'), ('w_3', 'worker name 3'), ('w_4', 'worker name 4');");
            connPostgresTest.createStatement().executeUpdate("INSERT INTO client(id, clientname) VALUES ('cl_1', 'client name 1'), " +
                    "('cl_2', 'client name 2'), ('cl_3', 'client name 3'), ('cl_4', 'client name 4');");
            connPostgresTest.createStatement().executeUpdate("INSERT INTO orders(id, docno, client_id, manager_id, " +
                    "designer_id, duration, d_create, t_create, d_factory, d_end, price) VALUES " +
                    "('o_1', 'KI-000101', 'cl_2', 'w_1', NULL, 10, '2020-5-21', '10:02:00', '2020-5-23', '2020-6-2', 1388.65), " +
                    "('o_2', 'KI-000102', 'cl_2', 'w_3', NULL, 12, '2020-5-23', '11:02:00', '2020-5-25', '2020-6-6', 2388.65), " +
                    "('o_3', 'KI-000103', 'cl_1', 'w_3', NULL, 10, '2020-5-23', '12:02:00', '2020-5-21', '2020-6-2', 2288.65), " +
                    "('o_4', 'KI-000104', 'cl_3', 'w_2', NULL, 10, '2020-5-19', '14:59:00', '2020-5-19', '2020-5-29', 2868.05);");
            connPostgresTest.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        order1 = new Order("o_1", "KI-000101", "w_1", null, "cl_2", 1388.65, 10,
                LocalDate.of(2020, Month.MAY, 21), LocalTime.of(10, 2, 0), LocalDate.of(2020, Month.MAY, 23),
                LocalDate.of(2020, Month.JUNE, 2));
        order2 = new Order("o_2", "KI-000102", "w_3", null, "cl_2", 2388.65, 12,
                LocalDate.of(2020, Month.MAY, 23), LocalTime.of(11, 2, 0), LocalDate.of(2020, Month.MAY, 25),
                LocalDate.of(2020, Month.JUNE, 6));
        order3 = new Order("o_3", "KI-000103", "w_3", null, "cl_1", 2288.65, 10,
                LocalDate.of(2020, Month.MAY, 23), LocalTime.of(12, 2, 0), LocalDate.of(2020, Month.MAY, 21),
                LocalDate.of(2020, Month.JUNE, 2));
        order4 = new Order("o_4", "KI-000104", "w_2", null, "cl_3", 2868.05, 10,
                LocalDate.of(2020, Month.MAY, 19), LocalTime.of(14, 59, 0), LocalDate.of(2020, Month.MAY, 19),
                LocalDate.of(2020, Month.MAY, 29));

        orderList.addAll(Arrays.asList(order1, order2, order3, order4));
    }

    @Test
    public void testGetById() throws Exception {
        Order result = orderDao.getById("o_2");
        Order expected = order2;
        assertReflectionEquals(expected, result,  ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Order> result = orderDao.getAll();
        assertReflectionEquals(orderList, result,  ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testSaveAll() throws Exception {
        Order order5 = new Order("new o_5", "KI-000105", "w_1", null, "cl_1", 868.05, 10,
                LocalDate.of(2020, Month.MAY, 10), LocalTime.of(10, 0, 0), LocalDate.of(2020, Month.MAY, 11),
                LocalDate.of(2020, Month.MAY, 21));
        Order order6 = new Order("new o_6", "KI-000106", "w_1", null, "cl_1", 88.05, 10,
                LocalDate.of(2020, Month.MAY, 10), LocalTime.of(10, 0, 0), LocalDate.of(2020, Month.MAY, 11),
                LocalDate.of(2020, Month.MAY, 21));
        boolean result = orderDao.saveAll(Arrays.asList(order5, order6));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testUpdateAll() throws Exception {
        order2 = new Order("o_2", "KI-000102-update", "w_3", null, "cl_2", 1002388.65, 12,
                LocalDate.of(2020, Month.MAY, 23), LocalTime.of(11, 2, 0), LocalDate.of(2020, Month.MAY, 25),
                LocalDate.of(2020, Month.JUNE, 6));
        order3 = new Order("o_3", "KI-000103-update", "w_3", null, "cl_1", 1002288.65, 10,
                LocalDate.of(2020, Month.MAY, 23), LocalTime.of(12, 2, 0), LocalDate.of(2020, Month.MAY, 21),
                LocalDate.of(2020, Month.JUNE, 2));

        boolean result = orderDao.updateAll(Arrays.asList(order2, order3));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testDeleteAll() throws Exception {
        boolean result = orderDao.deleteAll(Arrays.asList("o_3", "o_2", "o_1"));
        Assert.assertEquals(true, result);
    }
}
