package cs6310.backend.dao;

import cs6310.backend.model.Item;
import cs6310.backend.model.ItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, ItemId> {
    Item findByItemId(ItemId itemId);
}
