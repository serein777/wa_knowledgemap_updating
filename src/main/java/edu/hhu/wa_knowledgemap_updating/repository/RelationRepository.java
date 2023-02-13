package edu.hhu.wa_knowledgemap_updating.repository;

import edu.hhu.wa_knowledgemap_updating.entity.Node;
import edu.hhu.wa_knowledgemap_updating.entity.Relation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface RelationRepository extends Neo4jRepository<Relation,Long> {
    @Query("MATCH p=(n:Actor)-[r:Relation]->(m:Actor) " +
            "WHERE id(n)={startNode} and id(m)={endNode} and r.relation={relation} RETURN p")
    public Relation findRelation(@Param("startNode") Node startNode, @Param("endNode") Node endNode, @Param("relation")
                                      String relation);
}
