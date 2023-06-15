package com.example.sharding;

import com.google.auto.service.AutoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AutoService(StandardShardingAlgorithm.class)
public class TenantCodeStandardAlgorithm implements StandardShardingAlgorithm<String> {

    private static final Map<String, String> tenantCodeMapping = new ConcurrentHashMap<>();

    public TenantCodeStandardAlgorithm() {
        tenantCodeMapping.put("a", "ds_0");
        tenantCodeMapping.put("b", "ds_1");
        tenantCodeMapping.put("c", "ds_2");
        tenantCodeMapping.put("d", "ds_3");
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        String tenantCode = shardingValue.getValue();
        return tenantCodeMapping.get(tenantCode);
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        throw new UnsupportedOperationException();
    }
}

