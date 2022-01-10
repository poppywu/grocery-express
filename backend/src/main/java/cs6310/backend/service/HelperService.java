package cs6310.backend.service;

import cs6310.backend.dao.*;
import cs6310.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class HelperService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;

    public Boolean checkStore(String name) {
        return storeRepository.findStoreByName(name) != null;
    }

    public Boolean checkCustomer(String username) {
        return customerRepository.findCustomerByUsername(username) != null;
    }

    public Boolean checkDrone(DroneId droneId) {
        return droneRepository.findDroneByDroneId(droneId) != null;
    }

    public Boolean checkOrder(OrderId orderId) {
        return orderRepository.findOrderByOrderId(orderId) != null;
    }

    public Order getOrder(OrderId orderId) {
        return orderRepository.findOrderByOrderId(orderId);
    }

    public Boolean checkItem(ItemId itemId) {
        return itemRepository.findByItemId(itemId) != null;
    }

    public Store getStore(String name) {
        return storeRepository.findById(name).get();
    }

    public Drone getDrone(DroneId droneId) {
        return droneRepository.findById(droneId).get();
    }

    public Customer getCustomer(String userName) {
        return customerRepository.findById(userName).get();
    }

    public String getTimeStamp() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }
}
