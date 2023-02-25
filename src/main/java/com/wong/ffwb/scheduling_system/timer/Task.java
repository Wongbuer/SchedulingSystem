package com.wong.ffwb.scheduling_system.timer;

import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.model.entity.WorkingScheduling;
import com.wong.ffwb.scheduling_system.service.WorkingSchedulingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class Task implements ApplicationContextAware {
    private WorkingSchedulingService workingSchedulingService;
    private static LocalDate date = LocalDate.of(2023, 3, 9);

    @Scheduled(cron = "0 0/2 * * * ?")
    public void schedulingTask() {
        log.debug("------- {} start scheduled task", LocalDateTime.now());
        String format = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        BaseResponse<List<WorkingScheduling>> schedulingByDay = workingSchedulingService.getSchedulingByDay(format);
        log.debug("------- {} finish scheduled task", LocalDateTime.now());
        date = date.plusDays(1);
        log.debug("------- {} is the new date", date);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        workingSchedulingService = applicationContext.getBean(WorkingSchedulingService.class);
    }
}
