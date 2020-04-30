package com.eurodyn.qlack.test.cmd.services.search;

import com.eurodyn.qlack.fuse.search.dto.ESDocumentIdentifierDTO;
import com.eurodyn.qlack.fuse.search.dto.IndexingDTO;
import com.eurodyn.qlack.fuse.search.service.IndexingService;
import com.eurodyn.qlack.test.cmd.dto.Car;
import com.eurodyn.qlack.test.cmd.dto.EmployeeDTO;
import com.eurodyn.qlack.test.cmd.mapper.EmployeeMapper;
import com.eurodyn.qlack.test.cmd.model.Employee;
import com.eurodyn.qlack.test.cmd.repository.ElasticEmployeeRepository;
import com.eurodyn.qlack.test.cmd.repository.EmployeeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author European Dynamics
 */

@Service
public class IndexingServiceTest {

  private IndexingService indexingService;
  private EmployeeRepository employeeRepository;
  private ElasticEmployeeRepository elasticEmployeeRepository;
  private EmployeeMapper employeeMapper;


  @Autowired
  public IndexingServiceTest(IndexingService indexingService, EmployeeRepository employeeRepository,
      ElasticEmployeeRepository elasticEmployeeRepository, EmployeeMapper employeeMapper) {
    this.indexingService = indexingService;
    this.employeeRepository = employeeRepository;
    this.elasticEmployeeRepository = elasticEmployeeRepository;
    this.employeeMapper = employeeMapper;
  }

  public void indexDocument() {
    System.out.println("******************");
    System.out.println("Testing createIndex method.");

    List<Employee> employees = new ArrayList<>();

    for (int i = 0; i < 1000; i++) {
      employees.add(createEmployee30());
      employees.add(createEmployee40());
      employees.add(createEmployee50());
    }

    employees.stream().forEach(e -> {
      EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(e);
      employeeDTO.setWeight(employeeDTO.getAge() + 15);

      Car car = new Car();

      if (employeeDTO.getAge() == 30) {
        car.setModel("Murcielago");
        car.setMake("Lamborghini");
      } else if (employeeDTO.getAge() == 40) {
        car.setMake("Ferrari");
        car.setModel("812");
      } else {
        car.setMake("Ferrari");
        car.setModel("812");
      }

      employeeDTO.setCar(car);
      IndexingDTO indexingDTO = new IndexingDTO();
      indexingDTO.setSourceObject(employeeDTO);
      indexingDTO.setIndex("employees");
      indexingDTO.setType("employee");
      indexingDTO.setId(employeeDTO.getId());
      indexingService.indexDocument(indexingDTO);
    });

    System.out.println("******************");
  }

  public void indexByRepo() {
    System.out.println("******************");
    System.out.println("Testing createIndex method with repository");
    EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(createEmployee30());
    elasticEmployeeRepository.save(employeeDTO);
    System.out.println("******************");
  }

  public void unindexDocument() {
    System.out.println("******************");
    System.out.println("Testing deleteIndex method.");

    ESDocumentIdentifierDTO dto = new ESDocumentIdentifierDTO();

    dto.setId("d1278f33-3656-42d4-8085-2352c96ae234");
    dto.setType("employee");
    dto.setIndex("employee");

    indexingService.unindexDocument(dto);

    System.out.println("******************");
  }

  public void deleteFromRepo() {
    EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(createEmployee30());
    elasticEmployeeRepository.delete(employeeDTO);
  }

  private Employee createEmployee30() {
    Employee employee = new Employee();
    employee.setFirstName(UUID.randomUUID().toString());
    employee.setLastName("Smith");
    employee.setAge(30);

    employee.setId(UUID.randomUUID().toString());
    return employee;
  }

  private Employee createEmployee40() {
    Employee employee = new Employee();
    employee.setFirstName(UUID.randomUUID().toString());
    employee.setLastName("Doe");
    employee.setAge(40);
    employee.setId(UUID.randomUUID().toString());

    return employee;
  }

  private Employee createEmployee50() {
    Employee employee = new Employee();
    employee.setFirstName(UUID.randomUUID().toString());
    employee.setLastName("Smith");
    employee.setAge(50);
    employee.setId(UUID.randomUUID().toString());

    return employee;
  }

}
