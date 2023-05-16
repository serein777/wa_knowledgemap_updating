package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity( type="Relation")
public class StreamInflowRelation {
    @Id
    @GeneratedValue
    public  Long id;

    @StartNode
    public StreamNode startNode;

    @EndNode
    public StreamNode endNode;
    @Property( name ="mysql_id")
    public Long mysqlId;

    @Property(name = "relation")
    public String relation;


}
