package cs6310.backend.service;

import com.sun.jdi.request.DuplicateRequestException;
import cs6310.backend.dao.LineRepository;
import cs6310.backend.model.Line;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineService {
    Logger logger = LoggerFactory.getLogger(LineService.class);
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private HelperService helperService;

    public void save(Line line) {
        if (lineRepository.getByLineId(line.getLineId()) != null) {
            logger.error("Line Service : Line Save Failed Due To Duplicate At " + helperService.getTimeStamp());
            throw new DuplicateRequestException("Line Already Exists On Order");
        } else {
            logger.info("Line Service : Line Save Success At " + helperService.getTimeStamp());
            lineRepository.save(line);
        }
    }
}