package edu.hhu.wa_knowledgemap_updating.repository;

import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservoirRepository extends Neo4jRepository<ReservoirNode,Long> {
    @Query("Match (r:Reservoir) return r ")
    public List<ReservoirNode> selectAll();
    @Query("Match (r:Reservoir) where r.name={name} return r ")
    public ReservoirNode selectByName(@Param("name") String name);

    @Query("match (r:Reservoir) where r.name =~'.*+{keyword}+.*' return r")
    List<ReservoirNode> selectByKeyWord(@Param("keyword") String keyWord);
    @Query("Match (r:Reservoir) where r.mysql_id={mysql_id} return r ")
    Optional<ReservoirNode> selectByMySqlId(@Param("mysql_id") Long mysqlId);
}
