package edu.hhu.wa_knowledgemap_updating.service.impl;

import edu.hhu.wa_knowledgemap_updating.dto.StreamDto;
import edu.hhu.wa_knowledgemap_updating.dto.StreamKettleDto;
import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import edu.hhu.wa_knowledgemap_updating.entity.RespBean;
import edu.hhu.wa_knowledgemap_updating.entity.Stream;
import edu.hhu.wa_knowledgemap_updating.entity.StreamNode;
import edu.hhu.wa_knowledgemap_updating.repository.StreamRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.StreamJpaRepository;
import edu.hhu.wa_knowledgemap_updating.service.StreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class StreamServiceImpl implements StreamService {
    @Autowired
    StreamRepository streamRepository;
    @Autowired
    StreamJpaRepository streamJpaRepository;
    @Override
    public boolean updateIncrementalInfo(StreamKettleDto nodeDto) {
        if(nodeDto.getName()==null||nodeDto.getName().trim().length()==0) {
            log.info("ReservoirServiceImpl updateIncrementalInfo  name is null ");
            return  false;
        }
        //获取增量更新方法 创建、更新、删除
        String method=nodeDto.getMethod();
        if(method==null||method.trim().length()==0){
            log.info("ReservoirServiceImpl updateIncremental Info method is null ");
            return  false;
        }
        //查询出之前的节点
        StreamNode beforeNode = streamRepository.selectByName(nodeDto.getName());
        //创建
        if(method.equals("create")){
            if(beforeNode!=null){
                log.info("the node exists");
                return  false;
            }
            log.info("create....");
            StreamNode createNode =new StreamNode();
            boolean isSuccess=buildStreamNode(createNode,nodeDto);
            if(!isSuccess) return false;
            streamRepository.save(createNode);
        }
        //更新或者删除
        else {
            //之前的节点为空
            if(null==beforeNode){
                log.info("can find the stream node ");
                return  false;
            }
            if(method.equals("update")){
                log.info("update....");
                boolean isSuccess=buildStreamNode(beforeNode,nodeDto);
                if(!isSuccess) return false;
                streamRepository.save(beforeNode);
            }
            else if(method.equals("delete")){
                log.info("delete....");
               streamRepository.delete(beforeNode);
            }
        }
        return  true;
    }

    @Override
    public List<StreamNode> getAllNode() {
        return  streamRepository.getAll();
    }
    @Override
    public StreamNode findNodeByName(String name) {
        return  streamRepository.selectByName(name);
    }

    public boolean buildStreamNode(StreamNode streamNode, StreamKettleDto nodeDto){
        String lastUpdateTime=nodeDto.getLastUpdateTime();
        String type=nodeDto.getType();
        if(lastUpdateTime==null||lastUpdateTime.trim().length()==0){
            log.info("ReservoirServiceImpl updateIncremental Info lastUpdateTime is null ");
            return  false;
        }
        if(type==null||type.trim().length()==0){
            log.info("ReservoirServiceImpl updateIncremental Info type is null ");
            return  false;
        }
        streamNode.setName(nodeDto.getName());
        streamNode.setLevel(nodeDto.getLevel());
        streamNode.setType(nodeDto.getType());
        streamNode.setLength(nodeDto.getLength());
        streamNode.setLastUpdateTime(nodeDto.getLastUpdateTime());
        streamNode.setMysqlId(nodeDto.getMysqlId());
        return true;
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
        return  RespBean.success();
    }

    @Override
    public RespBean delete(long id) {
        log.info("enter stream delete.....");
        Optional<Stream> op = streamJpaRepository.findById(id);
        if(!op.isPresent()){
            return new RespBean(400,"找不到该id的河流",null);
        }
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
        stream.setUpdateTime(createTime);
        streamJpaRepository.save(stream);
        return RespBean.success();
    }

    public StreamDto buildDto(Stream stream){
        StreamDto streamDto=new StreamDto();
        streamDto.setType(stream.getType());
        streamDto.setLevel(stream.getLevel());
        streamDto.setLength(stream.getLength());
        streamDto.setId(stream.getId());
        streamDto.setName(stream.getName());
        log.info("format date...");
        String format="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        if(stream.getUpdateTime()!=null){
            String date_s1=simpleDateFormat.format(stream.getUpdateTime());
            streamDto.setUpdateTime(date_s1);
        }
        if(stream.getCreateTime()!=null){
            String date_s2=simpleDateFormat.format(stream.getUpdateTime());
            streamDto.setCreateTime(date_s2);
        }
        return  streamDto;
    }
}
