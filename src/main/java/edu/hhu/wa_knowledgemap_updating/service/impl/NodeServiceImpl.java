package edu.hhu.wa_knowledgemap_updating.service.impl;

import edu.hhu.wa_knowledgemap_updating.entity.Node;
import edu.hhu.wa_knowledgemap_updating.repository.NodeRepository;
import edu.hhu.wa_knowledgemap_updating.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NodeServiceImpl implements NodeService {
   @Autowired
    NodeRepository nodeRepository;
    public Node save(Node node){
        Node save = nodeRepository.save(node);
        return  save;
    }

    public List<Node> getAll(){
        List<Node> nodeList = nodeRepository.selectAll();
        for (Node x:nodeList){
            System.out.println(x.getId()+" "+x.getName()+" "+x.getAge());
        }
        return nodeList;
    }
}
