package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;

import java.util.Date;

//用于和前端交互的Reservoir实体
@Data
public class ReservoirDto {
    private long id;
    private String name;
    private double maxWaterLevel;
    //经度
    private double longitude;
    //纬度
    private double latitude;
    //高程
    private int elevation;
    private String createTime;
    private String updateTime;
}
