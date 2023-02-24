package com.wong.ffwb.scheduling_system.scheduling.genetic.impl;

import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Individual;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.MutationSupport;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Population;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SchedulingMutationSupport implements MutationSupport {
    private double mutationRate;
    private int eliteCount;

    @Override
    public Population mutationPopulation(Population population) {
        SchedulingPopulation newPopulation = new SchedulingPopulation(population.size());
        for (int i = 0; i < population.size(); i++) {
            // 获取染色体个体
            Individual individual = population.getFittest(i);
            // 判断是否为精英个体
            if (i > eliteCount) {
                // 循环基因组
                for (int j = 0; j < individual.getChromosomeUnitList().size(); j++) {
                    // 判断是否满足变异率
                    if (Math.random() < mutationRate) {
                        // TODO: 修改开始结束时间硬编码
                        double[] time = SchedulingIndividual.randomTime(8.0, 21.0);
                        // TODO: 修改随机职位硬编码
                        Individual.ChromosomeUnit gene = new Individual.ChromosomeUnit(time[0], time[1], (int) (Math.random() * 4), time[1] - time[0]);
                        // 尝试修改基因
                        Individual.ChromosomeUnit oldGene = individual.getGene(j);
                        individual.setGene(j, gene);
                        // 变异后如果为无效解, 则rollback至变异前
                        if (!SchedulingIndividual.testValidity(individual, 8.0, 21.0, 0)) {
                            individual.setGene(j, oldGene);
                        }
                    }
                }
            }
            newPopulation.setIndividual(i, individual);
        }
        return newPopulation;
    }
}
