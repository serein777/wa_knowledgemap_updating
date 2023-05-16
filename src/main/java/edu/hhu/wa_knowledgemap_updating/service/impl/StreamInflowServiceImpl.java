package edu.hhu.wa_knowledgemap_updating.service.impl;


import edu.hhu.wa_knowledgemap_updating.dto.StreamInflowDto;
import edu.hhu.wa_knowledgemap_updating.dto.StreamInflowKettleDto;
import edu.hhu.wa_knowledgemap_updating.dto.StreamInflowNodeDto;
import edu.hhu.wa_knowledgemap_updating.entity.*;
import edu.hhu.wa_knowledgemap_updating.repository.StreamInflowRepository;
import edu.hhu.wa_knowledgemap_updating.repository.StreamRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.OperateRecordJpaRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.StreamInflowJpaRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.StreamJpaRepository;
import edu.hhu.wa_knowledgemap_updating.service.StreamInflowService;
import edu.hhu.wa_knowledgemap_updating.utils.DateFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class StreamInflowServiceImpl implements StreamInflowService {
    @Autowired
    StreamRepository streamRepository;
    @Autowired
    StreamInflowRepository streamInflowRepository;
    @Autowired
    StreamInflowJpaRepository streamInflowJpaRepository;
    @Autowired
    StreamJpaRepository streamJpaRepository;
    @Autowired
    OperateRecordJpaRepository operateRecordJpaRepository;
    @Override
    public boolean updateIncrementalInfo(StreamInflowKettleDto nodeDto) {
        //获取增量更新方法 创建、更新、删除
        String method=nodeDto.getMethod();
        String inflowStartName= nodeDto.getInflowStartName();
        String inflowEndName= nodeDto.getInflowEndName();
        Long mysqlId=nodeDto.getMysqlId();
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
        if(mysqlId==null){
            log.info(" StreamInflowServiceImpl updateIncrementalInfo inflow mysql id is null  ");
            return  false;
        }
        //查询出首尾节点
        StreamNode startNode = streamRepository.selectByName(nodeDto.getInflowStartName());
        StreamNode endNode = streamRepository.selectByName(nodeDto.getInflowEndName());
        if(null==startNode||null==endNode){
            log.info("StreamInflowServiceImpl the start node or the end node is null");
            return  false;
        }
        if(startNode.equals(endNode)){
            log.info("StreamInflowServiceImpl can not build with the same start node and end node");
            return  false;
        }
        //操作记录对象
        OperateRecord operateRecord=new OperateRecord();
        StringBuilder opDetail=new StringBuilder();
        StreamInflowRelation sameRelation=streamInflowRepository.findRelation(startNode,endNode,"流入");
        if(method.equals("create")){
            if(sameRelation!=null){
                log.info("the inflow relation exists");
                return  false;
            }
            operateRecord.setOperateType("创建");
            opDetail.append("创建河流流入关系:");
            opDetail.append(nodeDto.toString());
            StreamInflowRelation streamInflowRelation=new StreamInflowRelation();
            streamInflowRelation.setStartNode(startNode);
            streamInflowRelation.setEndNode(endNode);
            streamInflowRelation.setRelation("流入");
            streamInflowRelation.setMysqlId(mysqlId);
            streamInflowRepository.save(streamInflowRelation);
        }
        else{
            StreamInflowRelation beforeRelation=streamInflowRepository.findByMysqlId(nodeDto.getMysqlId());
            if(beforeRelation==null){
                log.info(" StreamInflowServiceImpl updateIncrementalInfo inflow relation not exist  ");
                return  false;
            }
            if(method.equals("update")){
                if(beforeRelation.startNode.getMysqlId()==nodeDto.getInflowStartId()
                        &&beforeRelation.getEndNode().getMysqlId()==nodeDto.getInflowEndId()){
                    log.info("流入起点和流入终点没有变动，无需更新");
                    return false;
                }
                //删除之前的关系
                streamInflowRepository.delete(beforeRelation);
                //创建新的关系
                operateRecord.setOperateType("更新");
                opDetail.append("修改河流流入关系:");
                opDetail.append("{");
                opDetail.append(checkDiff(beforeRelation,nodeDto));
                opDetail.append("}");
                StreamInflowRelation streamInflowRelation=new StreamInflowRelation();
                streamInflowRelation.setStartNode(startNode);
                streamInflowRelation.setEndNode(endNode);
                streamInflowRelation.setRelation("流入");
                streamInflowRelation.setMysqlId(mysqlId);
                streamInflowRepository.save(streamInflowRelation);
            }
            else if(method.equals("delete")){
                operateRecord.setOperateType("删除");
                opDetail.append("删除河流流入关系:");
                opDetail.append(nodeDto.toString());
                //删除该关系
                streamInflowRepository.delete(beforeRelation);
            }
        }
        operateRecord.setOperateTime(new Date());
        operateRecord.setDataType("河流流入关系");
        operateRecord.setOperateDetail(opDetail.toString());
        operateRecordJpaRepository.save(operateRecord);
        return  true;
    }
    public  String checkDiff(StreamInflowRelation beforeRelation, StreamInflowKettleDto nodeDto){
        StringBuilder sb=new StringBuilder();
        sb.append("修改前:[");
        sb.append("id:").append(beforeRelation.getMysqlId()).append(",");
        sb.append("inflowStartId:").append(beforeRelation.getStartNode().getMysqlId()).append(",");
        sb.append("inflowEndId;:").append(beforeRelation.getEndNode().getMysqlId()).append(",");
        sb.append("inflowStartName;:").append(beforeRelation.getStartNode().getName()).append(",");
        sb.append("inflowEndName;:").append(beforeRelation.getEndNode().getName());
        sb.append("]   ");
        String nodeDtoInfo=nodeDto.toString();
        sb.append("修改后:[").append(nodeDtoInfo.substring(1,nodeDtoInfo.length()-1)).append("]");
        return sb.toString();
    }
    @Override
    public RespBean list() {
        List<StreamInflow> list=streamInflowJpaRepository.findAll();
        List<StreamInflowDto> streamDtoList=new ArrayList<>();
        for (StreamInflow s:list){
            streamDtoList.add(buildDto(s));
        }
        return  RespBean.success(streamDtoList);
    }
    @Override
    public RespBean update(StreamInflowDto streamInflowDto) {
        //查询数据库是否有该id的流入关系
        Optional<StreamInflow> op = streamInflowJpaRepository.findById(streamInflowDto.getId());
        if(!op.isPresent()){
            return  new RespBean(400,"不存在该id的河流流入关系",null);
        }
        //先查询出是否存在起点河流，终点河流
        Stream start = streamJpaRepository.findByName(streamInflowDto.getInflowStartName());
        if(start==null){
            return  new RespBean(400,"不存在流入起点为该名字的河流",null);
        }
        Stream end = streamJpaRepository.findByName(streamInflowDto.getInflowEndName());
        if(end==null){
            return  new RespBean(400,"不存在流入终点为该名字的河流",null);
        }
        //判断起点和终点是否相同
        if(start.getId()==end.getId()){
            return  new RespBean(400,"不能建立该起点和终点一样的流入关系",null);
        }
        StreamInflow before=op.get();
        //查询是否存在该关系
        StreamInflow same=streamInflowJpaRepository.selectByStartIdAndEndId(start.getId(),end.getId());
        if(same!=null&&same.getId()!=streamInflowDto.getId()){
            return  new RespBean(400,"已经存在该流入关系",null);
        }
        before.setInflowStartId(start.getId());
        before.setInflowEndId(end.getId());
        before.setInflowStartName(streamInflowDto.getInflowStartName());
        before.setInflowEndName(streamInflowDto.getInflowEndName());
        Timestamp updateTime=new Timestamp(new Date().getTime());
        before.setUpdateTime(updateTime);
        streamInflowJpaRepository.save(before);
        return  RespBean.success();
    }

    @Override
    public RespBean delete(long id) {
        Optional<StreamInflow> op = streamInflowJpaRepository.findById(id);
        if(!op.isPresent()){
            return  new RespBean(400,"不存在该id的河流流入关系",null);
        }
        streamInflowJpaRepository.delete(op.get());
        return  RespBean.success();
    }

    @Override
    public RespBean create(StreamInflowDto streamInflowDto) {
        //先查询出是否存在起点河流，终点河流
        Stream start = streamJpaRepository.findByName(streamInflowDto.getInflowStartName());
        if(start==null){
            return  new RespBean(400,"不存在流入起点为该名字的河流",null);
        }
        Stream end = streamJpaRepository.findByName(streamInflowDto.getInflowEndName());
        if(end==null){
            return  new RespBean(400,"不存在流入终点为该名字的河流",null);
        }
        if(start.getId()==end.getId()){
            return  new RespBean(400,"不能建立该起点和终点一样的流入关系",null);
        }
        //查询是否存在该关系
        StreamInflow before=streamInflowJpaRepository.selectByStartIdAndEndId(start.getId(),end.getId());
        if(before!=null){
            return  new RespBean(400,"已经存在该流入关系",null);
        }
        StreamInflow streamInflow=new StreamInflow();
        streamInflow.setInflowStartId(start.getId());
        streamInflow.setInflowEndId(end.getId());
        streamInflow.setInflowStartName(streamInflowDto.getInflowStartName());
        streamInflow.setInflowEndName(streamInflowDto.getInflowEndName());
        Timestamp createTime=new Timestamp(new Date().getTime());
        Timestamp updateTime=new Timestamp(new Date().getTime());
        streamInflow.setCreateTime(createTime);
        streamInflow.setUpdateTime(updateTime);
        streamInflowJpaRepository.save(streamInflow);
        return RespBean.success();
    }

    @Override
    public RespBean findByKeyWord(String keyWord) {
        if(keyWord.trim().length()==0){
            return list();
        }
        else{
            List<StreamInflow> list=streamInflowJpaRepository.selectByKeyWord(keyWord);
            List<StreamInflowDto> streamDtoList=new ArrayList<>();
            for (StreamInflow s:list){
                streamDtoList.add(buildDto(s));
            }
            return  RespBean.success(streamDtoList);
        }
    }

    public  StreamInflowDto buildDto(StreamInflow streamInflow){
        StreamInflowDto streamInflowDto=new StreamInflowDto();
        streamInflowDto.setId(streamInflow.getId());
        streamInflowDto.setInflowStartId(streamInflow.getInflowStartId());
        streamInflowDto.setInflowEndId(streamInflow.getInflowEndId());
        streamInflowDto.setInflowStartName(streamInflow.getInflowStartName());
        streamInflowDto.setInflowEndName(streamInflow.getInflowEndName());
        if(streamInflow.getUpdateTime()!=null){
            String date_s1= DateFormatUtil.formatDate(streamInflow.getUpdateTime());
            streamInflowDto.setUpdateTime(date_s1);
        }
        if(streamInflow.getCreateTime()!=null){
            String date_s2=DateFormatUtil.formatDate(streamInflow.getCreateTime());
            streamInflowDto.setCreateTime(date_s2);
        }
        return streamInflowDto;
    }

}
