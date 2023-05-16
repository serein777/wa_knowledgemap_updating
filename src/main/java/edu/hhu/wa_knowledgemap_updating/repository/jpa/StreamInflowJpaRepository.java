package edu.hhu.wa_knowledgemap_updating.repository.jpa;

import edu.hhu.wa_knowledgemap_updating.entity.StreamInflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StreamInflowJpaRepository extends JpaRepository<StreamInflow,Long> {
    StreamInflow findByInflowStartName(@Param("inflow_start_name") String name);

    StreamInflow findByInflowEndName(@Param("inflow_end_name") String name);

    StreamInflow findByInflowStartId(@Param("inflow_start_id") Long id);

    StreamInflow findByInflowEndId(@Param("inflow_end_id") Long id);


    @Query(value = "select  id ,inflow_start_id,inflow_end_id,inflow_start_name,inflow_end_name ,create_time,update_time " +
            " from wa_stream_inflow where inflow_start_id=:inflow_start_id " +
            "and inflow_end_id=:inflow_end_id",nativeQuery = true)
    StreamInflow selectByStartIdAndEndId(@Param("inflow_start_id") Long inflowStartId, @Param("inflow_end_id") Long inflowEndId );

    @Query(value = "select  id ,inflow_start_id,inflow_end_id,inflow_start_name,inflow_end_name ,create_time,update_time " +
            " from wa_stream_inflow where inflow_start_name like concat('%',:keyword,'%') or inflow_end_name like concat('%',:keyword,'%') ",nativeQuery = true)
    List<StreamInflow> selectByKeyWord(@Param("keyword") String keyWord);

    @Query(value = "delete  from  wa_stream_inflow where inflow_start_id =:id or inflow_end_id =:id ",nativeQuery = true)
    @Transactional
    @Modifying
    int deleteByInflowStartIdOrInflowEndId(long id);
    @Query(value = "update wa_stream_inflow  set inflow_start_name=:inflow_start_name where inflow_start_id=:inflow_start_id"
            ,nativeQuery = true)
    @Transactional
    @Modifying
    int  updateInflowStartName(@Param("inflow_start_id") Long inflowStartId,@Param("inflow_start_name")String inflowStartName);

    @Query(value = "update wa_stream_inflow  set inflow_end_name=:inflow_end_name where inflow_end_id=:inflow_end_id"
            ,nativeQuery = true)
    @Transactional
    @Modifying
    int  updateInflowEndName(@Param("inflow_end_id") Long inflowEndId,@Param("inflow_end_name")String inflowEndName);
}
