package com.wong.ffwb.scheduling_system.scheduling.filler;

import com.wong.ffwb.scheduling_system.model.dto.WorkUnit;
import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Individual;

import java.util.List;

public interface Filler {

    List<WorkUnit> fillingOperation(List<Individual.ChromosomeUnit> feasibleSolution);
}
