package edu.hhu.wa_knowledgemap_updating.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import edu.hhu.wa_knowledgemap_updating.kettle.StreamInflowKettle;
import edu.hhu.wa_knowledgemap_updating.kettle.StreamKettle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreamInflowJob {
    @Autowired
    StreamInflowKettle streamInflowKettle;
    @XxlJob(value = "getStreamInflowIncrementData")
    public ReturnT getStreamInflowIncrementData(){
        System.out.println("get stream inflow increment data task start...");
        streamInflowKettle.updateIncrementData("get_steam_inflow_increment_data");
        return  ReturnT.SUCCESS;
    }
}
