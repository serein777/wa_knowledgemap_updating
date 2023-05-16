package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import javax.persistence.Column;
import java.sql.Date;

@Data
@NodeEntity(label = "Reservoir")
public class ReservoirNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property( name ="mysql_id")
    private long mysqlId;
    @Property( name ="name")
    private String name;
    @Property( name ="max_water_level")
    private double maxWaterLevel;
    //经度
    @Property(name = "longitude")
    private double longitude;
    //纬度
    @Property(name = "latitude")
    private double latitude;
    //高程
    @Property(name = "elevation")
    private  int elevation;
    @Property( name ="last_update_time")
    private String lastUpdateTime;
}
