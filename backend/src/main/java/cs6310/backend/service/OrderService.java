package cs6310.backend.service;

import com.sun.jdi.request.DuplicateRequestException;
import cs6310.backend.dao.ArchiveRepository;
import cs6310.backend.dao.OrderRepository;
import cs6310.backend.model.Order;
import cs6310.backend.model.OrderId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private HelperService helperService;

    public void save(Order order) {
        if (orderRepository.findOrderByOrderId(order.getOrderId()) != null ||
                archiveRepository.findArchiveByArchiveIdArchiveOrderId(order.getOrderId().getId()) != null) {
            logger.error("Order Service : Save Order Failed Due To Order Already Exists At " + helperService.getTimeStamp());
            throw new DuplicateRequestException("Order Already Exists");
        } else {
            logger.info("Order Service : Save Order Success At " + helperService.getTimeStamp());
            orderRepository.save(order);
        }
    }

    public void update(Order order) {
        orderRepository.save(order);
    }

    public void remove(OrderId orderId) {
        orderRepository.deleteById(orderId);
    }

    public String getOrdersByStoreName(String storeName) {
        List<String> displayArray = new ArrayList<>();
        orderRepository.getOrdersByOrderId_Name(storeName).forEach(order -> displayArray.add(order.displayOrder()));
        displayArray.add("OK:display_completed");
        logger.info("Order Service : Get Orders For Store : " + storeName + " Success At " + helperService.getTimeStamp());
        return String.join("\n", displayArray);
    }

    public List<Order> findOrdersByCreateDateBefore(java.util.Date thresholdTimestamp) {
        return orderRepository.findOrdersByCreateDateBefore(thresholdTimestamp);
    }

    @Transactional
    public void deleteOrdersByCreateDateBefore(java.util.Date thresholdTimestamp) {
        orderRepository.deleteOrdersByCreateDateBefore(thresholdTimestamp);
    }


}
