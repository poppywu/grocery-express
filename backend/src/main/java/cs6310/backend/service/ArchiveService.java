package cs6310.backend.service;

import cs6310.backend.dao.ArchiveRepository;
import cs6310.backend.model.Archive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchiveService {
    Logger logger = LoggerFactory.getLogger(ArchiveService.class);
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private HelperService helperService;

    public void archiveOrder(Archive archive) {
        archiveRepository.save(archive);
        String timeStamp = helperService.getTimeStamp();
        logger.info("Archive Service - Archive Order - Success At " + timeStamp);
    }
}