package com.wong.ffwb.scheduling_system.scheduling.genetic.impl;

import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.CrossOveSupport;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Population;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Individual;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class AtomizationCrossOverSupport implements CrossOveSupport {
    private double crossOverRate;
    private int eliteCount;
    private int rateNumber;

    @Override
    public Population crossoverPopulation(Population population, List<Integer> personnelQuantities) {
        Population newPopulation = new SchedulingPopulation(population.size());
        List<Individual> individuals = population.getIndividuals();
        for (int i = 0; i < individuals.size(); i++) {
            // 获取第一个父代
            Individual parent1 = population.getFittest(i);
            // 判断是否满足交叉率, 是否为精英个体
            if (crossOverRate > Math.random() && i > eliteCount) {
                // 选取第二个父母
                Individual parent2 = selectParent(population);
                // 声明子代
                Individual offSpring = new SchedulingIndividual();
                offSpring.setPersonnelQuantities(personnelQuantities);
                // 将父代原子化
                List<Individual.ChromosomeUnit> units = new ArrayList<>(parent1.size() + parent2.size());
                units.addAll(parent1.getChromosomeUnitList());
                units.addAll(parent2.getChromosomeUnitList());
                int length = 0;
                do {
                    int index = (int) (Math.random() * units.size());
//                    System.out.println(index + "----------" + units.size());
                    Individual.ChromosomeUnit unit = units.get(index);
                    units.remove(index);
                    offSpring.setGene(length, unit);
                    length++;
                } while (units.size() > 0 && !SchedulingIndividual.testValidity(offSpring, 8.0, 21.0, length - 1));
                newPopulation.setIndividual(i, offSpring);
            }
            newPopulation.setIndividual(i, parent1);
        }
        return newPopulation;
    }

    private Individual selectParent(Population population) {
        Population newPopulation = new SchedulingPopulation(rateNumber);
        shuffle(population);
        for (int i = 0; i < rateNumber; i++) {
            Individual individual = population.getIndividual((int) (Math.random() * population.size()));
            newPopulation.setIndividual(i, individual);
        }
        return newPopulation.getFittest(0);
    }

    private void shuffle(Population population) {
        Random rnd = new Random();
        for (int i = population.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Individual a = population.getIndividual(index);
            population.setIndividual(index, population.getIndividual(i));
            population.setIndividual(i, a);
        }
    }
}
