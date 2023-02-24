package com.wong.ffwb.scheduling_system.scheduling.genetic.intef;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public interface Individual extends Comparable<Individual> {
    List<ChromosomeUnit> getChromosomeUnitList();

    List<Integer> getPersonnelQuantities();

    void setGene(int offset, ChromosomeUnit chromosomeUnit);

    ChromosomeUnit getGene(int offset);

    void setPersonnelQuantities(List<Integer> personnelQuantities);

    double getFitness();

    int size();

    void setFitness(double fitness);

    @AllArgsConstructor
    @Getter
    @Setter
    class ChromosomeUnit {
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(beginTime).append(" ").append(endTime).append(" ").append(position).append(" ");
            return builder.toString();
        }

        private double beginTime;
        private double endTime;
        private int position;
        private double duration;
    }
}
