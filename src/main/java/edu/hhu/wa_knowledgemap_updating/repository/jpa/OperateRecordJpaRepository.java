package edu.hhu.wa_knowledgemap_updating.repository.jpa;

import edu.hhu.wa_knowledgemap_updating.entity.OperateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperateRecordJpaRepository extends JpaRepository<OperateRecord,Long> {

    @Query(value = "select id,operate_type,data_type,operate_detail,operate_time from wa_operate_record order by operate_time desc"
            ,nativeQuery = true)
    List<OperateRecord> getAllOperateRecord();
}
