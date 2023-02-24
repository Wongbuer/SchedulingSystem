package com.wong.ffwb.scheduling_system.scheduling;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wong.ffwb.scheduling_system.exception.NotSuchElementException;
import com.wong.ffwb.scheduling_system.model.entity.Rule;
import com.wong.ffwb.scheduling_system.model.entity.Traffic;
import com.wong.ffwb.scheduling_system.service.RuleService;
import com.wong.ffwb.scheduling_system.service.TrafficService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SchedulingLogic {
    @Resource
    private TrafficService trafficService;
    @Resource
    private RuleService ruleService;

    public List<Integer> CountingEmployee(Long storeId, LocalDate date, Function<Double, Integer> numberProcess) throws NotSuchElementException {
        // 根据storeID获取指定日期的人流量
        LambdaQueryWrapper<Traffic> trafficWrapper = new LambdaQueryWrapper<>();
        trafficWrapper.eq(Traffic::getTrafficStoreId, storeId)
                .eq(Traffic::getTrafficDate, date);
        List<Traffic> trafficList = trafficService.list(trafficWrapper);

        // 根据storeID获取规则表中该店铺的自定义规则
        LambdaQueryWrapper<Rule> ruleWrapper = new LambdaQueryWrapper<>();
        ruleWrapper.eq(Rule::getRuleStoreId, storeId);
        Rule rule = ruleService.getOne(ruleWrapper);

        // 利用计算公式算出人数, 并通过函数式接口确定处理逻辑(四舍五入还是向上取整)
        if (BeanUtil.isEmpty(rule)) {
            throw new NotSuchElementException("该店铺不存在自定义规则");
        }
        Double demandNumber = rule.getRuleDemandNumber();
        List<Integer> demandList = trafficList.stream()
                .map(traffic -> traffic.getTrafficCount() / demandNumber)
                .map(numberProcess).collect(Collectors.toList());

        // 返回数组
        return demandList;
    }

    public List<Integer> test(List<Integer> list) {
        List<Integer> list1 = new ArrayList<>();
        List<int[]> list2 = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            int count = 0;
            List<Integer> temp = new ArrayList<>();
            temp.addAll(list);
            int best = 1000;
            List<int[]> list3 = new ArrayList<>();
            while (true) {
                count++;
                int begin = (int) (Math.random() * (temp.size() - 4));
                int end = 4 + (int) (Math.random() * 4);
                end += begin;
                for (int j = begin; j < end - 2; j++) {
                    Integer integer = temp.get(j);
                    temp.set(j, integer - 1);
                }
                list3.add(new int[]{begin, end});
                boolean isBreak = true;
                for (int i1 = 0; i1 < temp.size(); i1++) {
                    if (temp.get(i1) > 0)
                        isBreak = false;
                }
                if (isBreak) {
                    break;
                }
            }
            list1.add(count);
            if (count < 30) {
                list2 = list3;
            }
        }
        list2.forEach(ints -> {
            double i = ints[0] / 2.0 + 8;
            double j = ints[1] / 2.0 + 8;
            System.out.println("--------" + i + ":" + j + "----------");
        });
        System.out.println(list2.size());
        return list1;
    }
}
