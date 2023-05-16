package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;

@Data
public class StreamInflowKettleDto {
    Long  inflowStartId;
    Long  inflowEndId;
    String inflowStartName;
    String inflowEndName;
    String method;
    Long   mysqlId;
    @Override
    public   String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("id:").append(this.getMysqlId()).append(",");
        sb.append("inflowStartId:").append(this.getInflowStartId()).append(",");
        sb.append("inflowEndId:").append(this.getInflowEndId()).append(",");
        sb.append("inflowStartName:").append(this.getInflowStartName()).append(",");
        sb.append("inflowEndName:").append(this.getInflowEndName());
        sb.append("}");
        return sb.toString();
    }
}
