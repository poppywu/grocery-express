package cs6310.backend.controller;

import cs6310.backend.model.*;
import cs6310.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private HelperService helperService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private DroneService droneService;
    @Autowired
    private LineService lineService;
    @Autowired
    private LoggerService loggerService;

    @PostMapping(path = "/private/sell_item")
    public ResponseEntity<String> addItem(@RequestBody Map<String, String> ip) {
        if (!helperService.checkStore(ip.get("storeName")))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:store_identifier_does_not_exist");
        ItemId itemId = new ItemId(ip.get("name"), ip.get("storeName"));
        if (!helperService.checkItem(itemId)) {
            Store store = helperService.getStore(ip.get("storeName"));
            Item item = new Item(itemId, Integer.parseInt(ip.get("weight")), store);
            item.setStore(helperService.getStore(ip.get("storeName")));
            itemService.save(item);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR:item_identifier_already_exists");
        }
        loggerService.writeLog("sell_item", ip);
        return ResponseEntity.status(HttpStatus.OK).body("OK:change_completed");
    }

    @GetMapping(path = "/private/display_items/{store_name}")
    public ResponseEntity<String> getItems(@PathVariable("store_name") String storeName) {
        if (!helperService.checkStore(storeName))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:store_identifier_does_not_exist");
        HashMap<String, String> m = new HashMap<String, String>();
        m.put("store_name", storeName);
        loggerService.writeLog("display_items", m);
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItems(storeName));
    }

    @PostMapping(path = "/public/request_item")
    public ResponseEntity<String> requestItem(@RequestBody Map<String, String> ip) {
        String storeName = ip.get("storeName");
        String itemName = ip.get("itemName");
        Integer quantity = Integer.parseInt(ip.get("quantity"));
        Integer price = Integer.parseInt(ip.get("price"));

        if (!helperService.checkStore(storeName))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:store_identifier_does_not_exist");

        OrderId orderId = new OrderId(ip.get("orderId"), storeName);
        if (!helperService.checkOrder(orderId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:order_identifier_does_not_exist");
        }

        ItemId itemId = new ItemId(itemName, storeName);
        if (!helperService.checkItem(itemId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR:item_identifier_does_not_exist");
        }

        Order order = helperService.getOrder(orderId);
        if (order.itemExists(order, orderId.getId(), itemName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR:item_already_ordered");
        }

        Customer customer = helperService.getCustomer(order.getCustomer().getUsername());
        TextEncryptor decryptor = Encryptors.text("credit", "5c0744940b5c369b");
        int credit = Integer.parseInt(decryptor.decrypt(customer.getCredit()));
        int updatedCredit = credit - order.getCost() - (quantity * price);


        Drone drone = order.getDrone();
        Integer weight = itemService.getItem(itemId).getWeight();
        int updatedCapacity = drone.getRemainingCapacity() - order.getWeight() - (quantity * weight);

        if (updatedCredit < 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("ERROR:customer_cant_afford_new_item");
        }

        if (updatedCapacity < 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("ERROR:drone_cant_carry_new_item");
        }

        // Check constraints & update order
        LineId lineId = new LineId(orderId, itemName);
        Line line = new Line(lineId, quantity, price, weight, order);
        drone.setRemainingCapacity(drone.getRemainingCapacity() - line.getTotalWeight());
        drone.addOrder(order);
        lineService.save(line);
        loggerService.writeLog("request_item", ip);

        return ResponseEntity.status(HttpStatus.OK).body("OK:change_completed");
    }
}
