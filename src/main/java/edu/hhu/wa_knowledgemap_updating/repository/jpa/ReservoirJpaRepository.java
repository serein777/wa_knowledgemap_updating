package edu.hhu.wa_knowledgemap_updating.repository.jpa;

import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface ReservoirJpaRepository extends JpaRepository<Reservoir,Long> {

    Reservoir findByName(@Param("name") String name);
    @Transactional
    @Modifying
    @Query(value = "insert into wa_reservoir values(null,:name,:max_water_level,:create_time,:update_time);",nativeQuery = true)
    int create(@Param("name") String name,@Param("max_water_level") Double maxWaterLevel,
               @Param("create_time") String createTIme,@Param("update_time")String updateTime);

    @Query(value = "select id,name,max_water_level,create_time,update_time from wa_reservoir where id=:id",nativeQuery = true)
    Reservoir selectById(@Param("id") long id);
}
