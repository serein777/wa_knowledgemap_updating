package edu.hhu.wa_knowledgemap_updating.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import edu.hhu.wa_knowledgemap_updating.kettle.StreamKettle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreamJob {
    @Autowired
    StreamKettle streamKettle;
    @XxlJob(value = "getStreamIncrementData")
    public ReturnT  getStreamIncrementData(){
        System.out.println("get stream increment data task start...");
       streamKettle.updateIncrementData("get_steam_increment_data");
        return  ReturnT.SUCCESS;
    }
}
