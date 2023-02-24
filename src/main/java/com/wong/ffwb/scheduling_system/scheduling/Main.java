package com.wong.ffwb.scheduling_system.scheduling;


import com.wong.ffwb.scheduling_system.scheduling.genetic.impl.AtomizationCrossOverSupport;
import com.wong.ffwb.scheduling_system.scheduling.genetic.impl.SchedulingEvalSupport;
import com.wong.ffwb.scheduling_system.scheduling.genetic.impl.SchedulingGeneticAlgorithm;
import com.wong.ffwb.scheduling_system.scheduling.genetic.impl.SchedulingMutationSupport;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.*;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CrossOveSupport crossOveSupport = new AtomizationCrossOverSupport(0.9, 1, 5);
        MutationSupport mutationSupport = new SchedulingMutationSupport(0.1, 1);
        EvalSupport evalSupport = new SchedulingEvalSupport(1, 1);
        GeneticAlgorithm ga = new SchedulingGeneticAlgorithm(crossOveSupport, mutationSupport, evalSupport, 10, 1000000);
        List<Integer> list = Arrays.asList(0, 1, 1, 2, 3, 4, 4, 5, 5, 7, 8, 6, 5, 5, 5, 4, 4, 3, 3, 2, 2, 2, 1, 1, 1, 1);
        for (int i = 0; i < 10; i++) {
            Population population = ga.initPopulation(list);
            ga.evalPopulation(population);
            int generation = 1;
            System.out.println("Best solution: " + population.getFittest(0).toString());
        }
        Population population = ga.initPopulation(list);
//        ga.evalPopulation(population);
//        int generation = 1;
//        while (!ga.isTerminationConditionMet(generation)) {
//            System.out.println("Best solution: " + population.getFittest(0).toString());
//            // Apply crossover
//            population = ga.crossOverPopulation(population, list);
//
//            // Apply mutation
//            population = ga.mutationPopulation(population);
//
//            // Evaluate population
//            ga.evalPopulation(population);
//
//            // Increment the current generation
//            generation++;
//        }
//        System.out.println("Found solution in " + generation + " generations");
//        System.out.println("Best solution: " + population.getFittest(0).toString());
    }
}
