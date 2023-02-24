package com.wong.ffwb.scheduling_system.scheduling.genetic.impl;

import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Individual;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Population;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SchedulingPopulation implements Population {
    private double populationFitness = -1;
    private List<Individual> individualList;

    public SchedulingPopulation(int populationSize) {
        this.individualList = new ArrayList<>(populationSize);
    }

    @Override
    public double getFitness() {
        return this.populationFitness;
    }

    @Override
    public Individual getFittest(int offset) {
        this.individualList.sort(Comparator.reverseOrder());
        return this.individualList.get(offset);
    }

    @Override
    public int size() {
        return this.individualList.size();
    }

    @Override
    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }

    @Override
    public List<Individual> getIndividuals() {
        return this.individualList;
    }

    @Override
    public Individual getIndividual(int offset) {
        return this.individualList.get(offset);
    }

    @Override
    public void setIndividual(int offset, Individual individual) {
        if (this.individualList.size() > offset)
            this.individualList.set(offset, individual);
        else
            this.individualList.add(individual);
    }
}
