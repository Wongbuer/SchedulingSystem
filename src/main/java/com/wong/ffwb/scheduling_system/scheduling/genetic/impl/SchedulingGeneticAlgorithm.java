package com.wong.ffwb.scheduling_system.scheduling.genetic.impl;

import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.*;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
public class SchedulingGeneticAlgorithm implements GeneticAlgorithm {
    private CrossOveSupport crossOveSupport;
    private MutationSupport mutationSupport;
    private EvalSupport evalSupport;
    private int maxGeneration;
    private int populationSize;

    @Override
    public Population initPopulation(List<Integer> personnelQuantities) {
        Population population = new SchedulingPopulation(populationSize);
        for (int i = 0; i < populationSize; i++) {
            Individual individual = new SchedulingIndividual(personnelQuantities);
            population.setIndividual(i, individual);
        }
        return population;
    }

    @Override
    public boolean isTerminationConditionMet(int generation) {
        return generation > maxGeneration;
    }

    public Population crossOverPopulation(Population population, List<Integer> personnelQuantities) {
        return crossOveSupport.crossoverPopulation(population, personnelQuantities);
    }

    public Population mutationPopulation(Population population) {
        return mutationSupport.mutationPopulation(population);
    }

    public void evalPopulation(Population population) {
        evalSupport.evalPopulation(population);
    }
}
