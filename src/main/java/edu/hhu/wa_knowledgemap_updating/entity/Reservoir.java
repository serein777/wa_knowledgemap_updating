package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.Property;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "wa_reservoir")
public class Reservoir {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "max_water_level")
    private Double maxWaterLevel;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name="update_time")
    private Timestamp updateTime;
}
