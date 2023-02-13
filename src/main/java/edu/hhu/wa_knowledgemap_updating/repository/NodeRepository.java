package edu.hhu.wa_knowledgemap_updating.repository;

import edu.hhu.wa_knowledgemap_updating.entity.Node;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface NodeRepository extends Neo4jRepository<Node,Long> {
    @Query("match(n:Fate) return n")
    public List<Node> selectAll();

    @Query("match(n:Fate{name:{name}}) return n")
    public  Node findByName(@Param("name") String name);

}
