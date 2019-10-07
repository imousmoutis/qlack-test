package com.eurodyn.qlack.test.cmd.services.scheduler.job;

import com.eurodyn.qlack.test.cmd.model.Employee;
import com.eurodyn.qlack.test.cmd.repository.EmployeeRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.java.Log;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author European Dynamics
 */

@Log
@Transactional
public class EmployeeDeletionJob implements Job {

  @Autowired
  EmployeeRepository employeeRepository;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    JobDataMap jobDataMapTrigger = jobExecutionContext.getJobDetail().getJobDataMap();
    int employeeThreshold = jobDataMapTrigger.getInt("employeeThreshold");

    List<Employee> employees = employeeRepository.findByFirstNameAndLastName("John", "Doe");

    if (employees.size() >= employeeThreshold) {
      employeeRepository.deleteByFirstNameAndLastName("John", "Doe");
      log.info(jobExecutionContext.getFireInstanceId() + " : Deleted all generated employees");
    }
  }
}