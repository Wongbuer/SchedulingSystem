create database if not exists scheduling_system;

use scheduling_system;

-- `employee`
create table if not exists `employee`
(
    `employee_id`            bigint primary key comment '员工ID',
    `employee_name`          varchar(256) not null comment '员工姓名',
    `employee_email`         varchar(256) not null unique comment '员工邮箱',
    `employee_position`      tinyint      not null comment '员工职位',
#     `employee_address`       varchar(256) null comment '员工地址',
    `employee_preference_id` bigint       null comment '员工偏好id',
#     `employee_seniority`     int          null comment '员工工龄',
    `employee_store_id`      bigint       not null comment '员工所属门店id',
    `employee_gender`        varchar(5)   null comment '用户性别'
#     `employee_age`           int          null comment '用户年龄'
) comment '员工信息';

-- 门店表
create table if not exists `store`
(
    `store_id`         bigint       not null comment '门店id' primary key,
    `store_name`       varchar(256) not null unique comment '门店名称',
    `store_address`    varchar(256) null comment '门店地址',
    `store_traffic_id` bigint       not null comment '门店人流量表id',
    `store_size`       double       not null comment '门店面积'
) comment '门店信息';

-- 偏好表
create table if not exists `preference`
(
    `pref_id`      bigint       not null comment '偏好id' primary key,
    `pref_content` varchar(256) not null comment '偏好内容(JSON)'
) comment '偏好信息';

-- 人流量表
create table if not exists `traffic`
(
    `traffic_id`         bigint not null comment '人流量id' primary key,
    `traffic_store_id`   bigint not null comment '门店id',
    `traffic_date`       date   not null comment '日期信息',
    `traffic_begin_time` time   not null comment '人流量阶段起始时间',
    `traffic_end_time`   time   not null comment '人流量阶段结束时间',
    `traffic_count`      double not null comment '用户名'
) comment '人流量信息';

# -- 分数表
# create table if not exists `score`
# (
#     `score_id`          bigint   not null comment '分数id' primary key,
#     `score_employee_id` bigint   not null comment '员工id',
#     `score_begin_time`  datetime not null comment '起始时段',
#     `score_end_time`    datetime not null comment '终止时段',
#     `score`             int      not null comment '分数'
# ) comment '分数信息';

-- 规则表
create table if not exists `rule`
(
    `rule_id`               int primary key comment '规则id',
    `rule_store_id`         bigint not null comment '规则所指店铺ID',
    `rule_prepare_time`     double not null default 0.5 comment '开店前的准备时间(默认值0.5)',
    `rule_prepare_number`   int    not null default 50 comment '开店前的员工参数(默认值50)',
    `rule_prepare_position` int    null comment '开店前的指定职位',
    `rule_demand_number`    double not null default 3.8 comment '员工需求参数(默认值3.8)',
    `rule_duty_position`    int    null comment '工作时的指定职位',
    `rule_duty_number`      int    not null default 1 comment '无客流时值班人数(默认值1)',
    `rule_close_time`       double not null default 2 comment '关店前打点时间(默认值2)',
    `rule_close_number1`    int    not null default 30 comment '关店打点人数参数一(默认值30)',
    `rule_close_number2`    int    not null default 2 comment '关店打点人数参数二(默认值2)',
    `rule_close_position`   int    null comment '关店打点的指定职位'
);

-- 排班表
create table if not exists `working_scheduling`
(
    `scheduling_id`          bigint primary key comment '排班主键',
    `scheduling_begin_time`  datetime not null comment '起始时间',
    `scheduling_end_time`    datetime not null comment '终止时间',
    `scheduling_employee_id` bigint   not null comment '员工id'
);

INSERT INTO `employee`
VALUES (1626867304941228034, '胡磊', 'u.enirtrgc@qq.com', 0, NULL, 1, '女');
INSERT INTO `employee`
VALUES (1626867809335644162, '李艳', 's.fvagm@gnignouiv.ve', 3, 1625850156500926466, 1, '男');
INSERT INTO `employee`
VALUES (1626867817229324289, '郝静', 's.gmxvhhd@chwx.nl', 3, 1625849467943010306, 1, '女');
INSERT INTO `employee`
VALUES (1626867822434455554, '杜平', 's.bvp@huhmtt.wf', 3, 1627950661859049474, 1, '女');
INSERT INTO `employee`
VALUES (1626867826502930433, '冯军', 'b.oidj@krxsjp.ec', 3, 1627950867375751169, 1, '女');
INSERT INTO `employee`
VALUES (1626867830814674945, '潘静', 'n.oaa@pkjtozrfp.org', 3, 1627950885134434305, 1, '女');
INSERT INTO `employee`
VALUES (1626867834874761217, '邹艳', 'q.cwcesn@hpjuln.coop', 3, 1627950888301133825, 1, '男');
INSERT INTO `employee`
VALUES (1626867838477668354, '陈超', 'g.lxckc@llfynn.pm', 3, 1627950890670915586, 1, '女');
INSERT INTO `employee`
VALUES (1626867842344816642, '郑静', 'z.lywmujtad@kfdfq.tv', 3, NULL, 1, '男');
INSERT INTO `employee`
VALUES (1626867845498933250, '汪强', 'l.rpvj@cbyl.kz', 3, NULL, 1, '男');
INSERT INTO `employee`
VALUES (1626867849265418241, '段艳', 'o.arufx@hbsxvtrvog.at', 3, NULL, 1, '男');
INSERT INTO `employee`
VALUES (1626867869200945153, '卢秀兰', 'a.eqdsskj@ftbzd.mx', 2, NULL, 1, '男');
INSERT INTO `employee`
VALUES (1626867884233330689, '孙磊', 'd.mueoeiu@enhns.sg', 2, NULL, 1, '男');
INSERT INTO `employee`
VALUES (1626867904961581058, '孔霞', 'm.xbvq@rtbpoga.kz', 1, NULL, 1, '男');

INSERT INTO `preference`
VALUES (1625849467943010306,
        '{\"workingDayPreference\":\"1\",\"workingTimePreference\":\"08:30-20:00\",\"workingHoursPreference\":\"4\"}');
INSERT INTO `preference`
VALUES (1625850156500926466,
        '{\"workingDayPreference\":\"4\",\"workingTimePreference\":\"08:30-20:00\",\"workingHoursPreference\":\"4\"}');
INSERT INTO `preference`
VALUES (1627950661859049474,
        '{\"workingTimePreference\":\"11:30-15:00\",\"workingHoursPreference\":\"2.5\",\"workingDayPreference\":\"2-6\"}');
INSERT INTO `preference`
VALUES (1627950867375751169,
        '{\"workingTimePreference\":\"15:00-18:00\",\"workingHoursPreference\":\"3\",\"workingDayPreference\":\"3-5\"}');
INSERT INTO `preference`
VALUES (1627950885134434305,
        '{\"workingTimePreference\":\"15:00-18:00\",\"workingHoursPreference\":\"3\",\"workingDayPreference\":\"3-5\"}');
INSERT INTO `preference`
VALUES (1627950888301133825,
        '{\"workingTimePreference\":\"15:00-18:00\",\"workingHoursPreference\":\"3\",\"workingDayPreference\":\"3-5\"}');
INSERT INTO `preference`
VALUES (1627950890670915586,
        '{\"workingTimePreference\":\"15:00-18:00\",\"workingHoursPreference\":\"3\",\"workingDayPreference\":\"3-5\"}');


INSERT INTO `traffic`
VALUES (1627957901802377217, 1627957171406266370, '2023-05-10', '08:30:00', '09:00:00', 0.1);
INSERT INTO `traffic`
VALUES (1627957905535307777, 1627957171406266370, '2023-05-10', '09:00:00', '09:30:00', 1.3);
INSERT INTO `traffic`
VALUES (1627957905598222337, 1627957171406266370, '2023-05-10', '09:30:00', '10:00:00', 5.7);
INSERT INTO `traffic`
VALUES (1627957905694691330, 1627957171406266370, '2023-05-10', '10:00:00', '10:30:00', 11.1);
INSERT INTO `traffic`
VALUES (1627957905732440066, 1627957171406266370, '2023-05-10', '10:30:00', '11:00:00', 13.4);
INSERT INTO `traffic`
VALUES (1627957905858269185, 1627957171406266370, '2023-05-10', '11:00:00', '11:30:00', 13.3);
INSERT INTO `traffic`
VALUES (1627957905896017922, 1627957171406266370, '2023-05-10', '11:30:00', '12:00:00', 17.3);
INSERT INTO `traffic`
VALUES (1627957906026041346, 1627957171406266370, '2023-05-10', '12:00:00', '12:30:00', 18.1);
INSERT INTO `traffic`
VALUES (1627957906088955905, 1627957171406266370, '2023-05-10', '12:30:00', '13:00:00', 22.8);
INSERT INTO `traffic`
VALUES (1627957906193813505, 1627957171406266370, '2023-05-10', '13:00:00', '13:30:00', 26.9);
INSERT INTO `traffic`
VALUES (1627957906256728065, 1627957171406266370, '2023-05-10', '13:30:00', '14:00:00', 21.6);
INSERT INTO `traffic`
VALUES (1627957906294476802, 1627957171406266370, '2023-05-10', '14:00:00', '14:30:00', 18.3);
INSERT INTO `traffic`
VALUES (1627957906361585666, 1627957171406266370, '2023-05-10', '14:30:00', '15:00:00', 17.2);
INSERT INTO `traffic`
VALUES (1627957906495803393, 1627957171406266370, '2023-05-10', '15:00:00', '15:30:00', 15.3);
INSERT INTO `traffic`
VALUES (1627957906558717953, 1627957171406266370, '2023-05-10', '15:30:00', '16:00:00', 14.3);
INSERT INTO `traffic`
VALUES (1627957906659381250, 1627957171406266370, '2023-05-10', '16:00:00', '16:30:00', 11.6);
INSERT INTO `traffic`
VALUES (1627957906722295809, 1627957171406266370, '2023-05-10', '16:30:00', '17:00:00', 8.3);
INSERT INTO `traffic`
VALUES (1627957906789404674, 1627957171406266370, '2023-05-10', '17:00:00', '17:30:00', 8.3);
INSERT INTO `traffic`
VALUES (1627957906915233793, 1627957171406266370, '2023-05-10', '17:30:00', '18:00:00', 7.2);
INSERT INTO `traffic`
VALUES (1627957906957176833, 1627957171406266370, '2023-05-10', '18:00:00', '18:30:00', 5.6);
INSERT INTO `traffic`
VALUES (1627957907083005954, 1627957171406266370, '2023-05-10', '18:30:00', '19:00:00', 5.6);
INSERT INTO `traffic`
VALUES (1627957907145920513, 1627957171406266370, '2023-05-10', '19:00:00', '19:30:00', 2.5);
INSERT INTO `traffic`
VALUES (1627957907246583810, 1627957171406266370, '2023-05-10', '19:30:00', '20:00:00', 2.1);
INSERT INTO `traffic`
VALUES (1627957907309498369, 1627957171406266370, '2023-05-10', '20:00:00', '20:30:00', 0.1);
INSERT INTO `traffic`
VALUES (1627957907376607234, 1627957171406266370, '2023-05-10', '20:30:00', '21:00:00', 0.1);


INSERT INTO `working_scheduling`
VALUES (1629325928263970818, '2023-05-10 12:30:00', '2023-05-10 16:00:00', 1626867304941228034);
INSERT INTO `working_scheduling`
VALUES (1629325928263970819, '2023-05-10 12:00:00', '2023-05-10 15:00:00', 1626867809335644162);
INSERT INTO `working_scheduling`
VALUES (1629325928263970820, '2023-05-10 13:00:00', '2023-05-10 16:00:00', 1626867817229324289);
INSERT INTO `working_scheduling`
VALUES (1629325928263970821, '2023-05-10 10:30:00', '2023-05-10 13:30:00', 1626867822434455554);
INSERT INTO `working_scheduling`
VALUES (1629325928263970822, '2023-05-10 12:00:00', '2023-05-10 15:30:00', 1626867826502930433);
INSERT INTO `working_scheduling`
VALUES (1629325928263970823, '2023-05-10 09:00:00', '2023-05-10 12:30:00', 1626867830814674945);
INSERT INTO `working_scheduling`
VALUES (1629325928263970824, '2023-05-10 11:30:00', '2023-05-10 14:30:00', 1626867834874761217);
INSERT INTO `working_scheduling`
VALUES (1629325928263970825, '2023-05-10 08:30:00', '2023-05-10 11:30:00', 1626867838477668354);
INSERT INTO `working_scheduling`
VALUES (1629325928263970826, '2023-05-10 15:00:00', '2023-05-10 17:30:00', 1626867842344816642);
INSERT INTO `working_scheduling`
VALUES (1629325928263970827, '2023-05-10 15:30:00', '2023-05-10 18:30:00', 1626867845498933250);
INSERT INTO `working_scheduling`
VALUES (1629325928263970828, '2023-05-10 11:30:00', '2023-05-10 14:00:00', 1626867849265418241);
INSERT INTO `working_scheduling`
VALUES (1629325928263970829, '2023-05-10 14:30:00', '2023-05-10 17:00:00', 1626867869200945153);
INSERT INTO `working_scheduling`
VALUES (1629325928263970830, '2023-05-10 18:00:00', '2023-05-10 21:00:00', 1626867884233330689);
INSERT INTO `working_scheduling`
VALUES (1629325928263970831, '2023-05-10 15:30:00', '2023-05-10 19:00:00', 1626867904961581058);
INSERT INTO `working_scheduling`
VALUES (1629325928263970832, '2023-05-10 09:30:00', '2023-05-10 13:30:00', 1626867304941228034);
INSERT INTO `working_scheduling`
VALUES (1629345769637109761, '2023-05-11 12:30:00', '2023-05-11 14:30:00', 1626867304941228034);
INSERT INTO `working_scheduling`
VALUES (1629345769637109762, '2023-05-11 10:00:00', '2023-05-11 13:30:00', 1626867809335644162);
INSERT INTO `working_scheduling`
VALUES (1629345769637109763, '2023-05-11 15:00:00', '2023-05-11 18:00:00', 1626867817229324289);
INSERT INTO `working_scheduling`
VALUES (1629345769637109764, '2023-05-11 10:30:00', '2023-05-11 14:00:00', 1626867822434455554);
INSERT INTO `working_scheduling`
VALUES (1629345769637109765, '2023-05-11 14:00:00', '2023-05-11 17:30:00', 1626867826502930433);
INSERT INTO `working_scheduling`
VALUES (1629345769637109766, '2023-05-11 12:00:00', '2023-05-11 15:00:00', 1626867830814674945);
INSERT INTO `working_scheduling`
VALUES (1629345769637109767, '2023-05-11 11:00:00', '2023-05-11 14:30:00', 1626867834874761217);
INSERT INTO `working_scheduling`
VALUES (1629345769637109768, '2023-05-11 13:00:00', '2023-05-11 15:00:00', 1626867838477668354);
INSERT INTO `working_scheduling`
VALUES (1629345769637109769, '2023-05-11 08:30:00', '2023-05-11 12:00:00', 1626867842344816642);
INSERT INTO `working_scheduling`
VALUES (1629345769637109770, '2023-05-11 14:30:00', '2023-05-11 17:00:00', 1626867845498933250);
INSERT INTO `working_scheduling`
VALUES (1629345769637109771, '2023-05-11 15:00:00', '2023-05-11 17:30:00', 1626867849265418241);
INSERT INTO `working_scheduling`
VALUES (1629345769637109772, '2023-05-11 09:30:00', '2023-05-11 13:30:00', 1626867869200945153);
INSERT INTO `working_scheduling`
VALUES (1629345769637109773, '2023-05-11 18:00:00', '2023-05-11 21:00:00', 1626867884233330689);
INSERT INTO `working_scheduling`
VALUES (1629345769637109774, '2023-05-11 12:00:00', '2023-05-11 15:30:00', 1626867904961581058);
INSERT INTO `working_scheduling`
VALUES (1629345769637109775, '2023-05-11 17:30:00', '2023-05-11 20:30:00', 1626867849265418241);
