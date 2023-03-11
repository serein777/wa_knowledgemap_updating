package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
@Data
public class ReservoirUpdateDto {
    private Long id;
    private Long mysqlId;
    private String name;
    private Double maxWaterLevel;
    private String lastUpdateTime;
    private String method;
}
