package edu.hhu.wa_knowledgemap_updating.entity;
import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.springframework.context.annotation.Bean;

@Data
@NodeEntity(label = "Fate")
public class Node {
    @Id
    @GeneratedValue
    private  Long id;

    @Property( name ="name")
    private  String name;

    @Property(name = "age")
    private  int age;
}
