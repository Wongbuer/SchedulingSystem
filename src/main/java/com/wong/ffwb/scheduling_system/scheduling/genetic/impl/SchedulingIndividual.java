package com.wong.ffwb.scheduling_system.scheduling.genetic.impl;

import com.wong.ffwb.scheduling_system.scheduling.genetic.intef.Individual;

import java.util.ArrayList;
import java.util.List;

public class SchedulingIndividual implements Individual {
    private List<ChromosomeUnit> chromosomes;
    private List<Integer> personnelQuantities;
    private double fitness = -1;

    public SchedulingIndividual() {
        chromosomes = new ArrayList<>(50);
    }

    public SchedulingIndividual(List<Integer> personnelQuantities) {
        this.personnelQuantities = new ArrayList<>(personnelQuantities);
        chromosomes = new ArrayList<>(50);
        do {
            // TODO: 修改开始时间与结束时间硬编码
            double[] time = randomTime(8.0, 21.0);
            ChromosomeUnit chromosomeUnit = new ChromosomeUnit(time[0], time[1], (int) (Math.random() * 4), time[1] - time[0]);
            chromosomes.add(chromosomeUnit);
        } while (!testValidity(this, 8.0, 21.0, size() - 1));
    }

    public void setPersonnelQuantities(List<Integer> personnelQuantities) {
        this.personnelQuantities = new ArrayList<>(personnelQuantities);
    }

    public static double[] randomTime(double beginTime, double endTime) {
        // 每半个小时为一个单位, endTime - beginTime为半小时的数量
        // 因为是时间节点, 所以需要 + 1
        int unitCount = (int) ((endTime - beginTime) / 0.5) + 1;
        // 范围缩小至关店前两小时
        int beginRange = unitCount - 4;
        int beginIndex = 0, endIndex = 0;
        do {
            // 生成随机开始时间索引
            beginIndex = (int) (Math.random() * beginRange);
            // 结束时间生成(4-8随机数)
            endIndex = (int) (Math.random() * 5) + 4 + beginIndex;
            // 判断结束时间是否合法
        } while (endIndex * 0.5 + beginTime > endTime);
        // 返回时间数组
        return new double[]{beginIndex * 0.5 + beginTime, endIndex * 0.5 + beginTime};
    }

    public static boolean testValidity(Individual individual, double start, double end, int offset) {
        List<ChromosomeUnit> chromosomes = individual.getChromosomeUnitList();
        List<Integer> quantities = individual.getPersonnelQuantities();
//        for (ChromosomeUnit chromosome : chromosomes) {
//            double beginTime = chromosome.getBeginTime();
//            double endTime = chromosome.getEndTime();
//            int beginIndex = (int) ((beginTime - start) / 0.5);
//            int endIndex = (int) ((endTime - beginTime) / 0.5) + beginIndex;
//            for (int i = beginIndex; i < endIndex; i++) {
//                Integer integer = quantities.get(i);
//                quantities.set(i, integer - 1);
//            }
//        }
        if (offset < 0)
            return false;
        for (int i = offset; i < chromosomes.size(); i++) {
            ChromosomeUnit unit = chromosomes.get(i);
            double beginTime = unit.getBeginTime();
            double endTime = unit.getEndTime();
            int beginIndex = (int) ((beginTime - start) / 0.5);
            int endIndex = (int) ((endTime - beginTime) / 0.5) + beginIndex;
            for (int j = beginIndex; j < endIndex; j++) {
                Integer integer = quantities.get(j);
                quantities.set(j, integer - 1);
            }
        }
        int max = quantities.stream().mapToInt(Integer::intValue).max().getAsInt();
        return max <= 0;
    }

    @Override
    public List<ChromosomeUnit> getChromosomeUnitList() {
        return this.chromosomes;
    }

    @Override
    public List<Integer> getPersonnelQuantities() {
        return this.personnelQuantities;
    }

    @Override
    public void setGene(int offset, ChromosomeUnit chromosomeUnit) {
        if (offset < size())
            chromosomes.set(offset, chromosomeUnit);
        else
            chromosomes.add(chromosomeUnit);
    }

    @Override
    public ChromosomeUnit getGene(int offset) {
        return chromosomes.get(offset);
    }

    @Override
    public double getFitness() {
        return this.fitness;
    }

    @Override
    public int size() {
        return this.chromosomes.size();
    }

    @Override
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(this.fitness, o.getFitness());
    }

    @Override
    public String toString() {
        String output = "";
        for (ChromosomeUnit chromosome : chromosomes) {
            output += chromosome;
        }
        return output + "///// fitness: " + fitness;
    }
}
