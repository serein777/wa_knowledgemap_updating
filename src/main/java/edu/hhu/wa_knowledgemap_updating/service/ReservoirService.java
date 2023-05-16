package edu.hhu.wa_knowledgemap_updating.service;

import edu.hhu.wa_knowledgemap_updating.dto.ReservoirDto;
import edu.hhu.wa_knowledgemap_updating.dto.ReservoirKettleDto;
import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import org.springframework.stereotype.Service;

@Service
public interface ReservoirService {
    public  boolean  updateMaxWaterLevel(ReservoirNode reservoirNode);
    public boolean updateIncrementalInfo(ReservoirKettleDto nodeDto);

    public RespBean getAllReservoirNode();

    public ReservoirNode findNodeByName(String name);

    public RespBean list();

    RespBean findByName(String name);

    RespBean create(ReservoirDto reservoirDto);

    RespBean update(ReservoirDto reservoirDto);


    public RespBean delete(long id);

    RespBean findByKeyWord(String keyWord);

    RespBean selectNodeByKeyWord(String keyWord);

}
