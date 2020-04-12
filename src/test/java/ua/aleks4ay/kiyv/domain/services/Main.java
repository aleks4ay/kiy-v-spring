package ua.aleks4ay.kiyv.domain.services;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.aleks4ay.kiyv.domain.model.Client;
import ua.aleks4ay.kiyv.domain.model.Order;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("persistence-config.xml")) {
            System.out.println("Bean definition names:");
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()).replaceAll(", ", "\n "));

            OrderService orderService = appCtx.getBean(OrderService.class);
            orderService.save(new Order("kod1", "docNumber1", "manager1", "designer1", "client1"));
        }
    }
}