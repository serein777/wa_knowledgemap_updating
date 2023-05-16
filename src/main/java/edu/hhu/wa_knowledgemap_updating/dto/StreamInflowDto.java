package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
public class StreamInflowDto {
    private  long  id;
    private  long inflowStartId;
    private  long inflowEndId;
    private  String inflowStartName;
    private  String inflowEndName;
    private String createTime;
    private String updateTime;
}
