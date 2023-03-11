package edu.hhu.wa_knowledgemap_updating;

import edu.hhu.wa_knowledgemap_updating.repository.ReservoirRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WaKnowledgemapUpdatingApplicationTests {
    @Autowired
    ReservoirRepository repository;
    @Test
    void contextLoads() {
    }



}
