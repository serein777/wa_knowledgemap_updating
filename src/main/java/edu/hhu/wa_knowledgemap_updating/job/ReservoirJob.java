package edu.hhu.wa_knowledgemap_updating.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import edu.hhu.wa_knowledgemap_updating.kettle.KettleService;
import edu.hhu.wa_knowledgemap_updating.kettle.ReservoirKettle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class ReservoirJob {
    @Autowired
    ReservoirKettle reservoirKettle;
    @XxlJob(value = "getMaxWaterLevel")
    public ReturnT  getMaxWaterLevel(){
             System.out.println("get max water level task start...");
            reservoirKettle.getMaxWaterLevel("get_reservoir_max_water_level");
             return  ReturnT.SUCCESS;
         }

    @XxlJob(value = "getReservoirIncrementData")
    public ReturnT  getReservoirIncrementData(){
        System.out.println("get reservoir update data task start...");
        reservoirKettle.updateNewInfo("get_reservoir_increment_data");
        return  ReturnT.SUCCESS;
    }
}
