package cs6310.backend.dao;

import cs6310.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Modifying
    @Query("update Customer c set c.credit=:credit where c.username=:username")
    void updateCredit(@Param("credit") String credit,@Param("username") String username);
    Customer findCustomerByUsername(String username);
}
