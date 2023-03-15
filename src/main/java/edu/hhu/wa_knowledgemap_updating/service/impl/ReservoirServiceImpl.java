package edu.hhu.wa_knowledgemap_updating.service.impl;

import edu.hhu.wa_knowledgemap_updating.dto.ReservoirDto;
import edu.hhu.wa_knowledgemap_updating.dto.ReservoirKettleDto;
import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.repository.ReservoirRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.ReservoirJpaRepository;
import edu.hhu.wa_knowledgemap_updating.service.ReservoirService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class ReservoirServiceImpl  implements ReservoirService {
    @Autowired
    ReservoirRepository reservoirRepository;

    @Autowired
    ReservoirJpaRepository reservoirJPARepository;

    public static final String DATE_FORMAT= "yyyy-MM-dd HH:mm:ss";
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
    public List<ReservoirNode> getAllReservoirNode() {
        List<ReservoirNode> list = reservoirRepository.selectAll();
        return  list;
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
        ReservoirNode  beforeNode = reservoirRepository.selectByName(nodeDto.getName());
        if(method.equals("create")){
            if(beforeNode!=null){
                log.info("the node exists");
                return  false;
            }
            if(lastUpdateTime==null||lastUpdateTime.trim().length()==0){
                log.info("ReservoirServiceImpl updateIncremental Info lastUpdateTime is null ");
                return  false;
            }
            ReservoirNode reservoirNode=new ReservoirNode();
            reservoirNode.setName(nodeDto.getName());
            reservoirNode.setMysqlId(nodeDto.getMysqlId());
            reservoirNode.setLastUpdateTime(nodeDto.getLastUpdateTime());
            reservoirNode.setMaxWaterLevel(nodeDto.getMaxWaterLevel());
            reservoirRepository.save(reservoirNode);
        }
        else {
            //查询出之前的节点
            if(null==beforeNode){
                log.info("can find the reservoir node ");
                return  false;
            }
            if(method.equals("update")){
                if(lastUpdateTime==null||lastUpdateTime.trim().length()==0){
                    log.info("ReservoirServiceImpl updateIncremental Info lastUpdateTime is null ");
                    return  false;
                }
                beforeNode.setMaxWaterLevel(nodeDto.getMaxWaterLevel());
                beforeNode.setLastUpdateTime(nodeDto.getLastUpdateTime());
                beforeNode.setMysqlId(nodeDto.getMysqlId());
                reservoirRepository.save(beforeNode);
            }
            else if(method.equals("delete")){
                reservoirRepository.delete(beforeNode);
            }
        }
        return  true;
    }

    public RespBean list(){
        List<Reservoir> list = reservoirJPARepository.findAll();
        List<ReservoirDto> dtoList=new ArrayList<>();
        for(Reservoir r:list){
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
        Timestamp createTime=new Timestamp(new Date().getTime());
        Timestamp updateTime=new Timestamp(new Date().getTime());
        reservoirJPARepository.create(reservoirDto.getName(),reservoirDto.getMaxWaterLevel(),
                createTime.toString(),updateTime.toString());
        return RespBean.success();
    }

    @Override
    public RespBean update(ReservoirDto reservoirDto) {
        //查询是否存在该水库
        Reservoir  before = reservoirJPARepository.selectById(reservoirDto.getId());
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
        before.setUpdateTime(new Timestamp(new Date().getTime()));
        reservoirJPARepository.save(before);
        return RespBean.success();
    }


    public  ReservoirDto buildDto(Reservoir reservoir){
        ReservoirDto reservoirDto=new ReservoirDto();
        reservoirDto.setId(reservoir.getId());
        reservoirDto.setMaxWaterLevel(reservoir.getMaxWaterLevel());
        reservoirDto.setName(reservoir.getName());
        log.info("format date...");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        if(reservoir.getUpdateTime()!=null){
            String date_s1=simpleDateFormat.format(reservoir.getUpdateTime());
            reservoirDto.setUpdateTime(date_s1);
        }
        if(reservoir.getCreateTime()!=null){
            String date_s2=simpleDateFormat.format(reservoir.getUpdateTime());
            reservoirDto.setCreateTime(date_s2);
        }
        return reservoirDto;
    }

    public RespBean delete(long id) {
        //查询是否存在该水库
        Reservoir  before = reservoirJPARepository.selectById(id);
        if(before==null){
            return new RespBean(400L, "不存在该水库", null);
        }
        reservoirJPARepository.delete(before);
        return RespBean.success();
    }

}
