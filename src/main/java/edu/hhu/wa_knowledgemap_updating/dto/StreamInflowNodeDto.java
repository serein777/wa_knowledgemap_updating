package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;
/*
用于前端交互
* */
@Data
public class StreamInflowNodeDto {
    long startId;
    Long endId;
    String relation;
}
