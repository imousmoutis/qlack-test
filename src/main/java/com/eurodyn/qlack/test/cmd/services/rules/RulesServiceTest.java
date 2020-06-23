package com.eurodyn.qlack.test.cmd.services.rules;

import com.eurodyn.qlack.fuse.rules.dto.DmnModelDTO;
import com.eurodyn.qlack.fuse.rules.service.DmnModelService;
import org.camunda.commons.utils.IoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RulesServiceTest {

    private static final String START_TESTING = "\n========================================\nTesting ";
    private static final String END_TESTING = "========================================";
    private static final String FILE_PATHNAME = "src/main/resources/rules/camunda.xml";
    private static final String TO_BE_EXECUTED = "decision";
    private final List<Map<String, Object>> variables = new ArrayList<>();
    private String modelId;

    @Autowired
    private DmnModelService dmnModelService;


    // Create new DMN models passing different parameters and delete the first model
    public void createDmnModel() {
        modelId = createDmnModelFromFilePathName();
        deleteDmnModel(modelId);
        modelId = createDmnModelFromInputStream();
    }

    // First find a DMN model by its Id and then retrieve all DMN models available in database
    public void findDmnModel() {
        DmnModelDTO dmnModelDTO = dmnModelService.findById(modelId);
        assert dmnModelDTO != null;

        List<DmnModelDTO> dmnModelDTOList = dmnModelService.getAll();
        assert !dmnModelDTOList.isEmpty();
    }

    // Execute rules calling all three overloaded methods
    public void executeRules() {
        initializeVariables();
        List<Map<String, Object>> results;
        results = rulesExecutionWithPathname();
        printResults(results);
        results = rulesExecutionWithInputStream();
        printResults(results);
        results = rulesExecutionWithStringList();
        printResults(results);
    }

    // Create a new DMN model by providing the pathname of the file containing the DMN xml file
    private String createDmnModelFromFilePathName() {
        System.out.println(START_TESTING + "createDmnModel method with file pathname.");

        String dmnModelId = dmnModelService.createDmnModel(FILE_PATHNAME);

        System.out.println(END_TESTING);
        return dmnModelId;
    }

    // Create a new DMN model by providing the InputStream from the DMN xml file
    private String createDmnModelFromInputStream() {
        System.out.println(START_TESTING + "createDmnModel method with InputStream.");
        String dmnModelId = "";

        try {
            File file = new File(FILE_PATHNAME);
            InputStream inputStream = new FileInputStream(file);
            dmnModelId = dmnModelService.createDmnModel(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(END_TESTING);
        return dmnModelId;
    }

    // Deletes a DMN model by providing its Id
    private void deleteDmnModel(String modelId) {
        System.out.println(START_TESTING + "deleteDmnModel method.");

        dmnModelService.deleteDmnModel(modelId);

        System.out.println(END_TESTING);
    }

    // Initializes the input variables which will be used against the rules
    private void initializeVariables() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();

        map1.put("season", "Spring");
        map1.put("guestCount", 4);
        map2.put("season", "Fall");
        map2.put("guestCount", 2);

        variables.add(map1);
        variables.add(map2);
    }

    // Executes rules by providing the DMN xml file pathname
    private List<Map<String, Object>> rulesExecutionWithPathname() {
        System.out.println(START_TESTING + "execute rules method with file pathname");

        List<Map<String, Object>> results =
                dmnModelService.executeRules(FILE_PATHNAME, variables, TO_BE_EXECUTED);

        System.out.println(END_TESTING);
        return results;
    }

    // Executes rules by providing the DMN xml as InputStream
    private List<Map<String, Object>> rulesExecutionWithInputStream() {
        System.out.println(START_TESTING + "execute rules method with InputStream");

        List<String> rules = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(FILE_PATHNAME))) {
            rules = lines.collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        List<Map<String, Object>> results =
                dmnModelService.executeRules(rules, variables, TO_BE_EXECUTED);

        System.out.println(END_TESTING);
        return results;
    }

    // Executes rules by providing the DMN xml as a list of Strings
    private List<Map<String, Object>> rulesExecutionWithStringList() {
        System.out.println(START_TESTING + "execute rules method with a list of Strings");

        File file = new File(FILE_PATHNAME);
        InputStream inputStream = IoUtil.fileAsStream(file);
        List<Map<String, Object>> results =
                dmnModelService.executeRules(inputStream, variables, TO_BE_EXECUTED);

        System.out.println(END_TESTING);
        return results;
    }

    // Prints the execution results to the console
    private void printResults(List<Map<String, Object>> resultList) {
        System.out.println("********** PRINTING RESULTS **********");
        resultList.forEach(result -> {
            for (Map.Entry<String, Object> entry : result.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        });
        System.out.println("********** PRINTING COMPLETED **********");
    }
}
