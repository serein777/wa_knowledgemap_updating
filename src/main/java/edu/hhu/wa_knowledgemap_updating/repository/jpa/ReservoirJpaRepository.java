package edu.hhu.wa_knowledgemap_updating.repository.jpa;

import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReservoirJpaRepository extends JpaRepository<Reservoir,Long> {

    Reservoir findByName(@Param("name") String name);
    @Query(value = "select id,name,max_water_level,longitude,latitude,elevation," +
            "create_time,update_time"+
            " from wa_reservoir where id=:id",nativeQuery = true)
    Reservoir selectById(@Param("id") long id);

    @Query(value ="select id,name,max_water_level,longitude,latitude,elevation," +
            "create_time,update_time"+
            " from wa_reservoir where name like  concat('%',:keyword,'%')",nativeQuery = true)
    List<Reservoir> selectByKeyWord(@Param("keyword") String keyword);

//    @Query(value = "select id,name,max_water_level,longitude,latitude,elevation," +
//            "date_format(create_time,'%Y-%m-%d %H:%i:%s') as create_time,"+
//            "date_format(update_time,'%Y-%m-%d %H:%i:%s') as update_time "+
//            "from wa_reservoir",nativeQuery = true)
//    List<Reservoir> selectAll();
}
