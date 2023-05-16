package edu.hhu.wa_knowledgemap_updating.kettle;

import edu.hhu.wa_knowledgemap_updating.dto.StreamInflowKettleDto;
import edu.hhu.wa_knowledgemap_updating.service.StreamInflowService;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.row.RowMetaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StreamInflowKettle {
    @Autowired
    KettleService kettleService;
    @Autowired
    StreamInflowService streamInflowService;

    public void updateIncrementData(String fileName) {
        Result result =kettleService.runKjb(fileName,null,null);
        if(result==null){
            log.info("result 为空");
            return;
        }
        List<RowMetaAndData> rows = result.getRows(); //获取数据
        log.info("row size {}",rows.size());
        if(rows.size()==0) return;
        StreamInflowKettleDto[] streamInflowDtos=new StreamInflowKettleDto[rows.size()];
        int idx=0;
        for (RowMetaAndData row : rows) {
            RowMetaInterface rowMeta = row.getRowMeta(); //获取列的元数据信息
            streamInflowDtos[idx]=new StreamInflowKettleDto();
            String[] fieldNames = rowMeta.getFieldNames();
            Object[] datas = row.getData();

            for (int i = 0; i < fieldNames.length; i++) {
                System.out.println(fieldNames[i] + "=" + datas[i]);
                if (fieldNames[i].equals("inflow_start_id")) {
                    streamInflowDtos[idx].setInflowStartId((Long) datas[i]);
                }else if (fieldNames[i].equals("inflow_end_id")) {
                    streamInflowDtos[idx].setInflowEndId((Long) datas[i]);
                }else if (fieldNames[i].equals("inflow_start_name")) {
                    streamInflowDtos[idx].setInflowStartName((String) datas[i]);
                }else if (fieldNames[i].equals("inflow_end_name")) {
                    streamInflowDtos[idx].setInflowEndName((String) datas[i]);
                }else if (fieldNames[i].equals("method")) {
                    streamInflowDtos[idx].setMethod((String) datas[i]);
                } else if (fieldNames[i].equals("old_id")) {
                    streamInflowDtos[idx].setMysqlId((Long) datas[i]);
                }

            }
            idx++;
        }
        log.info("开始更新到知识图谱中:");
        for (StreamInflowKettleDto node : streamInflowDtos) {
            streamInflowService.updateIncrementalInfo(node);
        }
    }
}
