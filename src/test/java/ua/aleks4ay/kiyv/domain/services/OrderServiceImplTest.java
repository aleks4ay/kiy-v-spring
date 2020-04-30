package ua.aleks4ay.kiyv.domain.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import ua.aleks4ay.kiyv.domain.model.Description;
import ua.aleks4ay.kiyv.domain.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;


@ContextConfiguration("classpath:persistence-config.xml")
@RunWith(SpringRunner.class)
@Sql(scripts = "/db/clear_order.sql")
//@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@Transactional
public class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    List<Order> orderList;

    Order order1, order2, order3, order4;
    Description descr1, descr2;


    @Before
    public void setUp() {
        order1 = new Order("kod1", "docNumber1", "manager1", "designer1", "client1");
        order2 = new Order("kod2", "docNumber2", "manager2", "designer2", "client2");
        order3 = new Order("kod3", "docNumber3", "manager3", "designer3", "client3");
        order4 = new Order("kod4", "docNumber4", "manager4", "designer4", "client4");

        descr1 = new Description(1 , "Стол н/ж 1200х600х850, с бортом, 3 полки", "", 0, 0,
                LocalDateTime.now(), 1, new BigDecimal(2825.00), null);
        descr2 = new Description(2 , "Стеллаж н/ж 1200х400х1250, 5 полок", "", 0, 0,
                LocalDateTime.now(), 1, new BigDecimal(2187.00), null);
        descr1.setOwner(order1);
        descr2.setOwner(order1);
        order1.setDescriptions(Arrays.asList(descr1, descr2));
        orderList = Arrays.asList(order1, order2, order3, order4);

        orderService.save(order1);
        orderService.save(order2);
        orderService.save(order3);
        orderService.save(order4);
    }

    @Test
    public void testGetOne() throws Exception {
        Order result = orderService.getOne("kod1");
        Order expected = new Order("kod1", "docNumber1", "manager1", "designer1", "client1");
        assertReflectionEquals(expected, result,  ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testSave() throws Exception {
        Order result = orderService.save(new Order("kod", "docNumber", "manager", "designer", "client"));
        Order actual = new Order("kod", "docNumber", "manager", "designer", "client");
        assertReflectionEquals(actual, result, ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testDelete() throws Exception {
        boolean result = orderService.delete("kod1");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testUpdate() throws Exception {
        Order newOrder = new Order("kod1", "docNumber_update", "manager_update", "designer_update", "client_update");
        Order result = orderService.update(newOrder);
        assertReflectionEquals(newOrder, result, ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Order> result = orderService.getAll();
        List<Order> expected = Arrays.asList(order1, order2, order3, order4);
        assertReflectionEquals(expected, result, ReflectionComparatorMode.IGNORE_DEFAULTS);
    }
}
