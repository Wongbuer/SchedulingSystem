package com.wong.ffwb.scheduling_system.scheduling.genetic.intef;

import java.util.List;

public interface Population {
    int size();
    void setPopulationFitness(double fitness);

    double getFitness();

    Individual getFittest(int offset);


    List<Individual> getIndividuals();

    Individual getIndividual(int offset);

    void setIndividual(int offset, Individual individual);
}
