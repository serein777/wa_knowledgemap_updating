package edu.hhu.wa_knowledgemap_updating.dto;

import edu.hhu.wa_knowledgemap_updating.entity.StreamInflowRelation;
import edu.hhu.wa_knowledgemap_updating.entity.StreamNode;
import lombok.Data;

import java.util.List;
@Data
public class StreamNodeDto {
    List<StreamNode> streamNodeList;
    List<StreamInflowNodeDto> relationList;
}
