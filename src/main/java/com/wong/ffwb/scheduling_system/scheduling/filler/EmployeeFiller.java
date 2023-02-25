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
import java.util.stream.IntStream;

/**
 * 员工填充器
 *
 * @author Wongbuer
 * @date 2023/02/16
 */
public class EmployeeFiller extends AbstractFiller {
    private final Week week;
    private double exchangeRate;
    private final Map<Individual.ChromosomeUnit, EmployeeScoreDTO> dayCacheMap = new HashMap<>();
    private final Map<Individual.ChromosomeUnit, EmployeeScoreDTO> hourCacheMap = new HashMap<>();


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
        for (EmployeeScoreDTO employeeScoreDTO : employeeScoreDTOList) {
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
            if (dayPreference.contains(week.ordinal())) {
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
        }
    }

    @Override
    List<WorkUnit> filling(List<EmployeeScoreDTO> employeeScoreDTOList, List<Individual.ChromosomeUnit> chromosomeUnitList) {
        List<EmployeeScoreDTO> dtos = new ArrayList<>(employeeScoreDTOList);
        List<Individual.ChromosomeUnit> list = new ArrayList<>(chromosomeUnitList);
        List<WorkUnit> workUnitList = new ArrayList<>(list.size());

        // 循环, 直到所有班次都被安排
//        do {
//            // 员工分数排序
//            dtos = dtos.stream()
//                    .sorted(Comparator.comparing(EmployeeScoreDTO::getScore).reversed())
//                    .collect(Collectors.toList());
//            // 提取分数最高员工
//            EmployeeScoreDTO employeeScoreDTO = dtos.get(0);
//            int score = employeeScoreDTO.getScore();
//            // 判断优先级是否为工作日优先级
//            if ((score & 4) == 4) {
//                // 移除员工列表
//                dtos.remove(0);
//                // 根据职位选择适合班次
//                Individual.ChromosomeUnit unit = findChromosomeUnitByPosition(list, employeeScoreDTO.getEmployee().getEmployeePosition());
//                // 移除排班列表
//                list.remove(unit);
//                // 添加至工作日偏好缓存
//                dayCacheMap.put(unit, employeeScoreDTO);
//            } else if ((score & 2) == 2) {
//                // 根据时间和职位查找染色体
//                Individual.ChromosomeUnit unit = findChromosomeUnitByTimeAndPosition(list, employeeScoreDTO);
//                // 找到对应班次
//                if (unit != null) {
//                    list.remove(unit);
//                    hourCacheMap.put(unit, employeeScoreDTO);
//                } else {
//                    // 小于交换率
//                    if (Math.random() < exchangeRate) {
//                        Individual.ChromosomeUnit unit1 = findChromosomeUnitByTimeAndPosition(dayCacheMap.keySet().stream().toList(), employeeScoreDTO);
//                        if (unit1 != null) {
//
//                        }
//                    }
//                }
//            } else if ((score & 1) == 1) {
//
//            }
//        } while (true);
        for (EmployeeScoreDTO employeeScoreDTO : employeeScoreDTOList) {
            Individual.ChromosomeUnit unit = list.get(0);
            LocalTime beginTime = transformDoubleToTime(unit.getBeginTime());
            LocalTime endTime = transformDoubleToTime(unit.getEndTime());
            WorkUnit workUnit = WorkUnit.builder()
                    .employee(employeeScoreDTO.getEmployee())
                    .beginTime(beginTime)
                    .endTime(endTime)
                    .build();
            workUnitList.add(workUnit);
            list.remove(unit);
        }
        if (list.size() > 0) {
            for (Individual.ChromosomeUnit unit : list) {
                int empSize = employeeScoreDTOList.size();
                EmployeeScoreDTO dto = employeeScoreDTOList.get((int) (Math.random() * empSize));
                LocalTime beginTime = transformDoubleToTime(unit.getBeginTime());
                LocalTime endTime = transformDoubleToTime(unit.getEndTime());
                WorkUnit workUnit = WorkUnit.builder()
                        .employee(dto.getEmployee())
                        .beginTime(beginTime)
                        .endTime(endTime)
                        .build();
                workUnitList.add(workUnit);
            }
        }
        return workUnitList;
    }

    private Individual.ChromosomeUnit findChromosomeUnitByPosition(List<Individual.ChromosomeUnit> list, int position) {
        for (Individual.ChromosomeUnit chromosomeUnit : list) {
            if (chromosomeUnit.getPosition() == position)
                return chromosomeUnit;
        }
        return null;
    }

    /**
     * 根据时间和职位查找染色体
     *
     * @param list             列表
     * @param employeeScoreDTO 员工分数dto
     * @return {@link Individual.ChromosomeUnit}
     */
    private Individual.ChromosomeUnit findChromosomeUnitByTimeAndPosition(List<Individual.ChromosomeUnit> list, EmployeeScoreDTO employeeScoreDTO) {
        for (Individual.ChromosomeUnit chromosomeUnit : list) {
            double beginTime = chromosomeUnit.getBeginTime();
            double endTime = chromosomeUnit.getEndTime();
            List<Double> hoursPreference = employeeScoreDTO.getHoursPreference();
            Integer position = employeeScoreDTO.getEmployee().getEmployeePosition();
            if (hoursPreference.contains(beginTime) && hoursPreference.contains(endTime) && chromosomeUnit.getPosition() == position) {
                return chromosomeUnit;
            }
        }
        return list.get(0);
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
