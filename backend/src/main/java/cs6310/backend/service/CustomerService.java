package cs6310.backend.service;

import com.sun.jdi.request.DuplicateRequestException;
import cs6310.backend.dao.CustomerRepository;
import cs6310.backend.model.Customer;
import cs6310.backend.model.Line;
import cs6310.backend.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class CustomerService {
    Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private HelperService helperService;


    public void save(Customer customer) {
        TextEncryptor encryptor = Encryptors.text("credit", "5c0744940b5c369b");
        customer.setCredit(encryptor.encrypt(customer.getCredit()));
        String timeStamp = helperService.getTimeStamp();
        try {
            customerRepository.findById(customer.getUsername()).get();
            logger.error("Customer Service : Save Customer Failed Due To Customer Already Exists At " + timeStamp);
            throw new DuplicateRequestException("Customer Already Exists");
        } catch (NoSuchElementException error) {
            customerRepository.save(customer);
            logger.info("Customer Service : Save Customer Success At " + timeStamp);
        }
    }

    public void update(Customer customer) {
        customerRepository.save(customer);
        logger.info("Customer Service : Update Customer Success At " + helperService.getTimeStamp());
    }

    public String getCustomers() {
        ArrayList<String> resp = new ArrayList<>();
        TextEncryptor encryptor = Encryptors.text("credit", "5c0744940b5c369b");
        customerRepository.findAll().forEach(customer -> {
            customer.setCredit(encryptor.decrypt(customer.getCredit()));
            resp.add(customer.displayCustomer());
        });
        resp.add("OK:display_completed");
        logger.info("Customer Service : Get Customer Success At " + helperService.getTimeStamp());
        return String.join("\n", resp);
    }

    public String getCustomerCredit(String username) {
        TextEncryptor encryptor = Encryptors.text("credit", "5c0744940b5c369b");
        logger.info("Customer Service : Get Customer Credit Success At " + helperService.getTimeStamp());
        return encryptor.decrypt(customerRepository.getById(username).getCredit());
    }

    public void updateCustomerCredit(String username, String credit) {
        TextEncryptor encryptor = Encryptors.text("credit", "5c0744940b5c369b");
        logger.info("Customer Service : Update Customer Credit Success At " + helperService.getTimeStamp());
        customerRepository.updateCredit(encryptor.encrypt(credit), username);
    }

    public Boolean canAffordNewLine(Line line, Order order) {
        Integer credit = Integer.parseInt(getCustomerCredit(order.getCustomer().getUsername()));
        return credit >= line.getTotalPrice();
    }
}
