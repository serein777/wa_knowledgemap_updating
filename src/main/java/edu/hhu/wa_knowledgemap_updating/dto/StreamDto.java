package edu.hhu.wa_knowledgemap_updating.dto;

//用于前端返回数据

import lombok.Data;

@Data
public class StreamDto {
    private long id;
    private String name;
    private String type;
    private Integer level;
    private Integer length;
    private String createTime;
    private String updateTime;
}
