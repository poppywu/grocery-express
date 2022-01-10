package cs6310.backend.controller;

import com.sun.jdi.request.DuplicateRequestException;
import cs6310.backend.model.Customer;
import cs6310.backend.service.CustomerService;
import cs6310.backend.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LoggerService loggerService;

    @PostMapping(path="/private/make_customer")
    public ResponseEntity<String> addCustomer(@RequestBody Map<String, String> cp){
        try {
            Customer customer = new Customer(
                    cp.get("username"), cp.get("firstName"), cp.get("lastName"),
                    cp.get("phoneNum"), Integer.parseInt(cp.get("rating")), cp.get("credit")
            );
            customerService.save(customer);
            loggerService.writeLog("make_customer", cp);
            return ResponseEntity.status(HttpStatus.OK).body("OK:change_completed");
        } catch (DuplicateRequestException error){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR:customer_identifier_already_exists");
        }
    }

    @GetMapping(path="/private/display_customers")
    public ResponseEntity<String> getCustomers() {
        loggerService.writeLog("display_customers", new HashMap<String, String>());
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers());
    }
}
