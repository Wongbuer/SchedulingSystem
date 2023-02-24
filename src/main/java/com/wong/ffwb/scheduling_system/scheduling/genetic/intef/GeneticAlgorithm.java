package com.wong.ffwb.scheduling_system.scheduling.genetic.intef;

import java.util.List;

public interface GeneticAlgorithm {
    Population initPopulation(List<Integer> personnelQuantities);

    boolean isTerminationConditionMet(int generation);

    Population crossOverPopulation(Population population, List<Integer> personnelQuantities);

    Population mutationPopulation(Population population);

    void evalPopulation(Population population);
}
