package edu.hhu.wa_knowledgemap_updating.dto;

import edu.hhu.wa_knowledgemap_updating.utils.DateFormatUtil;
import lombok.Data;

/**
 * 用于封装增量数据
 */
@Data
public class ReservoirKettleDto {
    private Long id;
    private Long mysqlId;
    private String name;
    private Double maxWaterLevel;
    private String lastUpdateTime;
    //经度
    private Double longitude;
    //纬度
    private Double latitude;
    //高程
    private int elevation;
    private String method;
    @Override
    public   String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("id:").append(this.getMysqlId()).append(",");
        sb.append("name:").append(this.name).append(",");
        sb.append("maxWaterLevel:").append(this.maxWaterLevel).append(",");
        sb.append("longitude:").append(this.longitude).append(",");
        sb.append("latitude:").append(this.latitude).append(",");
        sb.append("updateTime:").append(this.getLastUpdateTime());
        sb.append("}");
        return sb.toString();
    }
}
