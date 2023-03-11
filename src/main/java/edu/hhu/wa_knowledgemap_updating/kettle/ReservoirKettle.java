package edu.hhu.wa_knowledgemap_updating.kettle;

import edu.hhu.wa_knowledgemap_updating.dto.ReservoirUpdateDto;
import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import edu.hhu.wa_knowledgemap_updating.service.ReservoirService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.row.RowMetaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * 用于执行水坝相关的kettle  trans job
 */
@Service
@Slf4j
public class ReservoirKettle {
    @Autowired
    KettleService kettleService;
    @Autowired
    ReservoirService reservoirService;
    public  void  getMaxWaterLevel(String fileName){
       Result result =kettleService.runKjb(fileName,null,null);
       if(result==null){
           log.info("result 为空");
           return;
       }
       List<RowMetaAndData> rows = result.getRows(); //获取数据
        log.info("row size {}",rows.size());
        if(rows.size()==0) return;
        ReservoirNode reservoirNodes[]=new ReservoirNode[rows.size()];
        int idx=0;
        log.info("当前最高水坝水位信息:");
        for (RowMetaAndData row : rows) {
            RowMetaInterface rowMeta = row.getRowMeta(); //获取列的元数据信息
            String[] fieldNames = rowMeta.getFieldNames();
            Object[] datas = row.getData();
            reservoirNodes[idx]=new ReservoirNode();
            for (int i = 0; i < fieldNames.length; i++) {
                System.out.println(fieldNames[i] + "=" + datas[i]);
                if(fieldNames[i].equals("name")){
                    reservoirNodes[idx].setName((String) datas[i]);
                }
                else if(fieldNames[i].equals("max_water_level")){
                    reservoirNodes[idx].setMaxWaterLevel(((BigDecimal)datas[i]).doubleValue());
                }
                else if(fieldNames[i].equals("last_update_time")){
                    reservoirNodes[idx].setLastUpdateTime((String) datas[i]);
                }
            }
            idx++;
        }
        for( ReservoirNode node :reservoirNodes){
            reservoirService.updateMaxWaterLevel(node);
        }
    }

    /**
     * 获取增量信息 并封装实体
     * @param fileName
     */
    public void  updateNewInfo(String fileName){
        Result result=kettleService.runKjb(fileName,null,null);
        if(result==null){
            log.info("result 为空");
            return;
        }
        List<RowMetaAndData> rows = result.getRows(); //获取数据
        log.info("row size {}",rows.size());
        if(rows.size()==0) return;
        ReservoirUpdateDto[] reservoirUpdateDtos=new ReservoirUpdateDto[rows.size()];
        int idx=0;
        log.info("水坝增量信息如下:");
        for (RowMetaAndData row : rows) {
            RowMetaInterface rowMeta = row.getRowMeta(); //获取列的元数据信息
            String[] fieldNames = rowMeta.getFieldNames();
            Object[] datas = row.getData();
            reservoirUpdateDtos[idx] = new ReservoirUpdateDto();
            for (int i = 0; i < fieldNames.length; i++) {
                System.out.println(fieldNames[i] + "=" + datas[i]);
                if (fieldNames[i].equals("name")) {
                    reservoirUpdateDtos[idx].setName((String) datas[i]);
                } else if (fieldNames[i].equals("max_water_level")) {
                    reservoirUpdateDtos[idx].setMaxWaterLevel(((BigDecimal) datas[i]).doubleValue());
                }else if (fieldNames[i].equals("update_time")) {
                    reservoirUpdateDtos[idx].setLastUpdateTime((String) datas[i]);
                } else if (fieldNames[i].equals("method")) {
                    reservoirUpdateDtos[idx].setMethod((String) datas[i]);
                }else if (fieldNames[i].equals("old_id")) {
                    reservoirUpdateDtos[idx].setMysqlId((Long) datas[i]);
                }
            }
            idx++;
        }
        log.info("开始更新到知识图谱中:");
        for (ReservoirUpdateDto node : reservoirUpdateDtos) {
                reservoirService.updateIncrementalInfo(node);
        }
    }
}
