package edu.hhu.wa_knowledgemap_updating.service;

import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import org.springframework.stereotype.Service;

@Service
public interface OperateRecordService {
    public RespBean list();
}
