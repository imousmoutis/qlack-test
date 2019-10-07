package com.eurodyn.qlack.test.cmd.services.scheduler;

import com.eurodyn.qlack.fuse.scheduler.dto.JobDTO;
import com.eurodyn.qlack.fuse.scheduler.service.SchedulerService;
import com.eurodyn.qlack.test.cmd.services.scheduler.job.EmployeeCreationJob;
import com.eurodyn.qlack.test.cmd.services.scheduler.job.EmployeeDeletionJob;
import com.eurodyn.qlack.test.cmd.services.scheduler.job.LoggerJob;
import java.util.List;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author European Dynamics
 */

@Service
public class SchedulerServiceTest {


  @Value("${qlack.fuse.scheduler.cron.employeeDeletionJob}")
  private String deletionCronExp;

  @Value("${qlack.fuse.scheduler.cron.employeeCreationJob}")
  private String creationCronExp;

  @Value("${qlack.fuse.scheduler.employeeThreshold}")
  private int employeeThreshold;

  private final SchedulerService schedulerService;

  @Autowired
  public SchedulerServiceTest(SchedulerService schedulerService) {
    this.schedulerService = schedulerService;
  }

  public void listAllJobs() {
    System.out.println("******************");
    System.out.println("Testing listAlljobs method.");
    List<JobDTO> jobNames = schedulerService.getJobInfo();
    System.out.println("Scheduled jobs are: ");
    jobNames.forEach(s -> System.out.println(s.toString()));
    System.out.println("******************");
  }

  public void scheduleJobs() {
    System.out.println("******************");
    System.out.println("Testing scheduleJobs method.");
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("employeeThreshold", employeeThreshold);

    schedulerService.scheduleJob(EmployeeDeletionJob.class, creationCronExp, jobDataMap);
    schedulerService.scheduleJob(EmployeeCreationJob.class, creationCronExp);

    String cronExpDaily = "0 0 12 1/1 * ? *";
    schedulerService.scheduleJob(LoggerJob.class, cronExpDaily);

    System.out.println("******************");
  }

  public void triggerJob() {
    System.out.println("******************");
    System.out.println("Testing trigger method.");
    schedulerService.triggerJob(LoggerJob.class);
    System.out.println("******************");
  }

  public void deleteJob() {
    System.out.println("******************");
    System.out.println("Testing deleteJob method.");
    schedulerService.deleteJob(SchedulerService.getJobName(LoggerJob.class));
    System.out.println("******************");
  }
}