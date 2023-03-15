package edu.hhu.wa_knowledgemap_updating.kettle;

import edu.hhu.wa_knowledgemap_updating.dto.StreamKettleDto;
import edu.hhu.wa_knowledgemap_updating.service.StreamService;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.row.RowMetaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StreamKettle {
    @Autowired
    KettleService kettleService;
    @Autowired
    StreamService streamService;
    public void updateIncrementData(String fileName) {
        Result result =kettleService.runKjb(fileName,null,null);
        if(result==null){
            log.info("result 为空");
            return;
        }
        List<RowMetaAndData> rows = result.getRows(); //获取数据
        log.info("row size {}",rows.size());
        if(rows.size()==0) return;
        StreamKettleDto[] streamKettleDtos =new StreamKettleDto[rows.size()];
        int idx=0;
        for (RowMetaAndData row : rows) {
            RowMetaInterface rowMeta = row.getRowMeta(); //获取列的元数据信息
            streamKettleDtos[idx]=new StreamKettleDto();
            String[] fieldNames = rowMeta.getFieldNames();
            Object[] datas = row.getData();
            for (int i = 0; i < fieldNames.length; i++) {
                System.out.println(fieldNames[i] + "=" + datas[i]);
                if (fieldNames[i].equals("name")) {
                    streamKettleDtos[idx].setName((String)datas[i]);
                } else if (fieldNames[i].equals("type")) {
                    streamKettleDtos[idx].setType((String) datas[i]);
                } else if (fieldNames[i].equals("level")) {
                    streamKettleDtos[idx].setLevel(((Long)datas[i]).intValue());
                } else if (fieldNames[i].equals("length")) {
                    streamKettleDtos[idx].setLength(((Long)datas[i]).intValue());
                } else if (fieldNames[i].equals("last_update_time")) {
                    streamKettleDtos[idx].setLastUpdateTime( (String) datas[i]);
                } else if (fieldNames[i].equals("method")) {
                    streamKettleDtos[idx].setMethod( (String) datas[i]);
                }else if (fieldNames[i].equals("old_id")) {
                    streamKettleDtos[idx].setMysqlId( (Long) datas[i]);
                }
            }
            idx++;
        }
        log.info("开始更新到知识图谱中:");
        for (StreamKettleDto node : streamKettleDtos) {
            streamService.updateIncrementalInfo(node);
        }
    }
}
