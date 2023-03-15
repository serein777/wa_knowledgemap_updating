package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;

/**
 * 用于封装增量数据
 */
@Data
public class ReservoirKettleDto {
    private Long id;
    private Long mysqlId;
    private String name;
    private Double maxWaterLevel;
    private String lastUpdateTime;
    private String method;
}
