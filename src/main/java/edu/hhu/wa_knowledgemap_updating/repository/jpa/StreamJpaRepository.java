package edu.hhu.wa_knowledgemap_updating.repository.jpa;

import edu.hhu.wa_knowledgemap_updating.entity.Reservoir;
import edu.hhu.wa_knowledgemap_updating.entity.Stream;
import org.pentaho.di.trans.step.StepAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamJpaRepository extends JpaRepository<Stream,Long> {
    Stream findByName(@Param("name") String name);

}
