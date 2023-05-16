package edu.hhu.wa_knowledgemap_updating.repository;

import edu.hhu.wa_knowledgemap_updating.entity.StreamInflow;
import edu.hhu.wa_knowledgemap_updating.entity.StreamInflowRelation;
import edu.hhu.wa_knowledgemap_updating.entity.StreamNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.management.relation.Relation;
import java.util.List;

@Repository
public interface StreamInflowRepository extends Neo4jRepository<StreamInflowRelation,Long> {
    @Query("Match  p=(n:Stream)-[r{relation:{relation}}]->(m:Stream) " +
            "WHERE id(n)={startNode} and id(m)={endNode} and r.relation={relation}" +
            "RETURN p")
    StreamInflowRelation findRelation(@Param("startNode") StreamNode startNode,
                                @Param("endNode") StreamNode endNode,
                                @Param("relation") String relation);
    @Query("Match  p=(n:Stream)-[r]->(m:Stream) where r.relation='流入' return p")
    List<StreamInflowRelation> getAllRelations();
    @Query("Match  p=(n:Stream)-[r]->(m:Stream) where r.mysql_id={mysql_id} return p")
    StreamInflowRelation findByMysqlId(@Param("mysql_id") Long mysqlId);

}
