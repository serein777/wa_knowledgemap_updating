package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;

@Data
public class RelationDto {
    private Long id;
    private String startNodeName;
    private String endNodeName;
    private String relation;

}
