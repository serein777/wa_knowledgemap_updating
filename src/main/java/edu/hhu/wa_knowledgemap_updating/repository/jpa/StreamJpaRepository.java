package edu.hhu.wa_knowledgemap_updating.repository.jpa;

import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import edu.hhu.wa_knowledgemap_updating.entity.Stream;
import org.pentaho.di.trans.step.StepAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreamJpaRepository extends JpaRepository<Stream,Long> {
    Stream findByName(@Param("name") String name);

    @Query(value = "select id,name,type,level,length,create_time,update_Time from wa_stream where name like concat('%',:keyword,'%') ",nativeQuery = true)
    List<Stream> selectByKeyWord(@Param("keyword") String keyWord);

}
