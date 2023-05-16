package edu.hhu.wa_knowledgemap_updating;

import edu.hhu.wa_knowledgemap_updating.dto.ReservoirKettleDto;
import edu.hhu.wa_knowledgemap_updating.entity.OperateRecord;
import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import edu.hhu.wa_knowledgemap_updating.kettle.KettleService;
import edu.hhu.wa_knowledgemap_updating.kettle.ReservoirKettle;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.OperateRecordJpaRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.ReservoirJpaRepository;
import edu.hhu.wa_knowledgemap_updating.utils.DateFormatUtil;
import org.junit.jupiter.api.Test;
import org.pentaho.di.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class ReservoirTest {
    @Autowired
    ReservoirJpaRepository reservoirJpaRepository;
    @Autowired
    ReservoirKettle reservoirKettle;
    @Autowired
    KettleService kettleService;
    @Autowired
    OperateRecordJpaRepository operateRecordJpaRepository;
    @Test
    public  void test1(){
         reservoirKettle.updateNewInfo("get_reservoir_increment_data");
    }
    @Test
    public  void  test2(){
        ReservoirKettleDto r=new ReservoirKettleDto();
        r.setMysqlId(1L);
        r.setName("测试水坝");
        r.setMaxWaterLevel(12.5);
        r.setLongitude(135.0);
        r.setLatitude(35.0);
        r.setElevation(120);
        r.setLastUpdateTime(DateFormatUtil.formatDate(new Date()));
        OperateRecord operateRecord=new OperateRecord();
        StringBuilder opDetail=new StringBuilder();
        opDetail.append("创建水坝节点:");
        opDetail.append(r.toString());
        operateRecord.setOperateDetail(opDetail.toString());
        operateRecord.setDataType("水坝");
        operateRecord.setOperateType("创建");
        operateRecord.setOperateTime(new Date());
        operateRecordJpaRepository.save(operateRecord);

    }
}
