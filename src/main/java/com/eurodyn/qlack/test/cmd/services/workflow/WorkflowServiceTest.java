package com.eurodyn.qlack.test.cmd.services.workflow;

import com.eurodyn.qlack.fuse.workflow.dto.ProcessHistoryDTO;
import com.eurodyn.qlack.fuse.workflow.dto.ProcessInstanceDTO;
import com.eurodyn.qlack.fuse.workflow.service.WorkflowService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceTest {

    private final WorkflowService workflowService;

    private final VariableServiceTest variableServiceTest;

    @Autowired
    public WorkflowServiceTest(WorkflowService workflowService,
        VariableServiceTest variableServiceTest) {
        this.workflowService = workflowService;
        this.variableServiceTest = variableServiceTest;
    }

    public void startWorkflowInstance() {
        System.out.println("******************");
        System.out.println("Testing startWorkflowInstance method.");

        Map<String, Object> variables = new HashMap<>();
        variables.put("username", "datamanager1");

        String processInstanceId = workflowService.startWorkflowInstance("two-tasks-process", variables);

        System.out.println("Process instance with id " + processInstanceId + " has started.");
        System.out.println("******************");
    }

    public void getProcessInstancesByProcessId() {
        System.out.println("******************");
        System.out.println("Testing getProcessInstancesByProcessId method.");

        List<ProcessInstanceDTO> processInstances = workflowService.getProcessInstancesByProcessId("two-tasks-process");

        processInstances.stream().forEach(i -> System.out.println(i.toString()));
        System.out.println("******************");

        processInstances.stream().forEach(i -> {
            variableServiceTest.getVariableInstance(i.getId());
            variableServiceTest.setVariableInstance(i.getId());
            suspendWorkflowInstance(i.getId());
            resumeWorkflowInstance(i.getId());
            deleteWorkflowInstance(i.getId());
        });
    }

    public void suspendWorkflowInstance(String processInstanceId) {
        System.out.println("******************");
        System.out.println("Testing suspendWorkflowInstance method.");

        workflowService.suspendWorkflowInstance(processInstanceId);
        System.out.println("******************");
    }

    public void resumeWorkflowInstance(String processInstanceId) {

        System.out.println("******************");
        System.out.println("Testing resumeWorkflowInstance method.");

        workflowService.resumeWorkflowInstance(processInstanceId);
        System.out.println("******************");
    }

    public void deleteWorkflowInstance(String processInstanceId) {
        System.out.println("******************");
        System.out.println("Testing deleteWorkflowInstance method.");

        workflowService.deleteWorkflowInstance(processInstanceId, "test");
        System.out.println("******************");
    }

    public void getProcessHistory(String processId) {
        System.out.println("******************");
        System.out.println("Testing getProcessHistory method.");

        List<ProcessHistoryDTO> processHistory = workflowService.getProcessHistory(processId);
        processHistory.stream().forEach(h -> System.out.println(h.toString()));

        System.out.println("******************");
    }

    public void updateProcessesFromResources() {
        System.out.println("******************");
        System.out.println("Testing updateProcessesFromResources method.");

        workflowService.updateProcessesFromResources();

        System.out.println("******************");
    }

}
