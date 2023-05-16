package edu.hhu.wa_knowledgemap_updating.service.impl;

import edu.hhu.wa_knowledgemap_updating.dto.OperateRecordDto;
import edu.hhu.wa_knowledgemap_updating.entity.OperateRecord;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.OperateRecordJpaRepository;
import edu.hhu.wa_knowledgemap_updating.service.OperateRecordService;
import edu.hhu.wa_knowledgemap_updating.utils.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OperateRecordServiceImpl implements OperateRecordService {
    @Autowired
    OperateRecordJpaRepository operateRecordJpaRepository;
    @Override
    public RespBean list() {
        List<OperateRecord> list = operateRecordJpaRepository.getAllOperateRecord();
        List<OperateRecordDto> dtoList= new ArrayList<>();
        for(OperateRecord opr:list){
            OperateRecordDto dto=new OperateRecordDto();
            dto.setId(opr.getId());
            dto.setDataType(opr.getDataType());
            dto.setOperateDetail(opr.getOperateDetail());
            dto.setOperateType(opr.getOperateType());
            dto.setOperateTime(DateFormatUtil.formatDate(opr.getOperateTime()));
            dtoList.add(dto);
        }
        return  RespBean.success(dtoList);
    }
}
