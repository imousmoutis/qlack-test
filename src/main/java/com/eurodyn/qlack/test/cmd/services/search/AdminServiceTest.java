package com.eurodyn.qlack.test.cmd.services.search;

import com.eurodyn.qlack.fuse.search.request.CreateIndexRequest;
import com.eurodyn.qlack.fuse.search.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author European Dynamics
 */

@Service
public class AdminServiceTest {

  private AdminService adminService;

  @Autowired
  public AdminServiceTest(AdminService adminService) {
    this.adminService = adminService;
  }

  public boolean checkIsUp() {
    System.out.println("******************");
    System.out.println("Testing checkIsUp method.");
    boolean checkIsUp = adminService.checkIsUp();
    System.out.println("Service is up: " + checkIsUp);
    System.out.println("******************");
    return checkIsUp;
  }

  public void createIndex() {
    System.out.println("******************");
    System.out.println("Testing createIndex method.");
    System.out.println("******************");


    List<CreateIndexRequest> createIndexRequestList = new ArrayList<>();

    CreateIndexRequest createIndexRequest = new CreateIndexRequest();
    createIndexRequest.setName("employees");
    createIndexRequest.setType("employee");

    CreateIndexRequest createIndexRequest2 = new CreateIndexRequest();
    createIndexRequest2.setName("employees2");
    createIndexRequest2.setType("employee");

    CreateIndexRequest createIndexRequest3 = new CreateIndexRequest();
    createIndexRequest3.setName("employees3");
    createIndexRequest3.setType("employee");

    CreateIndexRequest createIndexRequest4 = new CreateIndexRequest();
    createIndexRequest4.setName("employees4");
    createIndexRequest4.setType("employee");


    Path resourceDirectory = Paths.get("src/main/resources/elasticsearch/employee.json");

    try {
      String mapping = new String(Files.readAllBytes(resourceDirectory), StandardCharsets.UTF_8);
      createIndexRequest.setIndexMapping(mapping);
      createIndexRequest2.setIndexMapping(mapping);
      createIndexRequest3.setIndexMapping(mapping);
      createIndexRequest4.setIndexMapping(mapping);
    } catch (IOException e) {
      e.printStackTrace();
    }

    createIndexRequestList.add(createIndexRequest);
    createIndexRequestList.add(createIndexRequest2);
    createIndexRequestList.add(createIndexRequest3);
    createIndexRequestList.add(createIndexRequest4);

    createIndexRequestList.forEach(cir -> adminService.createIndex(cir));

//    createIndexRequest.setAliasName("RandomDudes");
//
//    adminService.createIndex(createIndexRequest);
    //adminService.createIndex(EmployeeDTO.class);
  }

  public void openAndCloseIndex() {
    System.out.println("Testing indexExists method.");
    System.out.println("******************");
    adminService.indexExists("employees");
    System.out.println("******************");
    System.out.println("Testing closeIndex method.");
    System.out.println("******************");
    adminService.closeIndex("employees2");
    System.out.println("Testing openIndex method.");
    System.out.println("******************");
    adminService.openIndex("employees2");
    System.out.println("******************");
  }

  public void updateSettings() {
    adminService.openIndex("employees");
    Map<String, String> settings = new HashMap();
    settings.put("index.number_of_replicas", "1");
    adminService.updateIndexSettings("employees", settings, false);
    adminService.closeIndex("employees");

  }

  public void deleteIndex() {
    adminService.deleteIndex("persons");
  }
}
