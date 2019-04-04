package com.eurodyn.qlack.test.cmd.services.scheduler.job;

import lombok.extern.java.Log;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author European Dynamics
 */

@Log
public class LoggerJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info(jobExecutionContext.getJobInstance() + " : executed at " + jobExecutionContext.getFireTime());
    }
}
