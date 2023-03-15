package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.*;
/*
    图谱 河流节点
 */
@NodeEntity(label = "Stream")
@Data
public class StreamNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property( name ="mysql_id")
    private Long mysqlId;
    @Property( name ="name")
    private String name;
    @Property( name ="type")
    private String type;
    @Property( name ="level")
    private int level;
    @Property( name ="length")
    private int length;
    @Property( name ="last_update_time")
    private String lastUpdateTime;
}
