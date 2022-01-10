package cs6310.backend.service;

import com.sun.jdi.request.DuplicateRequestException;
import cs6310.backend.dao.StoreRepository;
import cs6310.backend.model.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class StoreService {
    Logger logger = LoggerFactory.getLogger(StoreService.class);
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private HelperService helperService;


    public void save(Store store) {
        try {
            storeRepository.findById(store.getName()).get();
            logger.error("Store Service : Save Store Failed Due To Store Already Exists At " + helperService.getTimeStamp());
            throw new DuplicateRequestException("Store Already Exists");
        } catch (NoSuchElementException error) {
            logger.info("Store Service : Save Store Success At " + helperService.getTimeStamp());
            storeRepository.save(store);
        }
    }

    public void update(Store store) {
        storeRepository.save(store);
    }

    public String getStores() {
        ArrayList<String> resp = new ArrayList<>();
        storeRepository.findAll().forEach(store -> {
            resp.add(store.displayStore());
        });
        resp.add("OK:display_completed");
        logger.info("Store Service : Get Stores Success At " + helperService.getTimeStamp());
        return String.join("\n", resp);
    }
}
