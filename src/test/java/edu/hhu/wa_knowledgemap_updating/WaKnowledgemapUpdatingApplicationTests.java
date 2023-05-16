package edu.hhu.wa_knowledgemap_updating;
import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import edu.hhu.wa_knowledgemap_updating.repository.ReservoirRepository;
import edu.hhu.wa_knowledgemap_updating.repository.jpa.ReservoirJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.xml.crypto.Data;
import java.util.Date;

@SpringBootTest
class WaKnowledgemapUpdatingApplicationTests {
    @Autowired
    ReservoirJpaRepository reservoirJpaRepository;
    @Test
    void contextLoads() {
        Reservoir r=reservoirJpaRepository.findByName("测试水坝");
        r.setElevation(123);
        reservoirJpaRepository.save(r);
    }



}
