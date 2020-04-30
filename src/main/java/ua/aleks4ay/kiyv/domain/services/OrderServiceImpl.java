package ua.aleks4ay.kiyv.domain.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.aleks4ay.kiyv.repository.OrderRepository;
import ua.aleks4ay.kiyv.domain.model.Order;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{

    @Resource
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        if (getOne(id) != null) {
            orderRepository.deleteById(id);
            if (getOne(id) == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Order update(Order order) {
        if (orderRepository.findById(order.getId()) != null) {
            orderRepository.save(order);
            return order;
        }
        return null;
    }

    @Override
    public Order getOne(String id) {
        Order order = orderRepository.findById(id);
        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
