package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
@Entity
@Table(name = "wa_stream")
@Data
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int level;
    private String type;
    private  int length;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "update_time")
    private Timestamp updateTime;
}
