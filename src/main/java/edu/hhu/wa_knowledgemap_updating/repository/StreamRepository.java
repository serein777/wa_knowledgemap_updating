package edu.hhu.wa_knowledgemap_updating.repository;
import edu.hhu.wa_knowledgemap_updating.entity.StreamNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreamRepository extends  Neo4jRepository<StreamNode, Long> {
    @Query("match (r:Stream) where r.name={name}  return r")
    public StreamNode selectByName(@Param("name") String name);

    @Query("match (r:Stream)  return r")
    List<StreamNode> getAll();
}
