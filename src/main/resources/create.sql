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
)

