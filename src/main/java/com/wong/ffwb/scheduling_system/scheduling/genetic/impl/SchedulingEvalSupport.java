package com.wong.ffwb.scheduling_system.scheduling.genetic.impl;

import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.EvalSupport;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Individual;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Population;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SchedulingEvalSupport implements EvalSupport {
    private double weightOfPeople;
    private double weightOfEmployee;

    @Override
    public void evalPopulation(Population population) {
        double populationFitness = 0;
        // 计算不同解的Fitness
        for (Individual individual : population.getIndividuals()) {
            populationFitness += calcFitness(individual);
        }
        population.setPopulationFitness(populationFitness);
    }

    @Override
    public double calcFitness(Individual individual) {
        List<Integer> personnelQuantities = individual.getPersonnelQuantities();
        int sum = personnelQuantities.stream().mapToInt(Integer::intValue).sum();
        // 取绝对值, 归一化处理
        double peopleFlowCostScore = 1.0 / Math.abs(sum) * weightOfPeople;
        // TODO: 用户偏好评估逻辑
        double employeePreferenceScore = 0;
        double fitness = peopleFlowCostScore + employeePreferenceScore;
        individual.setFitness(fitness);
        return fitness;
    }
}
