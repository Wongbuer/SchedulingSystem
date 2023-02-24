package com.wong.ffwb.scheduling_system.controller;

import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import com.wong.ffwb.scheduling_system.model.dto.EmployeeScoreDTO;
import com.wong.ffwb.scheduling_system.model.entity.Employee;
import com.wong.ffwb.scheduling_system.model.entity.Preference;
import com.wong.ffwb.scheduling_system.model.entity.Traffic;
import com.wong.ffwb.scheduling_system.scheduling.filler.EmployeeFiller;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Individual;
import com.wong.ffwb.scheduling_system.service.EmployeeService;
import com.wong.ffwb.scheduling_system.service.PreferenceService;
import com.wong.ffwb.scheduling_system.service.TrafficService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/scheduling")
public class SchedulingController {
    @Resource
    private TrafficService trafficService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private PreferenceService preferenceService;

    @GetMapping
    public BaseResponse<?> test() {
        List<Employee> list = employeeService.list();
        List<EmployeeScoreDTO> dtos = new ArrayList<>(list.size());
        for (Employee employee : list) {
            if (employee.getEmployeePreferenceId() != null) {
                Preference preference = preferenceService.getById(employee.getEmployeePreferenceId());
                employee.setPreferenceContent(preference.getPreferenceContent());
            }
            EmployeeScoreDTO employeeScoreDTO = new EmployeeScoreDTO(employee);
            dtos.add(employeeScoreDTO);
        }
        EmployeeFiller filler = new EmployeeFiller(dtos, "2023-05-10");
        List<Traffic> trafficList = trafficService.list();
        List<Individual.ChromosomeUnit> units = new ArrayList<>();
        for (Traffic traffic : trafficList) {
            int hour = traffic.getTrafficBeginTime().getHour();
            int minute = traffic.getTrafficBeginTime().getMinute();
            double begin = hour + minute == 0.0 ? 0 : 0.5;

            int hour1 = traffic.getTrafficEndTime().getHour();
            int minute1 = traffic.getTrafficEndTime().getMinute();
            double end = hour1 + minute1 == 0.0 ? 0 : 0.5;
            Individual.ChromosomeUnit chromosomeUnit = new Individual.ChromosomeUnit(begin, end, (int) (Math.random() * 4), end - begin);
            units.add(chromosomeUnit);
        }
        filler.processingPriority(units);
        return ResultUtils.success("ok");
    }
}
