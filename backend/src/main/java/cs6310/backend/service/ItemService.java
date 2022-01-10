package cs6310.backend.service;

import com.sun.jdi.request.DuplicateRequestException;
import cs6310.backend.dao.ItemRepository;
import cs6310.backend.model.Item;
import cs6310.backend.model.ItemId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ItemService {
    Logger logger = LoggerFactory.getLogger(ItemService.class);
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private HelperService helperService;

    public void save(Item item) {
        if (checkItem(item.getItemId())) {
            logger.error("Item Service : Item Save Failed Due To Item Already Exists At " + helperService.getTimeStamp());
            throw new DuplicateRequestException("Item Already Exists");
        } else {
            logger.info("Item Service : Item Save Success At " + helperService.getTimeStamp());
            itemRepository.save(item);
        }
    }

    public Boolean checkItem(ItemId itemId) {
        return itemRepository.findByItemId(itemId) != null;
    }

    public Item getItem(ItemId itemId) {
        return itemRepository.findByItemId(itemId);
    }

    public String getItems(String storeName) {
        ArrayList<String> resp = new ArrayList<>();
        itemRepository.findAll().forEach(item -> {
            if (item.getStore().getName().equals(storeName))
                resp.add(item.displayItem());
        });
        resp.add("OK:display_completed");
        logger.info("Item Service : Get Items For Store : " + storeName + " Success At " + helperService.getTimeStamp());
        return String.join("\n", resp);
    }
}
