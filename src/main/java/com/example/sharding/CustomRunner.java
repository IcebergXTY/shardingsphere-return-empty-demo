package com.example.sharding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class CustomRunner implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        String sql = "select concat(data_source_id, '_', cost_item_name) as ruleKey, " +
                "    sum(ori_amount) as ori_amount " +
                "from cost_item_order_detail " +
                "where tenant_code = 'a'" +
                "    and platform_code = 'b'" +
                "    and shop_code = 'c'" +
                "    and (" +
                "        data_source_id = '111'" +
                "        and cost_item_name = '222'" +
                "    )" +
                "    or (" +
                "        data_source_id = '333'" +
                "        and cost_item_name = '444'" +
                "    ) " +
                "group by data_source_id," +
                "    cost_item_name";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        System.out.println(map);
    }
}
