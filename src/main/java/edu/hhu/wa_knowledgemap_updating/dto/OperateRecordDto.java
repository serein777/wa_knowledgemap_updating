package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class OperateRecordDto {
    private Long id;
    private String dataType;
    private String operateType;
    private String operateDetail;
    private String operateTime;
}
