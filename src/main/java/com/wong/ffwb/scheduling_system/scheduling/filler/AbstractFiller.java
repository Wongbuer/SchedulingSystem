package com.wong.ffwb.scheduling_system.scheduling.filler;

import com.wong.ffwb.scheduling_system.model.dto.EmployeeScoreDTO;
import com.wong.ffwb.scheduling_system.model.dto.WorkUnit;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Individual;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象员工填充器
 *
 * @author Wongbuer
 * @date 2023/02/16
 */
public abstract class AbstractFiller implements Filler {
    protected List<List<Individual.ChromosomeUnit>> priorityList = new ArrayList<>(3);
    protected List<EmployeeScoreDTO> employeeScoreDTOList;

    public AbstractFiller(List<EmployeeScoreDTO> employeeScoreDTOList) {
        this.employeeScoreDTOList = employeeScoreDTOList;
    }

    /**
     * 填充操作
     *
     * @param feasibleSolution 可行解决方案
     * @return {@link List}<{@link WorkUnit}>
     */
    @Override
    public List<WorkUnit> fillingOperation(List<Individual.ChromosomeUnit> feasibleSolution) {
        List<WorkUnit> list = new ArrayList<>(feasibleSolution.size());
        processingPriority(feasibleSolution);
//        filling();
        return list;
    }

    abstract void processingPriority(List<Individual.ChromosomeUnit> list);
    abstract void filling(List<EmployeeScoreDTO> employeeScoreDTOList, List<Individual.ChromosomeUnit> list);
}
