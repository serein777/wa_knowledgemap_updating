package edu.hhu.wa_knowledgemap_updating.dto;

import lombok.Data;
/*
用于封装增量更新的数据
 */
@Data
public class StreamKettleDto {
    private Long id;
    private Long mysqlId;
    private String name;
    private String type;
    private Integer level;
    private Integer length;
    private String lastUpdateTime;
    private String method;
    @Override
    public   String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("id:").append(this.getMysqlId()).append(",");
        sb.append("name:").append(this.name).append(",");
        sb.append("type:").append(this.type).append(",");
        sb.append("level:").append(this.level).append(",");
        sb.append("length:").append(this.length).append(",");
        sb.append("updateTime:").append(this.getLastUpdateTime());
        sb.append("}");
        return sb.toString();
    }
}
