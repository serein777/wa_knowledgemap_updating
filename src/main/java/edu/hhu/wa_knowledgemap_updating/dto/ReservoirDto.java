package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;

import java.sql.Timestamp;

//用于和前端交互的Reservoir实体
@Data
public class ReservoirDto {
    private long id;
    private String name;
    private double maxWaterLevel;
    private String createTime;
    private String updateTime;
}
