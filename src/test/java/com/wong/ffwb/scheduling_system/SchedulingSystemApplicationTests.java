package com.wong.ffwb.scheduling_system;


import com.wong.ffwb.scheduling_system.dao.WorkingSchedulingDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SchedulingSystemApplicationTests {
    @Resource
    private WorkingSchedulingDao workingSchedulingDao;

    @Test
    void test() {
        workingSchedulingDao.selectWorkingSchedulingByDate("2023-05-10").forEach(System.out::println);
    }

}
