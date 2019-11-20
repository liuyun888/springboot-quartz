package com.gupaoedu.demo.task;

import com.gupaoedu.demo.util.BaseJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @Author
 * @Date 2019/11/20 0020
 */
public class TestTask2 implements BaseJob {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(Thread.currentThread().getName() + " " +sdf.format(date) + " Task2： ----波普第二定律----");
    }
}
