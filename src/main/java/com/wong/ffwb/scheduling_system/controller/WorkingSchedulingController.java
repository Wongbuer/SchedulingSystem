package com.wong.ffwb.scheduling_system.controller;


import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
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
import com.wong.ffwb.scheduling_system.service.EmployeeService;
import com.wong.ffwb.scheduling_system.service.PreferenceService;
import com.wong.ffwb.scheduling_system.service.WorkingSchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (WorkingScheduling)表控制层
 *
 * @author Wongbuer
 * @since 2023-02-24 17:39:39
 */
@Validated
@RestController
@RequestMapping("workingScheduling")
public class WorkingSchedulingController {
    /**
     * 服务对象
     */
    @Resource
    private WorkingSchedulingService workingSchedulingService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private PreferenceService preferenceService;
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private WorkingSchedulingDao workingSchedulingDao;

    /**
     * 分页查询所有数据
     *
     * @param pageRequest       分页请求对象
     * @param workingScheduling 查询实体
     * @return 所有数据
     */
    @Operation(summary = "分页查询数据")
    @Parameters({
            @Parameter(name = "pageRequest", description = "分页请求对象"),
            @Parameter(name = "workingScheduling", description = "查询实体")
    })
    @GetMapping
    public BaseResponse<List<WorkingScheduling>> selectByPage(@NotNull PageRequest pageRequest, WorkingScheduling workingScheduling) {
        return workingSchedulingService.selectByPage(pageRequest, workingScheduling);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @Operation(summary = "通过主键查询单条数据")
    @Parameters({
            @Parameter(name = "id", description = "主键")
    })
    @GetMapping("{id}")
    public BaseResponse<WorkingScheduling> selectOne(@NotNull @PathVariable Serializable id) {
        return workingSchedulingService.selectOne(id);
    }

    /**
     * 新增数据
     *
     * @param workingScheduling 实体对象
     * @return 新增结果
     */
    @Operation(summary = "新增数据")
    @Parameters({
            @Parameter(name = "workingScheduling", description = "实体对象")
    })
    @PostMapping
    public BaseResponse<Boolean> insert(@Valid @RequestBody WorkingScheduling workingScheduling) {
        return workingSchedulingService.insert(workingScheduling);
    }

    /**
     * 修改数据
     *
     * @param workingScheduling 实体对象
     * @return 修改结果
     */
    @Operation(summary = "修改数据")
    @Parameters({
            @Parameter(name = "workingScheduling", description = "实体对象")
    })
    @PutMapping
    public BaseResponse<Boolean> update(@RequestBody WorkingScheduling workingScheduling) {
        return workingSchedulingService.updateOne(workingScheduling);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @Operation(summary = "删除数据”")
    @Parameters({
            @Parameter(name = "idList", description = "主键结合")
    })
    @DeleteMapping
    public BaseResponse<Boolean> delete(@NotEmpty @RequestParam("idList") List<Long> idList) {
        return workingSchedulingService.delete(idList);
    }

    @GetMapping("/test")
    public BaseResponse<?> test(String dateStr) {
        List<WorkingScheduling> workingSchedulingList = workingSchedulingDao.selectWorkingSchedulingByDate(dateStr);
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
        for (int i = 0; i < 3; i++) {
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
        workingSchedulingService.saveBatch(workingSchedulingList);
        return ResultUtils.success(units);
    }
}

