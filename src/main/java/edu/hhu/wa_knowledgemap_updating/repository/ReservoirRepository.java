package edu.hhu.wa_knowledgemap_updating.repository;

import edu.hhu.wa_knowledgemap_updating.entity.Node;
import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReservoirRepository extends Neo4jRepository<ReservoirNode,Long> {
    @Query("Match (r:Reservoir) return r ")
    public List<ReservoirNode> selectAll();
    @Query("Match (r:Reservoir) where r.name={name} return r ")
    public ReservoirNode selectByName(@Param("name") String name);



}
