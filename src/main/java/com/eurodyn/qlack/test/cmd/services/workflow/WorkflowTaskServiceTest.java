package com.eurodyn.qlack.test.cmd.services.workflow;

import com.eurodyn.qlack.fuse.workflow.dto.ProcessInstanceDTO;
import com.eurodyn.qlack.fuse.workflow.dto.TaskDTO;
import com.eurodyn.qlack.fuse.workflow.service.WorkflowService;
import com.eurodyn.qlack.fuse.workflow.service.WorkflowTaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowTaskServiceTest {

    private final WorkflowService workflowService;

    private final WorkflowTaskService workflowTaskService;

    @Autowired
    public WorkflowTaskServiceTest(WorkflowService workflowService,
        WorkflowTaskService workflowTaskService) {
        this.workflowService = workflowService;
        this.workflowTaskService = workflowTaskService;
    }

    public void testProcedure() {
        List<ProcessInstanceDTO> processInstances = workflowService.getProcessInstancesByProcessId("two-tasks-process");
        getTasksByProcessInstanceId(processInstances.get(0).getId());
    }

    public void getTasksByProcessInstanceId(String processInstanceId) {
        System.out.println("******************");
        System.out.println("Testing getTasksByProcessInstanceId method.");

        List<TaskDTO> tasks = workflowTaskService.getTasksByProcessInstanceId(processInstanceId);
        tasks.stream().forEach(t -> System.out.println(t.toString()));

        System.out.println("******************");

        completeTask(tasks.get(0).getId());
    }

    public void completeTask(String taskId) {
        System.out.println("******************");
        System.out.println("Testing completeTask method.");

        workflowTaskService.completeTask(taskId);

        System.out.println("******************");
    }

}
