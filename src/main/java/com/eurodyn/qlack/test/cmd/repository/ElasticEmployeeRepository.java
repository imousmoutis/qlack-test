package com.eurodyn.qlack.test.cmd.repository;

import com.eurodyn.qlack.test.cmd.dto.EmployeeDTO;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticEmployeeRepository extends ElasticsearchRepository<EmployeeDTO, String> {

  List<EmployeeDTO> findByAge(int age);

  List<EmployeeDTO> findByFirstName(String name);
}
