package ua.aleks4ay.kiyv.repository;

import org.junit.Before;
import org.junit.Test;
import ua.aleks4ay.kiyv.domain.model.Client;
import ua.aleks4ay.kiyv.domain.model.Description;
import ua.aleks4ay.kiyv.domain.model.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TablesDBCreate {
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception{
        entityManagerFactory = Persistence.createEntityManagerFactory("ua.aleks4ay.kiyv.domain.model");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(createTempOrder());
        em.persist(createTempClient());
        em.getTransaction().commit();
        em.close();
    }

    public Order createTempOrder() {
        Order order = new Order("DR31", "KI-0001234", "Соломко", "Мосиенко", "Агромат-сервис");
        Description d1 = new Description(1 , "Стол н/ж 1200х600х850, с бортом, 3 полки", "", 0, 0,
                LocalDateTime.now(), 1, new BigDecimal(2825.00), null);
        Description d2 = new Description(2 , "Стеллаж н/ж 1200х400х1250, 5 полок", "", 0, 0,
                LocalDateTime.now(), 1, new BigDecimal(2187.00), null);
        Description d3 = new Description(3 , "Мойка 1С н/ж 600х600х850, с бортом, без полки", "1 отв. под смеситель", 0, 0,
                LocalDateTime.now(), 1, new BigDecimal(3045.00), null);
        Description d4 = new Description(1 , "Тележка серверовочная н/ж 1200х600х1050", "", 0, 0,
                LocalDateTime.now(), 1, new BigDecimal(4012.50), null);
        d1.setOwner(order);
        d2.setOwner(order);
        d3.setOwner(order);
        d4.setOwner(order);
        order.setDescriptions(Arrays.asList(d1, d2, d3, d4));
        return order;
    }

    public Client createTempClient() {
        Client client = new Client();
        client.setKod("klient1");
        client.setClientName("klient name 1");
        return client;
    }

    @Test
    public void testResultToString() throws Exception {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("from Order ", Order.class)
                .getResultList()
                .forEach(System.out::println);
        System.out.println("-------------------");
        em.createQuery("from Description ", Description.class)
                .getResultList()
                .forEach(System.out::println);
        System.out.println("-------------------");
        em.createQuery("from Client ", Client.class)
                .getResultList()
                .forEach(System.out::println);

        em.getTransaction().commit();
        em.close();
    }
}
