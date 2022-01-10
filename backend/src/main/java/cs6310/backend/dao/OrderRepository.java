package cs6310.backend.dao;

import cs6310.backend.model.Order;
import cs6310.backend.model.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order,OrderId> {
    Order findOrderByOrderId(OrderId orderId);
    List<Order> getOrdersByOrderId_Name(String storeName);
    List<Order> findOrdersByCreateDateBefore(Date date);
    void deleteOrdersByCreateDateBefore(Date date);
}
