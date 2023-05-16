package edu.hhu.wa_knowledgemap_updating.entity;

import edu.hhu.wa_knowledgemap_updating.utils.DateFormatUtil;
import lombok.Data;
import org.neo4j.ogm.annotation.Property;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "wa_reservoir")
public class Reservoir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "max_water_level")
    private double maxWaterLevel;
    //经度
    @Column(name = "longitude")
    private double longitude;
    //纬度
    @Column(name = "latitude")
    private double latitude;
    //高程
    @Column(name = "elevation")
    private int elevation;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name="update_time")
    private Date updateTime;

}
