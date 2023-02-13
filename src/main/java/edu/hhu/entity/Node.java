package edu.hhu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
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
