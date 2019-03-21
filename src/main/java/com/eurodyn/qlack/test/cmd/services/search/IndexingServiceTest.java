package com.eurodyn.qlack.test.cmd.services.search;

import com.eurodyn.qlack.fuse.search.dto.ESDocumentIdentifierDTO;
import com.eurodyn.qlack.fuse.search.dto.IndexingDTO;
import com.eurodyn.qlack.fuse.search.service.IndexingService;
import com.eurodyn.qlack.test.cmd.dto.EmployeeDTO;
import com.eurodyn.qlack.test.cmd.mapper.EmployeeMapper;
import com.eurodyn.qlack.test.cmd.model.Employee;
import com.eurodyn.qlack.test.cmd.repository.ElasticEmployeeRepository;
import com.eurodyn.qlack.test.cmd.repository.EmployeeRepository;
import java.util.List;
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
        ElasticEmployeeRepository elasticEmployeeRepository,   EmployeeMapper employeeMapper) {
        this.indexingService = indexingService;
        this.employeeRepository = employeeRepository;
        this.elasticEmployeeRepository = elasticEmployeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public void indexDocument() {
        System.out.println("******************");
        System.out.println("Testing createIndex method.");

        List<Employee> employees = employeeRepository.findAll();

        employees.stream().forEach(e -> {
            EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(e);
            IndexingDTO indexingDTO = new IndexingDTO();
            indexingDTO.setSourceObject(employeeDTO);
            indexingDTO.setIndex("employee");
            indexingDTO.setType("employee");
            indexingDTO.setId(employeeDTO.getId());
            indexingService.indexDocument(indexingDTO);
        });

        System.out.println("******************");
    }

    public void indexByRepo() {
        System.out.println("******************");
        System.out.println("Testing createIndex method with repository");
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(createEmployee());
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
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(createEmployee());
        elasticEmployeeRepository.delete(employeeDTO);
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setAge(42);
        employee.setId("1239d1d2b-a26d-4ed1-9b85-9009d596942a");

        return employee;
    }

}
