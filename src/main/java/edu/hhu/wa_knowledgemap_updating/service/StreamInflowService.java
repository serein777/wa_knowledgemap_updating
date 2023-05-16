package edu.hhu.wa_knowledgemap_updating.service;

import edu.hhu.wa_knowledgemap_updating.dto.StreamDto;
import edu.hhu.wa_knowledgemap_updating.dto.StreamInflowDto;
import edu.hhu.wa_knowledgemap_updating.dto.StreamInflowKettleDto;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;

public interface StreamInflowService {
    //获取增量信息，更新到知识图谱
    public boolean updateIncrementalInfo(StreamInflowKettleDto node);
    public RespBean list();
    public RespBean update(StreamInflowDto streamInflowDto);
    public RespBean delete(long id);
    public RespBean create(StreamInflowDto streamInflowDto);

    RespBean findByKeyWord(String keyWord);
}
