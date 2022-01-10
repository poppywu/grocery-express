package cs6310.backend.controller;

import com.sun.jdi.request.DuplicateRequestException;
import cs6310.backend.model.Store;
import cs6310.backend.service.LoggerService;
import cs6310.backend.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StoreController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private LoggerService loggerService;

    @PostMapping(path="/private/make_store")
    public ResponseEntity<String> addStore(@RequestBody Map<String, String> sp){
        try {
            Store store = new Store(sp.get("name"), Integer.parseInt(sp.get("revenue")));
            storeService.save(store);
            loggerService.writeLog("make_store", sp);
            return ResponseEntity.status(HttpStatus.OK).body("OK:change_completed");
        } catch (DuplicateRequestException error){
            error.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR:store_identifier_already_exists");
        }
    }
    @GetMapping(path="/private/display_stores")
    public ResponseEntity<String> getStores() {
        loggerService.writeLog("display_stores", new HashMap<String, String>());
        return ResponseEntity.status(HttpStatus.OK).body(storeService.getStores());
    }
}
