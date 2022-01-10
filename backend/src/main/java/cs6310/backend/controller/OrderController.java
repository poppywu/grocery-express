package cs6310.backend.controller;

import cs6310.backend.model.*;
import cs6310.backend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {
    Logger logger= LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private StoreService storeService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private HelperService helperService;
    @Autowired
    private PilotService pilotService;
    @Autowired
    private DroneService droneService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LoggerService loggerService;

    @PostMapping(path="/public/start_order")
    public ResponseEntity<String> createOrder(@RequestBody Map<String,String> orderInfo){
        try {
            String storeName=orderInfo.get("storeName");
            String userName=orderInfo.get("username");
            Integer droneId= Integer.parseInt(orderInfo.get("droneId"));
            logger.error(droneId +"is drone Id");
            String orderId=orderInfo.get("orderId");
            if(!helperService.checkStore(storeName)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:store_identifier_does_not_exist");
            }
            if(!helperService.checkCustomer(userName)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:customer_identifier_does_not_exist");
            }
            if(!helperService.checkDrone(new DroneId(droneId,storeName))){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:drone_identifier_does_not_exist");
            }
            OrderId orderID = new OrderId(orderId,storeName);
            if(helperService.checkOrder(orderID)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR:order_identifier_already_exists");
            }
            Order order = new Order(orderID, helperService.getStore(storeName),helperService.getDrone(new DroneId(droneId,storeName)),helperService.getCustomer(userName));
            orderService.save(order);
            loggerService.writeLog("start_order", orderInfo);
            return ResponseEntity.status(HttpStatus.OK).body("OK:change_completed");
        } catch (Exception error) {
            error.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/private/display_orders/{storeName}")
    public ResponseEntity<String> displayOrdersByStore(@PathVariable("storeName") String storeName){
        if(!helperService.checkStore(storeName)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:store_identifier_does_not_exist");
        }
        HashMap<String, String> m = new HashMap<String, String>();
        m.put("store_name", storeName);
        loggerService.writeLog("display_orders", m);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByStoreName(storeName));
    }

    @PostMapping(path="/public/purchase_order")
    public ResponseEntity<String> purchaseOrder(@RequestBody Map<String,String> po){
        String storeName = po.get("storeName");
        if(!helperService.checkStore(storeName))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:store_identifier_does_not_exist");

        OrderId orderId = new OrderId(po.get("orderId"), storeName);
        if(!helperService.checkOrder(orderId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:order_identifier_does_not_exist");
        }

        Order order = helperService.getOrder(orderId);
        Store store = order.getStore();
        Customer customer = order.getCustomer();
        Drone drone = order.getDrone();

        if(drone.getTripsRemaining() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("ERROR:drone_needs_fuel");
        }

        if (drone.getPilot() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("ERROR:drone_needs_pilot");
        }

        // Deducting customer credit & adding to store revenue
        TextEncryptor crypt = Encryptors.text("credit","5c0744940b5c369b");

        for (Line line : order.getLines()) {
            int credit = Integer.parseInt(crypt.decrypt(customer.getCredit()));
            store.setRevenue(store.getRevenue() + line.getTotalPrice());
            customer.setCredit(crypt.encrypt(""+(credit - line.getTotalPrice())));
        }
        drone.setRemainingCapacity(drone.getRemainingCapacity() + order.getWeight());
        drone.removeOrder(order);

        // Updating drone remaining deliveries
        drone.completeOrder();

        // Updating pilot experience
        pilotService.getPilot(drone.getPilot().getUsername()).incrementExperience();
        orderService.remove(order.getOrderId());
        customerService.update(customer);
        storeService.update(store);
        droneService.update(drone);
        loggerService.writeLog("purchase_order", po);
        return ResponseEntity.status(HttpStatus.OK).body("OK:change_completed");
    }

    @PostMapping(path="/public/cancel_order")
    public ResponseEntity<String> cancelOrder(@RequestBody Map<String,String> po){
        String storeName = po.get("storeName");
        if(!helperService.checkStore(storeName))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:store_identifier_does_not_exist");

        OrderId orderId = new OrderId(po.get("orderId"), storeName);
        if(!helperService.checkOrder(orderId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:order_identifier_does_not_exist");
        }

        Order order = helperService.getOrder(orderId);
        Drone drone = order.getDrone();
        drone.removeOrder(order);
        drone.setRemainingCapacity(drone.getRemainingCapacity() + order.getWeight());
        orderService.remove(order.getOrderId());
        droneService.update(drone);
        loggerService.writeLog("cancel_order", po);
        return ResponseEntity.status(HttpStatus.OK).body("OK:change_completed");
    }
}
