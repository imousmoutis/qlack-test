package com.eurodyn.qlack.test.cmd.services.search;

import com.eurodyn.qlack.fuse.search.request.CreateIndexRequest;
import com.eurodyn.qlack.fuse.search.service.AdminService;
import com.eurodyn.qlack.test.cmd.dto.EmployeeDTO;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        CreateIndexRequest createIndexRequest = new CreateIndexRequest();
        createIndexRequest.setName("employees");
        createIndexRequest.setType("employee");

        Path resourceDirectory = Paths.get("src/main/resources/employee.json");

        try {
            String mapping = new String(Files.readAllBytes(resourceDirectory), StandardCharsets.UTF_8);
            createIndexRequest.setIndexMapping(mapping);
        } catch (IOException e) {
            e.printStackTrace();
        }

        adminService.createIndex(createIndexRequest);
        adminService.createIndex(EmployeeDTO.class);
    }

    public void openAndCloseIndex() {
        System.out.println("Testing indexExists method.");
        System.out.println("******************");
        adminService.indexExists("employee1");
        System.out.println("******************");
        System.out.println("Testing closeIndex method.");
        System.out.println("******************");
        adminService.closeIndex("employee1");
        System.out.println("Testing openIndex method.");
        System.out.println("******************");
        adminService.openIndex("employee1");
        System.out.println("******************");
    }
}
