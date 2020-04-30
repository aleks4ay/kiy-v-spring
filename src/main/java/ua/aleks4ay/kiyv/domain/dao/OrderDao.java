package ua.aleks4ay.kiyv.domain.dao;

import ua.aleks4ay.kiyv.domain.model.Order;

import java.util.Collection;
import java.util.List;

public interface OrderDao {

//    boolean save(Order order);
//
//    boolean delete(String kod);

    Order getById(String id);

    List<Order> getAll();

    boolean saveAll(List<Order> orderList);

    boolean updateAll(List<Order> orderList);

    boolean deleteAll(Collection<String> orderList);

}
