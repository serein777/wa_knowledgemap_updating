package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;

@Data
public class StreamInflowKettleDto {
    Long  inflowStartId;
    Long  inflowEndId;
    String inflowStartName;
    String inflowEndName;
    String method;
}
