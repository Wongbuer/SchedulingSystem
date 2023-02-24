package com.wong.ffwb.scheduling_system.scheduling.genetic.intef;

import java.util.List;

@FunctionalInterface
public interface CrossOveSupport {
    Population crossoverPopulation(Population population, List<Integer> personnelQuantities);
}
