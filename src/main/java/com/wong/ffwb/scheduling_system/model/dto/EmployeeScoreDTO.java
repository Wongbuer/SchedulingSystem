package com.wong.ffwb.scheduling_system.model.dto;

import com.wong.ffwb.scheduling_system.model.entity.Employee;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeScoreDTO {
    private Employee employee;
    List<Integer> dayPreference;
    List<Double> hoursPreference;
    List<Double> timePreference;
    private int score;
    public EmployeeScoreDTO(Employee employee) {
        this.employee = employee;
    }
}
