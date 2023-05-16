package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 数据更新记录
 */
@Table(name = "wa_operate_record")
@Data
@Entity
public class OperateRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_type")
    private String dataType;
    @Column(name = "operate_type")
    private String operateType;
    @Column(name = "operate_detail")
    private String operateDetail;
    @Column(name = "operate_time")
    private Date operateTime;
}
