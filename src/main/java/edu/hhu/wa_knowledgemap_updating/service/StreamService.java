package edu.hhu.wa_knowledgemap_updating.service;

import edu.hhu.wa_knowledgemap_updating.dto.StreamDto;
import edu.hhu.wa_knowledgemap_updating.dto.StreamKettleDto;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.entity.Stream;
import edu.hhu.wa_knowledgemap_updating.entity.StreamNode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StreamService {
    public boolean updateIncrementalInfo(StreamKettleDto streamDto);

    public  RespBean getAllNode();

    public RespBean list();
    public RespBean selectByName(String  name);
    public RespBean update(StreamDto streamDto);
    public RespBean delete(long id);
    public RespBean create(StreamDto streamDto);

    StreamNode findNodeByName(String name);

    RespBean selectByKeyWord(String keyWord);
    RespBean selectNodeByKeyWord(String keyWord);
}
