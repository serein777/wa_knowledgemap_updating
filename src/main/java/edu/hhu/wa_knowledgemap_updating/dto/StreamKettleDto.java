package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;
/*
用于封装增量更新的数据
 */
@Data
public class StreamKettleDto {
    private Long id;
    private Long mysqlId;
    private String name;
    private String type;
    private Integer level;
    private Integer length;
    private String lastUpdateTime;
    private String method;
}
