package com.wong.ffwb.scheduling_system.scheduling.filler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import cn.hutool.json.JSONUtil;
import com.wong.ffwb.scheduling_system.model.dto.EmployeeScoreDTO;
import com.wong.ffwb.scheduling_system.model.dto.PreferenceContent;
import com.wong.ffwb.scheduling_system.model.dto.WorkUnit;
import com.wong.ffwb.scheduling_system.model.entity.Employee;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Individual;

import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 员工填充器
 *
 * @author Wongbuer
 * @date 2023/02/16
 */
public class EmployeeFiller extends AbstractFiller {
    private final Week week;
    private Set<EmployeeScoreDTO> first = new HashSet<>();
    private Set<EmployeeScoreDTO> second = new HashSet<>();
    private Set<EmployeeScoreDTO> third = new HashSet<>();


    /**
     * 员工填料
     *
     * @param employeeScoreDTOList 包含员工与员工优先级分数的DTO列表
     * @param dateStr              str日期
     */
    public EmployeeFiller(List<EmployeeScoreDTO> employeeScoreDTOList, String dateStr) {
        super(employeeScoreDTOList);
        Date date = DateUtil.parse(dateStr, "yyyy-MM-dd").toJdkDate();
        this.week = DateUtil.dayOfWeekEnum(date);
    }

    /**
     * 处理优先级
     *
     * @param list 列表
     */
    @Override
    public void processingPriority(List<Individual.ChromosomeUnit> list) {
        // 获取所有班次时间
//        HashSet<Double> durationSet = list.stream().mapToDouble(Individual.ChromosomeUnit::getDuration).collect(HashSet::new, Set::add, Set::addAll);
        // 遍历所有员工并判断优先级
        employeeScoreDTOList.forEach(employeeScoreDTO -> {
            int score = 0;
            PreferenceContent preferenceContent = employeeScoreDTO.getEmployee().getPreferenceContent();
            List<Integer> dayPreference = new ArrayList<>();
            List<Double> hoursPreference = new ArrayList<>();
            List<Double> timePreference = new ArrayList<>();
            if (preferenceContent != null) {
                // 解析工作日偏好
                dayPreference = parseWorkingDayPreference(preferenceContent.getWorkingDayPreference());
                // 解析工作时间偏好
                hoursPreference = parseWorkingHoursPreference(preferenceContent.getWorkingHoursPreference());
                // 解析工作时长偏好
                timePreference = parseWorkingTimePreference(preferenceContent.getWorkingTimePreference());
            }
            if (dayPreference.indexOf(week.ordinal()) != -1) {
                score += 4;
            }
            if (hoursPreference.size() != 0) {
                score += 2;
            }
            if (timePreference.size() != 0) {
                score += 1;
            }
            employeeScoreDTO.setScore(score);
            employeeScoreDTO.setDayPreference(dayPreference);
            employeeScoreDTO.setHoursPreference(hoursPreference);
            employeeScoreDTO.setTimePreference(timePreference);
        });
    }

    @Override
    void filling(List<EmployeeScoreDTO> employeeScoreDTOList, List<Individual.ChromosomeUnit> list) {
        // 循环遍历班次列表, 并填充员工
        employeeScoreDTOList = employeeScoreDTOList.stream().sorted(Comparator.comparing(EmployeeScoreDTO::getScore).reversed()).collect(Collectors.toList());
        List<WorkUnit> workUnitList = new ArrayList<>(list.size());
        for (Individual.ChromosomeUnit chromosomeUnit : list) {
            LocalTime beginTime = transformDoubleToTime(chromosomeUnit.getBeginTime());
            LocalTime endTime = transformDoubleToTime(chromosomeUnit.getEndTime());
            int position = chromosomeUnit.getPosition();
            // TODO : 用户填充逻辑
        }

    }
    
    private EmployeeScoreDTO findEmployeeScoreDTOByPosition(List<EmployeeScoreDTO> list, int position) {
        for (EmployeeScoreDTO employeeScoreDTO : list) {
            if (employeeScoreDTO.getEmployee().getEmployeePosition() == position) {
                return employeeScoreDTO;
            }
        }
        return null;
    }

    private LocalTime transformDoubleToTime(double time) {
        int hour = (int) time;
        double min = time - hour;
        return LocalTime.of(hour, min == 0 ? 0 : 30);
    }


    /**
     * 解析工作日偏好
     *
     * @param workingDayPreference 工作日偏好
     * @return {@link List}<{@link Integer}>
     */
    private List<Integer> parseWorkingDayPreference(String workingDayPreference) {
        if (workingDayPreference == null) {
            return new ArrayList<>();
        }
        Matcher matcher = Pattern.compile("(\\d+)-(\\d+)").matcher(workingDayPreference);
        Matcher matcher2 = Pattern.compile("\\d+").matcher(workingDayPreference);
        int a = -1;
        int b = -1;
        if (matcher.find()) {
            a = Integer.parseInt(matcher.group(1));
            b = Integer.parseInt(matcher.group(2));
        } else if (matcher2.find()) {
            a = Integer.parseInt(matcher2.group());
        } else {
            throw new IllegalArgumentException("workingDayPreference is invalid");
        }
        if (a == -1 && b == -1) {
            throw new IllegalArgumentException("workingDayPreference is invalid");
        } else if (a == -1 || b == -1) {
            return Collections.singletonList(a == -1 ? b : a);
        } else {
            return IntStream.range(Math.min(a, b), Math.max(a, b)).collect(ArrayList::new, List::add, List::addAll);
        }
    }

    /**
     * 解析工作时间偏好
     *
     * @param workingTimePreference 工作时间偏好
     * @return {@link List}<{@link Double}>
     */
    private List<Double> parseWorkingTimePreference(String workingTimePreference) {
        if (workingTimePreference == null) {
            return new ArrayList<>();
        }
        Matcher matcher = Pattern.compile("(\\d+):(\\d+)-(\\d+):(\\d+)").matcher(workingTimePreference);
        double beginTime = -1;
        double endTime = -1;
        try {
            if (matcher.find()) {
                int beginHour = Integer.parseInt(matcher.group(1));
                int beginMinutes = Integer.parseInt(matcher.group(2));
                int endHour = Integer.parseInt(matcher.group(3));
                int endMinutes = Integer.parseInt(matcher.group(4));
                if (beginMinutes == 30) {
                    beginTime = beginHour + 0.5;
                } else if (beginMinutes == 0) {
                    beginTime = beginHour;
                } else {
                    throw new IllegalArgumentException("workingTimePreference is invalid");
                }
                if (endMinutes == 30) {
                    endTime = endHour + 0.5;
                } else if (endMinutes == 0) {
                    endTime = endHour;
                } else {
                    throw new IllegalArgumentException("workingTimePreference is invalid");
                }
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        if (beginTime == -1 || endTime == -1) {
            throw new IllegalArgumentException("workingTimePreference is invalid");
        }
        List<Double> list = new ArrayList<>();
        for (double i = beginTime; i <= endTime; i += 0.5) {
            list.add(i);
        }
        return list;
    }

    /**
     * 解析工作时长偏好
     *
     * @param workingHoursPreference 工作时长偏好
     * @return {@link List}<{@link Double}>
     */
    private List<Double> parseWorkingHoursPreference(String workingHoursPreference) {
        if (workingHoursPreference == null) {
            return new ArrayList<>();
        }
        Matcher matcher = Pattern.compile("([1-9]\\d*\\.?\\d+)|(0\\.\\d*[1-9])|(\\d+)").matcher(workingHoursPreference);
        List<Double> list = new ArrayList<>();
        while (matcher.find()) {
            double time = Double.parseDouble(matcher.group());
            list.add(time);
        }
        return list;
    }

    public static void main(String[] args) {
        List<EmployeeScoreDTO> dtos = new ArrayList<>();
        Employee employee = new Employee();
        PreferenceContent preferenceContent = JSONUtil.toBean("{\"workingDayPreference\":\"1\",\"workingTimePreference\":\"08:30-20:00\",\"workingHoursPreference\":\"4\"}",
                PreferenceContent.class);
        employee.setPreferenceContent(preferenceContent);
        EmployeeScoreDTO employeeScoreDTO = new EmployeeScoreDTO(employee);
        dtos.add(employeeScoreDTO);
        EmployeeFiller filler = new EmployeeFiller(dtos, "2023-02-13");
        filler.processingPriority(null);
    }
}
