package com.example.sharding;

import cn.hutool.core.util.StrUtil;
import com.google.auto.service.AutoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@AutoService(ComplexKeysShardingAlgorithm.class)
public class ShopCodeTableShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    private static final Map<String, String> shopCodeMapping = new ConcurrentHashMap<>();

    public ShopCodeTableShardingAlgorithm() {
        shopCodeMapping.put("a", "0");
        shopCodeMapping.put("b", "1");
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
        String logicTableName = shardingValue.getLogicTableName();
        Map<String, Collection<String>> shardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        Collection<String> tenantCodeList = shardingValuesMap.get("tenant_code");
        Collection<String> shopCodeList = shardingValuesMap.get("shop_code");

        Set<String> shardingNumList = shopCodeList.stream()
                .map(shopCodeMapping::get)
                .filter(StrUtil::isNotEmpty)
                .collect(Collectors.toSet());
        Set<String> tableNames = new HashSet<>();
        for (String shardNum : shardingNumList) {
            tableNames.add(logicTableName + "_" + shardNum);
        }
        return tableNames;
    }
}
