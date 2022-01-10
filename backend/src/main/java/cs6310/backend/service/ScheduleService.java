package cs6310.backend.service;

import cs6310.backend.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleService {
    Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    @Autowired
    private ArchiveService archiveService;
    @Autowired
    private OrderService orderService;

    //in order to demo the schedule service, every 10s an dummy order will be added
    @Scheduled(fixedDelay = 10000)
    @Async
    public void scheduleAddDummyOrder() {
        String uuid = UUID.randomUUID().toString();
        OrderId orderId = new OrderId(uuid, "kroger");
        Store store = new Store("kroger", 33000);
        Customer customer = new Customer("aapple2", "John", "Doe", "123-456-7890", 5, "1000");
        DroneId droneId = new DroneId(1, "kroger");
        Drone drone = new Drone(droneId, 1000, 1000);
        Order order = new Order(orderId, store, drone, customer);
        orderService.save(order);
        logger.info("Dummy Order Gets Added - store name : kroger, customer : aapple2, drone : 1, order : " + uuid);
    }

    //every 30s, the archive job gets run, and any order older than 15s will get archived and deleted from the order table
    @Scheduled(fixedDelay = 30000)
    @Async
    public void archiveThenDeleteScheduledJob() {
        Date thresholdTimestamp = getArchiveTimestampThreshold(300);
        List<Order> orderList = orderService.findOrdersByCreateDateBefore(thresholdTimestamp);
        orderList.forEach(order -> {
            String orderId = order.getOrderId().getId();
            String storeName = order.getStore().getName();
            Date dateCreated = order.getCreateDate();
            archiveService.archiveOrder(new Archive(new ArchiveId(storeName, orderId), dateCreated));
        });
        orderService.deleteOrdersByCreateDateBefore(thresholdTimestamp);
        logger.info("Dummy Order of Timestamp Older Than 300 Seconds Gets Archived and Deleted");
    }

    public Date getArchiveTimestampThreshold(Integer secondsOld) {
        Date current = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.SECOND, -secondsOld);
        return cal.getTime();
    }
}
