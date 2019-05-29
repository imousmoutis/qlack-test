package com.eurodyn.qlack.test.cmd.services.workflow;

import com.eurodyn.qlack.fuse.workflow.dto.ProcessInstanceDTO;
import com.eurodyn.qlack.fuse.workflow.dto.TaskDTO;
import com.eurodyn.qlack.fuse.workflow.service.WorkflowService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceTest {

    private final WorkflowService workflowService;

    @Autowired
    public WorkflowServiceTest(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    public void startWorkflowInstance() {
        System.out.println("******************");
        System.out.println("Testing startWorkflowInstance method.");

        String processInstanceId = workflowService.startWorkflowInstance("my-process");

        System.out.println("Process instance with id " +processInstanceId+ " has started.");
        System.out.println("******************");

        getTasksByProcessInstanceId(processInstanceId);
    }

    public void getTasksByProcessInstanceId(String processInstanceId){
        System.out.println("******************");
        System.out.println("Testing getTasksByProcessInstanceId method.");

        List<TaskDTO> tasks = workflowService.getTasksByProcessInstanceId(processInstanceId);
        tasks.stream().forEach(t -> System.out.println(t.toString()));

        System.out.println("******************");
    }

    public void getProcessInstancesByProcessId(){
        System.out.println("******************");
        System.out.println("Testing getProcessInstancesByProcessId method.");

        List<ProcessInstanceDTO> processInstances = workflowService.getProcessInstancesByProcessId("my-process");

        processInstances.stream().forEach(i -> System.out.println(i.toString()));
        System.out.println("******************");
    }
}
