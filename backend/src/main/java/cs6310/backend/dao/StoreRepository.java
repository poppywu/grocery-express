package cs6310.backend.dao;

import cs6310.backend.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, String> {
    Store findStoreByName(String name);
}
