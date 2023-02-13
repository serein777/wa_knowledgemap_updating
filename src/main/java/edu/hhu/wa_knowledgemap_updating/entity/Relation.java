package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity( type="Relation")
public class Relation {
    @Id
    @GeneratedValue
    public  Long id;

    @StartNode
    public Node startNode;

    @EndNode
    public  Node endNode;

    @Property
    public String relation;


}
