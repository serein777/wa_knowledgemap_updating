package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "wa_stream_inflow")
@Data
public class StreamInflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  Long inflowStartId;
    private  Long inflowEndId;
    private  String inflowStartName;
    private  String inflowEndName;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "update_time")
    private Timestamp updateTime;
}
