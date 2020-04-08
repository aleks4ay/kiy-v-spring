package ua.aleks4ay.kiyv.domain.services;

import ua.aleks4ay.kiyv.domain.model.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order);

    boolean delete(int id);

    Order update(Order order);

    Order getOne(int id);

    List<Order> getAll();

}
