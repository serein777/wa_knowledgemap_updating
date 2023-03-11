package edu.hhu.wa_knowledgemap_updating.service.impl;

import edu.hhu.wa_knowledgemap_updating.dto.ReservoirUpdateDto;
import edu.hhu.wa_knowledgemap_updating.entity.ReservoirNode;
import edu.hhu.wa_knowledgemap_updating.repository.ReservoirRepository;
import edu.hhu.wa_knowledgemap_updating.service.ReservoirService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReservoirServiceImpl  implements ReservoirService {
    @Autowired
    ReservoirRepository reservoirRepository;
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

    public boolean updateIncrementalInfo(ReservoirUpdateDto nodeDto){
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
}
