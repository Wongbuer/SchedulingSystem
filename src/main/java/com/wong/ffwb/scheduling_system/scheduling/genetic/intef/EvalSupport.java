package com.wong.ffwb.scheduling_system.scheduling.genetic.intef;

public interface EvalSupport {
    void evalPopulation(Population population);
    double calcFitness(Individual individual);
}
