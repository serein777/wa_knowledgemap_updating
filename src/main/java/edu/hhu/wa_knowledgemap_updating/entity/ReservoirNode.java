package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.sql.Date;

@Data
@NodeEntity(label = "Reservoir")
public class ReservoirNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property( name ="mysql_id")
    private Long mysqlId;
    @Property( name ="name")
    private String name;
    @Property( name ="max_water_level")
    private Double maxWaterLevel;
    @Property( name ="last_update_time")
    private String lastUpdateTime;
}
