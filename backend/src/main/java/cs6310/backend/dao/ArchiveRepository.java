package cs6310.backend.dao;

import cs6310.backend.model.Archive;
import cs6310.backend.model.ArchiveId;
import cs6310.backend.model.Order;
import cs6310.backend.model.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, ArchiveId> {
    Archive findArchiveByArchiveIdArchiveOrderId(String archiveOrderId);
}