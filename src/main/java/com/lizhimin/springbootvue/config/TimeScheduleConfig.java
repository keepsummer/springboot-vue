package com.lizhimin.springbootvue.config;
import com.lizhimin.springbootvue.listener.DelListenerPOIScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
/**
 * quartz的配置信息
 * @author wenbronk
 * @time 2017年3月29日  下午5:20:29  2017
 */
@Configuration
public class TimeScheduleConfig {

    @Bean(name = "detailFactoryBean")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(DelListenerPOIScheduler scheduledTasks) {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        // 这儿设置对应的Job对象
        bean.setTargetObject(scheduledTasks);
        // 这儿设置对应的方法名 与执行具体任务调度类中的方法名对应
        bean.setTargetMethod("doCheck");
        bean.setConcurrent(false);
        return bean;
    }

    @Bean(name = "cronTriggerBean")
    public CronTriggerFactoryBean cronTriggerBean(MethodInvokingJobDetailFactoryBean detailFactoryBean) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(detailFactoryBean.getObject());
        try {
            trigger.setCronExpression("0/10 1 * * * ?");// 每10秒执行一次
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trigger;

    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(CronTriggerFactoryBean cronTriggerBean) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setTriggers(cronTriggerBean.getObject());
        return schedulerFactory;
    }

}