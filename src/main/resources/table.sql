CREATE TABLE `cost_item_order_detail`
(
    `id`             bigint unsigned NOT NULL COMMENT '主键',
    `tenant_code`    varchar(32)    NOT NULL COMMENT '',
    `platform_code`  varchar(32)    NOT NULL COMMENT '',
    `shop_code`      varchar(32)    NOT NULL COMMENT '',
    `data_source_id` int            NOT NULL COMMENT '',
    `cost_item_name` varchar(32)    NOT NULL COMMENT '',
    `ori_amount`     decimal(16, 6) NOT NULL COMMENT '',
    `update_time`    datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `updator`        varchar(32)    NOT NULL DEFAULT 'system' COMMENT '更新人',
    PRIMARY KEY (`id`)
);