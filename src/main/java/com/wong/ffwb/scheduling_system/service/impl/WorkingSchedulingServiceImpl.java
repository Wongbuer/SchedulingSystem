package com.wong.ffwb.scheduling_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import com.wong.ffwb.scheduling_system.constant.CommonConstant;
import com.wong.ffwb.scheduling_system.dao.EmployeeDao;
import com.wong.ffwb.scheduling_system.dao.WorkingSchedulingDao;
import com.wong.ffwb.scheduling_system.model.dto.EmployeeScoreDTO;
import com.wong.ffwb.scheduling_system.model.dto.WorkUnit;
import com.wong.ffwb.scheduling_system.model.entity.Employee;
import com.wong.ffwb.scheduling_system.model.entity.WorkingScheduling;
import com.wong.ffwb.scheduling_system.scheduling.filler.EmployeeFiller;
import com.wong.ffwb.scheduling_system.scheduling.genetic.impl.AtomizationCrossOverSupport;
import com.wong.ffwb.scheduling_system.scheduling.genetic.impl.SchedulingEvalSupport;
import com.wong.ffwb.scheduling_system.scheduling.genetic.impl.SchedulingGeneticAlgorithm;
import com.wong.ffwb.scheduling_system.scheduling.genetic.impl.SchedulingMutationSupport;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Individual;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Population;
import com.wong.ffwb.scheduling_system.service.WorkingSchedulingService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (WorkingScheduling)表服务实现类
 *
 * @author Wongbuer
 * @since 2023-02-24 17:39:40
 */
@Service("workingSchedulingService")
public class WorkingSchedulingServiceImpl extends ServiceImpl<WorkingSchedulingDao, WorkingScheduling> implements WorkingSchedulingService {
    @Resource
    private EmployeeDao employeeDao;

    @Override
    public BaseResponse<List<WorkingScheduling>> selectByPage(PageRequest pageRequest, WorkingScheduling workingScheduling) {
        Page<WorkingScheduling> page = new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize());
        QueryWrapper<WorkingScheduling> queryWrapper = new QueryWrapper<>(workingScheduling);
        queryWrapper.orderBy(StringUtils.hasText(pageRequest.getSortField()), pageRequest.getSortOrder().equals(CommonConstant.SORT_ORDER_ASC), pageRequest.getSortField());
        page(page, queryWrapper);
        return ResultUtils.success(page.getRecords());
    }

    @Override
    public BaseResponse<WorkingScheduling> selectOne(Serializable id) {
        WorkingScheduling workingScheduling = getById(id);
        return ResultUtils.success(workingScheduling);
    }

    @Override
    public BaseResponse<Boolean> insert(WorkingScheduling workingScheduling) {
        boolean isSave = save(workingScheduling);
        return ResultUtils.success(isSave);
    }

    @Override
    public BaseResponse<Boolean> updateOne(WorkingScheduling workingScheduling) {
        boolean isUpdate = updateById(workingScheduling);
        return ResultUtils.success(isUpdate);
    }

    @Override
    public BaseResponse<Boolean> delete(List<Long> idList) {
        boolean isRemove = removeByIds(idList);
        return ResultUtils.success(isRemove);
    }

    @Override
    public BaseResponse<List<WorkingScheduling>> getSchedulingByDay(String dateStr) {
        List<WorkingScheduling> workingSchedulingList = baseMapper.selectWorkingSchedulingByDate(dateStr);
        if (workingSchedulingList.size() > 0) {
            return ResultUtils.success(workingSchedulingList);
        }
        List<EmployeeScoreDTO> employeeScoreDTOList = new LinkedList<>();
        List<Employee> employees = employeeDao.selectBatchWithPreferenceContent();
        List<Individual> candidateIndividualList = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeScoreDTO dto = new EmployeeScoreDTO(employee);
            employeeScoreDTOList.add(dto);
        }
        EmployeeFiller filler = new EmployeeFiller(employeeScoreDTOList, dateStr);
        SchedulingGeneticAlgorithm ga = new SchedulingGeneticAlgorithm(
                new AtomizationCrossOverSupport(0.9, -1, 10),
                new SchedulingMutationSupport(0.01, -1),
                new SchedulingEvalSupport(1, 0),
                1, 1000000);
        List<Integer> list = Arrays.asList(0, 1, 1, 2, 3, 4, 4, 5, 5, 7, 8, 6, 5, 5, 5, 4, 4, 3, 3, 2, 2, 2, 1, 1, 1, 1);
        for (int i = 0; i < 2; i++) {
            Population population = ga.initPopulation(list);
            ga.evalPopulation(population);
            int generation = 1;
            candidateIndividualList.add(population.getFittest(0));
            System.out.println("Best solution: " + population.getFittest(0).toString());
        }
        candidateIndividualList = candidateIndividualList.stream().sorted(Comparator.comparing(Individual::getFitness).reversed()).collect(Collectors.toList());
        Individual bestIndividual = candidateIndividualList.get(0);
        List<WorkUnit> units = filler.fillingOperation(bestIndividual.getChromosomeUnitList());
        for (WorkUnit unit : units) {
            WorkingScheduling workingScheduling = new WorkingScheduling();
            workingScheduling.setSchedulingEmployeeId(unit.getEmployee().getEmployeeId());
            workingScheduling.setSchedulingBeginTime(unit.getBeginTime().atDate(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            workingScheduling.setSchedulingEndTime(unit.getEndTime().atDate(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            workingSchedulingList.add(workingScheduling);
        }
        saveBatch(workingSchedulingList);
        return ResultUtils.success(workingSchedulingList);
    }
}

