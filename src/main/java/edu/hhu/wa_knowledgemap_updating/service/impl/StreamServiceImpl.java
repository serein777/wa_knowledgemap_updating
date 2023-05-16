package edu.hhu.wa_knowledgemap_updating.service.impl;

import edu.hhu.wa_knowledgemap_updating.dto.*;
import edu.hhu.wa_knowledgemap_updating.entity.*;
import edu.hhu.wa_knowledgemap_updating.repository.StreamInflowRepository;
import edu.hhu.wa_knowledgemap_updating.repository.StreamRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.OperateRecordJpaRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.StreamInflowJpaRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.StreamJpaRepository;
import edu.hhu.wa_knowledgemap_updating.service.StreamService;
import edu.hhu.wa_knowledgemap_updating.utils.DateFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class StreamServiceImpl implements StreamService {
    @Autowired
    StreamRepository streamRepository;
    @Autowired
    StreamJpaRepository streamJpaRepository;
    @Autowired
    StreamInflowRepository streamInflowRepository;
    @Autowired
    StreamInflowJpaRepository streamInflowJpaRepository;

    @Autowired
    OperateRecordJpaRepository operateRecordJpaRepository;
    @Override
    public boolean updateIncrementalInfo(StreamKettleDto nodeDto) {
       if(!checkParam(nodeDto)){
           return  false;
       }
        //获取增量更新方法 创建、更新、删除
        String method=nodeDto.getMethod();
        //查询出同名的节点
        StreamNode sameNameNode = streamRepository.selectByName(nodeDto.getName());
        //操作记录对象
        OperateRecord operateRecord=new OperateRecord();
        StringBuilder opDetail=new StringBuilder();
        //创建
        if(method.equals("create")){
            if(sameNameNode!=null){
                log.info("the node exists");
                return  false;
            }
            log.info("create....");
            StreamNode createNode =new StreamNode();
           buildStreamNode(createNode,nodeDto);
            operateRecord.setOperateType("创建");
            opDetail.append("创建河流节点:");
            opDetail.append(nodeDto.toString());
            streamRepository.save(createNode);
        }
        //更新或者删除
        else {
            StreamNode beforeNode = streamRepository.selectByMysqlId(nodeDto.getMysqlId());
            //之前的节点为空
            if(null==beforeNode){
                log.info("can find the stream node ");
                return  false;
            }
            if(method.equals("update")){
                //更新的名字重复
                if(sameNameNode!=null&&!sameNameNode.getId().equals(beforeNode.getId())){
                    log.info("the node exists");
                    return  false;
                }
                log.info("update....");
                operateRecord.setOperateType("更新");
                opDetail.append("更新河流节点");
                opDetail.append('{').append( checkDiff(beforeNode,nodeDto)).append("}");
                buildStreamNode(beforeNode,nodeDto);
                streamRepository.save(beforeNode);
            }
            else if(method.equals("delete")){
                log.info("delete....");
                operateRecord.setOperateType("删除");
                opDetail.append("删除河流节点及其关系:");
                opDetail.append(nodeDto.toString());
               streamRepository.delete(sameNameNode);
            }
        }
        //插入操作记录
        operateRecord.setDataType("河流");
        operateRecord.setOperateTime(new Date());
        operateRecord.setOperateDetail(opDetail.toString());
        operateRecordJpaRepository.save(operateRecord);
        return  true;
    }
    //比较增量数据字段变动
   public  String checkDiff(StreamNode beforeNode,StreamKettleDto nodeDto){
        StringBuilder sb=new StringBuilder();
        if(!beforeNode.getName().equals(nodeDto.getName())){
            sb.append("name:").append(beforeNode.getName()).append("->").append(nodeDto.getName()).append(",");
        }
       if(!beforeNode.getType().equals(nodeDto.getType())){
           sb.append("type:").append(beforeNode.getType()).append("->").append(nodeDto.getType()).append(",");
       }
       if(beforeNode.getLevel()!=nodeDto.getLevel()){
           sb.append("level:").append(beforeNode.getLevel()).append("->").append(nodeDto.getLevel()).append(",");
       }
       if(beforeNode.getLength()!=nodeDto.getLength()){
           sb.append("length:").append(beforeNode.getLength()).append("->").append(nodeDto.getLength()).append(",");
       }
       if(sb.length()==0){
           return "无数据变动";
       }
        return  sb.deleteCharAt(sb.length()-1).toString();
   }
   public boolean checkParam(StreamKettleDto nodeDto){
        String method=nodeDto.getMethod();
       String lastUpdateTime=nodeDto.getLastUpdateTime();
       String type=nodeDto.getType();
       String name=nodeDto.getName();
       if(name==null||name.trim().length()==0) {
           log.info("ReservoirServiceImpl updateIncrementalInfo  name is null ");
           return  false;
       }
       if(method==null||method.trim().length()==0){
           log.info("ReservoirServiceImpl updateIncremental Info method is null ");
           return  false;
       }
       if(lastUpdateTime==null||lastUpdateTime.trim().length()==0){
           log.info("ReservoirServiceImpl updateIncremental Info lastUpdateTime is null ");
           return  false;
       }
       if(type==null||type.trim().length()==0){
           log.info("ReservoirServiceImpl updateIncremental Info type is null ");
           return  false;
       }
       return  true;
   }
    @Override
    public RespBean getAllNode() {
        List<StreamNode> streamList = streamRepository.getAll();
        List<StreamInflowRelation> relationList=streamInflowRepository.getAllRelations();
        List<StreamInflowNodeDto> relations=new ArrayList<>();
        //封装数据，只保留起点id  终点id  关系
        for(StreamInflowRelation streamInflowRelation:relationList){
            StreamInflowNodeDto s=new StreamInflowNodeDto();
            s.setStartId(streamInflowRelation.startNode.getId());
            s.setEndId(streamInflowRelation.endNode.getId());
            s.setRelation(streamInflowRelation.getRelation());
            relations.add(s);
        }
        StreamNodeDto streamNodeDto=new StreamNodeDto();
        streamNodeDto.setStreamNodeList(streamList);
        streamNodeDto.setRelationList(relations);
        return RespBean.success(streamNodeDto);
    }
    @Override
    public StreamNode findNodeByName(String name) {
        return  streamRepository.selectByName(name);
    }

    @Override
    public RespBean selectByKeyWord(String keyWord) {
        if(keyWord.trim().length()==0){
            return  list();
        }
        List<Stream> list=streamJpaRepository.selectByKeyWord(keyWord);
        List<StreamDto> streamDtoList=new ArrayList<>();
        for (Stream s:list){
            streamDtoList.add(buildDto(s));
        }
        return  RespBean.success(streamDtoList);
    }

    @Override
    public RespBean selectNodeByKeyWord(String keyWord) {
        if(keyWord.trim().length()==0){
           return   getAllNode();
        }
        streamRepository.selectByKeyWord(keyWord);
        return  null;
    }

    public StreamNode buildStreamNode(StreamNode streamNode, StreamKettleDto nodeDto){
        streamNode.setName(nodeDto.getName());
        streamNode.setLevel(nodeDto.getLevel());
        streamNode.setType(nodeDto.getType());
        streamNode.setLength(nodeDto.getLength());
        streamNode.setLastUpdateTime(nodeDto.getLastUpdateTime());
        streamNode.setMysqlId(nodeDto.getMysqlId());
        return streamNode;
    }
    @Override
    public RespBean list() {
       List<Stream> list=streamJpaRepository.findAll();
       List<StreamDto> streamDtoList=new ArrayList<>();
       for (Stream s:list){
           streamDtoList.add(buildDto(s));
       }
       return  RespBean.success(streamDtoList);
    }

    @Override
    public RespBean selectByName(String name) {
        Stream  stream = streamJpaRepository.findByName(name);
        if(stream==null){
            return RespBean.success();
        }
        StreamDto streamDto=buildDto(stream);
        return  RespBean.success(streamDto);
    }
    @Override
    public RespBean update(StreamDto streamDto) {
        //查询该id的河流是否存在
        Optional<Stream> beforeOp = streamJpaRepository.findById(streamDto.getId());
        //不存在
        if(!beforeOp.isPresent()){
            return  new RespBean(400L,"不存在该id的河流",null);
        }
        Stream before=beforeOp.get();
        //查询是否有同名的河流
        Stream same = streamJpaRepository.findByName(streamDto.getName());
        if(same!=null&&same.getId()!=before.getId()){
        return  new RespBean(400L,"存在同名的河流，请修改河流名称",null);
        }
        before.setType(streamDto.getType());
        before.setName(streamDto.getName());
        before.setLevel(streamDto.getLevel());
        before.setLength(streamDto.getLength());
        Timestamp updateTime=new Timestamp(new Date().getTime());
        before.setUpdateTime(updateTime);
        streamJpaRepository.save(before);
        //修改河流流入关系中河流名字
        streamInflowJpaRepository.updateInflowStartName(before.getId(),before.getName());
        streamInflowJpaRepository.updateInflowEndName(before.getId(),before.getName());
        return  RespBean.success();
    }

    @Override
    public RespBean delete(long id) {
        log.info("enter stream delete.....");
        Optional<Stream> op = streamJpaRepository.findById(id);
        if(!op.isPresent()){
            return new RespBean(400,"找不到该id的河流",null);
        }
        int  res = streamInflowJpaRepository.deleteByInflowStartIdOrInflowEndId(id);
        System.out.println(res);
        streamJpaRepository.deleteById(id);
        return  RespBean.success();
    }

    @Override
    public RespBean create(StreamDto streamDto) {
        //查询是否有同名的河流
        Stream same = streamJpaRepository.findByName(streamDto.getName());
        if(same!=null){
            return  new RespBean(400L,"存在同名的河流，请修改河流名称",null);
        }
        Stream stream=new Stream();
        stream.setType(streamDto.getType());
        stream.setName(streamDto.getName());
        stream.setLevel(streamDto.getLevel());
        stream.setLength(streamDto.getLength());
        Timestamp createTime=new Timestamp(new Date().getTime());
        Timestamp updateTime=new Timestamp(new Date().getTime());
        stream.setCreateTime(createTime);
        stream.setUpdateTime(updateTime);
        streamJpaRepository.save(stream);
        return RespBean.success();
    }

    public StreamDto buildDto(Stream stream){
        StreamDto streamDto=new StreamDto();
        streamDto.setId(stream.getId());
        streamDto.setType(stream.getType());
        streamDto.setLevel(stream.getLevel());
        streamDto.setLength(stream.getLength());
        streamDto.setName(stream.getName());
        if(stream.getUpdateTime()!=null){
            String date_s1= DateFormatUtil.formatDate(stream.getUpdateTime());
            streamDto.setUpdateTime(date_s1);
        }
        if(stream.getCreateTime()!=null){
            String date_s2=DateFormatUtil.formatDate(stream.getCreateTime());
            streamDto.setCreateTime(date_s2);
        }
        return  streamDto;
    }
}
