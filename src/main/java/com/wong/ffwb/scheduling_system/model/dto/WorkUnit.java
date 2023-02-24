package com.wong.ffwb.scheduling_system.model.dto;

import com.wong.ffwb.scheduling_system.model.entity.Employee;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class WorkUnit {
    private LocalTime beginTime;
    private LocalTime endTime;
    private Employee employee;
}
