package edu.hhu.wa_knowledgemap_updating.service.impl;


import edu.hhu.wa_knowledgemap_updating.dto.StreamInflowKettleDto;
import edu.hhu.wa_knowledgemap_updating.entity.StreamInflowRelation;
import edu.hhu.wa_knowledgemap_updating.entity.StreamNode;
import edu.hhu.wa_knowledgemap_updating.repository.StreamInflowRepository;
import edu.hhu.wa_knowledgemap_updating.repository.StreamRepository;
import edu.hhu.wa_knowledgemap_updating.service.StreamInflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StreamInflowServiceImpl implements StreamInflowService {
    @Autowired
    StreamRepository streamRepository;
    @Autowired
    StreamInflowRepository streamInflowRepository;
    @Override
    public boolean updateIncrementalInfo(StreamInflowKettleDto nodeDto) {
        //获取增量更新方法 创建、更新、删除
        String method=nodeDto.getMethod();
        String inflowStartName= nodeDto.getInflowStartName();
        String inflowEndName= nodeDto.getInflowEndName();
        if(method==null||method.trim().length()==0){
            log.info(" StreamInflowServiceImpl updateIncrementalInfo method is null ");
            return  false;
        }
        if(inflowStartName==null||inflowStartName.trim().length()==0){
            log.info(" StreamInflowServiceImpl updateIncrementalInfo inflow start name is null  ");
            return  false;
        }
        if(inflowEndName==null||inflowEndName.trim().length()==0){
            log.info(" StreamInflowServiceImpl updateIncrementalInfo inflow end name is null  ");
            return  false;
        }
        //查询出之前的节点
        StreamNode startNode = streamRepository.selectByName(nodeDto.getInflowStartName());
        StreamNode endNode = streamRepository.selectByName(nodeDto.getInflowEndName());
        if(null==startNode||null==endNode){
            log.info("StreamInflowServiceImpl the start node or the end node is null");
            return  false;
        }
        StreamInflowRelation beforeRelation=streamInflowRepository.findRelation(startNode,endNode,"流入");

        if(method.equals("create")){
            if(beforeRelation!=null){
                log.info("the inflow relation exists");
                return  false;
            }
            StreamInflowRelation streamInflowRelation=new StreamInflowRelation();
            streamInflowRelation.setStartNode(startNode);
            streamInflowRelation.setEndNode(endNode);
            streamInflowRelation.setRelation("流入");
            streamInflowRepository.save(streamInflowRelation);
        }
        else{
            if(method.equals("update")){
               log.info("update......");
            }
            else if(method.equals("delete")){
            if(beforeRelation==null){
                log.info("the inflow relation is null");
                return  false;
            }
            streamInflowRepository.delete(beforeRelation);
        }
        }
        return  true;
    }


}
