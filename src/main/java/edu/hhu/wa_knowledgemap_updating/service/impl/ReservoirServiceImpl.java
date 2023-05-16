package edu.hhu.wa_knowledgemap_updating.service.impl;

import edu.hhu.wa_knowledgemap_updating.dto.ReservoirDto;
import edu.hhu.wa_knowledgemap_updating.dto.ReservoirKettleDto;
import edu.hhu.wa_knowledgemap_updating.entity.OperateRecord;
import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.repository.ReservoirRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.OperateRecordJpaRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.ReservoirJpaRepository;
import edu.hhu.wa_knowledgemap_updating.service.ReservoirService;
import edu.hhu.wa_knowledgemap_updating.utils.DateFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReservoirServiceImpl  implements ReservoirService {
    @Autowired
    ReservoirRepository reservoirRepository;

    @Autowired
    ReservoirJpaRepository reservoirJPARepository;

    @Autowired
    OperateRecordJpaRepository operateRecordJpaRepository;

    @Override
    public boolean updateMaxWaterLevel(ReservoirNode reservoirNode) {
        if(reservoirNode.getName()==null||reservoirNode.getName().trim().length()==0) {
            log.info("ReservoirServiceImpl updateMaxWaterLevel name is null ");
            return  false;
        }
        if(reservoirNode.getLastUpdateTime()==null||reservoirNode.getLastUpdateTime().trim().length()==0) {
            log.info("ReservoirServiceImpl updateMaxWaterLevel lastUpdateTime is null ");
            return  false;
        }
        //查询出之前的节点
        ReservoirNode  beforeNode = reservoirRepository.selectByName(reservoirNode.getName());
        if(null==beforeNode){
            log.info("can find the reservoir node ");
            return  false;
        }
        beforeNode.setMaxWaterLevel(reservoirNode.getMaxWaterLevel());
        beforeNode.setLastUpdateTime(reservoirNode.getLastUpdateTime());
        ReservoirNode save = reservoirRepository.save(beforeNode);
        if(save==null) return  false;
        return  true;
    }

    @Override
    public RespBean getAllReservoirNode() {
        List<ReservoirNode> list = reservoirRepository.selectAll();
        return  RespBean.success(list);
    }

    @Override
    public ReservoirNode findNodeByName(String name) {
       return reservoirRepository.selectByName(name);
    }

    public boolean updateIncrementalInfo(ReservoirKettleDto nodeDto){
        if(nodeDto.getName()==null||nodeDto.getName().trim().length()==0) {
            log.info("ReservoirServiceImpl updateIncrementalInfo  name is null ");
            return  false;
        }
        //获取增量更新方法 创建、更新、删除
        String method=nodeDto.getMethod();
        String lastUpdateTime=nodeDto.getLastUpdateTime();
        if(method==null||method.trim().length()==0){
            log.info("ReservoirServiceImpl updateIncremental Info method is null ");
            return  false;
        }

        ReservoirNode  sameNameNode = reservoirRepository.selectByName(nodeDto.getName());
        //操作记录对象
        OperateRecord operateRecord=new OperateRecord();

        StringBuilder opDetail=new StringBuilder();
        if(method.equals("create")){
        //创建流程
            if(sameNameNode!=null){
                log.info("the node exists");
                return  false;
            }
            if(lastUpdateTime==null||lastUpdateTime.trim().length()==0){
                log.info("ReservoirServiceImpl updateIncremental Info lastUpdateTime is null ");
                return  false;
            }
            ReservoirNode reservoirNode=new ReservoirNode();
            operateRecord.setOperateType("创建");
            opDetail.append("创建水坝节点:");
            opDetail.append(nodeDto.toString());
            reservoirNode.setName(nodeDto.getName());
            reservoirNode.setMysqlId(nodeDto.getMysqlId());
            reservoirNode.setLastUpdateTime(nodeDto.getLastUpdateTime());
            reservoirNode.setMaxWaterLevel(nodeDto.getMaxWaterLevel());
            reservoirNode.setLongitude(nodeDto.getLongitude());
            reservoirNode.setLatitude(nodeDto.getLatitude());
            reservoirNode.setElevation(nodeDto.getElevation());
            reservoirRepository.save(reservoirNode);
        }
        else {
            //查询出之前的节点
            Optional<ReservoirNode> op = reservoirRepository.selectByMySqlId(nodeDto.getMysqlId());
            if(!op.isPresent()){
                log.info("can find the reservoir node ");
                return  false;
            }
            ReservoirNode beforeNode=op.get();
            if(method.equals("update")){
                if(lastUpdateTime==null||lastUpdateTime.trim().length()==0){
                    log.info("ReservoirServiceImpl updateIncremental Info lastUpdateTime is null ");
                    return  false;
                }
                //更新流程
                if(sameNameNode!=null&&!sameNameNode.getId().equals(beforeNode.getId())){
                    log.info("the node exists");
                    return  false;
                }
                operateRecord.setOperateType("更新");
                opDetail.append("更新水坝节点:{");
                opDetail.append(checkDiff(beforeNode,nodeDto));
                opDetail.append("}");
                beforeNode.setName(nodeDto.getName());
                beforeNode.setMaxWaterLevel(nodeDto.getMaxWaterLevel());
                beforeNode.setLastUpdateTime(nodeDto.getLastUpdateTime());
                beforeNode.setMysqlId(nodeDto.getMysqlId());
                beforeNode.setLongitude(nodeDto.getLongitude());
                beforeNode.setLatitude(nodeDto.getLatitude());
                beforeNode.setElevation(nodeDto.getElevation());
                reservoirRepository.save(beforeNode);
            }
            else if(method.equals("delete")){
                //删除
                operateRecord.setOperateType("删除");
                opDetail.append("删除水坝节点:");
                opDetail.append(nodeDto.toString());
                reservoirRepository.delete(sameNameNode);

            }
        }
        //插入到操作记录表
        operateRecord.setOperateTime(new Date());
        operateRecord.setDataType("水坝");
        operateRecord.setOperateDetail(opDetail.toString());
        operateRecordJpaRepository.save(operateRecord);
        return  true;
    }
  //比较增量数组字段变化
    public String checkDiff(ReservoirNode beforeNode,ReservoirKettleDto nodeDto){
        StringBuilder sb =new StringBuilder();
        if(!beforeNode.getName().equals(nodeDto.getName())){
            sb.append("name:");
            sb.append(beforeNode.getName()).append("->").append(nodeDto.getName()).append(",");
        }
        if(beforeNode.getMaxWaterLevel()!=nodeDto.getMaxWaterLevel()){
            sb.append("maxWaterLevel:");
            sb.append(beforeNode.getMaxWaterLevel()).append("->").append(nodeDto.getMaxWaterLevel()).append(",");
        }
        if(beforeNode.getLongitude()!=nodeDto.getLongitude()){
            sb.append("longitude:");
            sb.append(beforeNode.getLongitude()).append("->").append(nodeDto.getLongitude()).append(",");
        }
        if(beforeNode.getLatitude()!=(nodeDto.getLatitude())){
            sb.append("latitude:");
            sb.append(beforeNode.getLatitude()).append("->").append(nodeDto.getLatitude()).append(",");
        }
        if(beforeNode.getElevation()!=nodeDto.getElevation()){
            sb.append("elevation:");
            sb.append(beforeNode.getElevation()).append("->").append(nodeDto.getElevation()).append(",");
        }
        if(sb.length()==0){
            return "无数据变动";
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }
    public RespBean list(){
        List<Reservoir> list = reservoirJPARepository.findAll();
        List<ReservoirDto> dtoList=new ArrayList<>();
        for (Reservoir r:list){
            dtoList.add(buildDto(r));
        }
        return  RespBean.success(dtoList);
    }

   //通过名字查找数据库的水坝数据
    @Override
    public RespBean findByName(String name) {
       Reservoir reservoir = reservoirJPARepository.findByName(name);
        if(reservoir==null){
            return  null;
        }
        ReservoirDto reservoirDto = buildDto(reservoir);
        return RespBean.success(reservoirDto);
    }

    @Override
    public RespBean create(ReservoirDto reservoirDto) {
        //查询是否有同名字的
        Reservoir before=reservoirJPARepository.findByName(reservoirDto.getName());
        if(before!=null) {
            return new RespBean(400L, "存在同名的水坝，请重新输入", null);
        }
        Reservoir reservoir=new Reservoir();
        reservoir.setName(reservoirDto.getName());
        reservoir.setMaxWaterLevel(reservoirDto.getMaxWaterLevel());
        reservoir.setLongitude(reservoirDto.getLongitude());
        reservoir.setLatitude(reservoirDto.getLatitude());
        reservoir.setElevation(reservoirDto.getElevation());
        reservoir.setCreateTime(new Date());
        reservoir.setUpdateTime(new Date());
        reservoirJPARepository.save(reservoir);
        return RespBean.success();
    }

    @Override
    public RespBean update(ReservoirDto reservoirDto) {
        //查询是否存在该水库
        Reservoir before = reservoirJPARepository.selectById(reservoirDto.getId());
        if(before==null){
            return new RespBean(400L, "不存在该水库", null);
        }
        //查询是否有同名字的
        Reservoir same=reservoirJPARepository.findByName(reservoirDto.getName());
        if(same!=null&&same.getId()!=before.getId()) {
            return new RespBean(400L, "存在同名的水坝，请重新输入", null);
        }
        before.setName(reservoirDto.getName());
        before.setMaxWaterLevel(reservoirDto.getMaxWaterLevel());
        before.setUpdateTime(new Date());
        before.setLongitude(reservoirDto.getLongitude());
        before.setLatitude(reservoirDto.getLatitude());
        before.setElevation(reservoirDto.getElevation());
        reservoirJPARepository.save(before);
        return RespBean.success();
    }


    public ReservoirDto buildDto(edu.hhu.wa_knowledgemap_updating.entity.Reservoir reservoir){
        ReservoirDto reservoirDto=new ReservoirDto();
        reservoirDto.setId(reservoir.getId());
        reservoirDto.setMaxWaterLevel(reservoir.getMaxWaterLevel());
        reservoirDto.setName(reservoir.getName());
        reservoirDto.setLongitude(reservoir.getLongitude());
        reservoirDto.setLatitude(reservoir.getLatitude());
        reservoirDto.setElevation(reservoir.getElevation());
        if(reservoir.getUpdateTime()!=null){
            String date_s1= DateFormatUtil.formatDate(reservoir.getUpdateTime());
            reservoirDto.setUpdateTime(date_s1);
        }
        if(reservoir.getCreateTime()!=null){
            String date_s2=DateFormatUtil.formatDate(reservoir.getCreateTime());
            reservoirDto.setCreateTime(date_s2);
        }
        return reservoirDto;
    }

    public RespBean delete(long id) {
        //查询是否存在该水库
        Reservoir before = reservoirJPARepository.selectById(id);
        if(before==null){
            return new RespBean(400L, "不存在该水库", null);
        }
        reservoirJPARepository.delete(before);
        return RespBean.success();
    }

    @Override
    public RespBean findByKeyWord(String keyWord) {
        if(keyWord.trim().length()==0){
            return list();
        }
        else{
            List<Reservoir> list= reservoirJPARepository.selectByKeyWord(keyWord);
            List<ReservoirDto> dtoList=new ArrayList<>();
            for(Reservoir r:list){
                dtoList.add(buildDto(r));
            }
            return  RespBean.success(dtoList);
        }
    }

    @Override
    public RespBean selectNodeByKeyWord(String keyWord) {
        if(keyWord.trim().length()==0){
            return  getAllReservoirNode();
        }
        List<ReservoirNode> reservoirNodeList = reservoirRepository.selectByKeyWord(keyWord);
        return RespBean.success(reservoirNodeList);
    }

}
