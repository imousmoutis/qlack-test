package com.eurodyn.qlack.test.cmd.services.scheduler.job;

import com.eurodyn.qlack.test.cmd.model.Employee;
import com.eurodyn.qlack.test.cmd.repository.EmployeeRepository;
import lombok.extern.java.Log;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author European Dynamics
 */

@Log
public class EmployeeCreationJob implements Job {

    @Autowired
    public EmployeeRepository employeeRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Employee employee = new Employee();
        employee.setLastName("Doe");
        employee.setFirstName("John");
        employee.setAge((int) (Math.random() * 100));

        employeeRepository.save(employee);
        System.out.println("Created: " + employee.toString());
        System.out.println("Employees are now " + employeeRepository.findAll().size());
        log.info(jobExecutionContext.getJobInstance() + " " +  jobExecutionContext.getFireTime() + " " + jobExecutionContext.getFireTime());
    }
}

