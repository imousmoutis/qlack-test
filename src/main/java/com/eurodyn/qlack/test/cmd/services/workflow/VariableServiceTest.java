package com.eurodyn.qlack.test.cmd.services.workflow;

import com.eurodyn.qlack.fuse.workflow.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VariableServiceTest {

  private final VariableService variableService;

  @Autowired
  public VariableServiceTest(VariableService variableService) {
    this.variableService = variableService;
  }

  public void getVariableInstance(String processInstanceId) {
    System.out.println("******************");
    System.out.println("Testing getVariableInstance method.");

    Object variableValue = variableService.getVariableInstance(processInstanceId, "username");

    System.out.println(
        "The process instance with id " + processInstanceId + " has the variable " + variableValue);
    System.out.println("******************");
  }

  public void setVariableInstance(String processInstanceId) {
    System.out.println("******************");
    System.out.println("Testing setVariableInstance method.");

    variableService.setVariableInstance(processInstanceId, "username", "updatedVariableValue");

    System.out.println("******************");
    getVariableInstance(processInstanceId);
  }
}
