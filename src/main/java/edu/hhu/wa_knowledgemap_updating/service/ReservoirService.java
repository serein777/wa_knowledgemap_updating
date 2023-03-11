package edu.hhu.wa_knowledgemap_updating.service;

import edu.hhu.wa_knowledgemap_updating.dto.ReservoirUpdateDto;
import edu.hhu.wa_knowledgemap_updating.entity.Node;
import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservoirService {
    public  boolean  updateMaxWaterLevel(ReservoirNode reservoirNode);
    public boolean updateIncrementalInfo(ReservoirUpdateDto nodeDto);

    public List<ReservoirNode> getAllReservoirNode();

}
