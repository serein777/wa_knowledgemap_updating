package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "wa_stream_inflow")
@Data
public class StreamInflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long  id;
    private  long inflowStartId;
    private  long inflowEndId;
    private  String inflowStartName;
    private  String inflowEndName;
}
