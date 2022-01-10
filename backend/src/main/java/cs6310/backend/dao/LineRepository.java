package cs6310.backend.dao;

import cs6310.backend.model.Line;
import cs6310.backend.model.LineId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, LineId> {
    Line getByLineId(LineId lineId);
}
