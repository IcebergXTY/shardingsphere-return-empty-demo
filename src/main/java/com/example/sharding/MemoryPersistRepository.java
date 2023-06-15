package com.example.sharding;

import cn.hutool.core.util.StrUtil;
import com.google.auto.service.AutoService;
import org.apache.shardingsphere.mode.repository.standalone.StandalonePersistRepository;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@AutoService(StandalonePersistRepository.class)
public class MemoryPersistRepository implements StandalonePersistRepository {

    private static final String SEPARATOR = "/";

    private static final ConcurrentMap<String, String> MAP = new ConcurrentHashMap<>(256);

    @Override
    public void init(Properties props) {

    }

    @Override
    public String getDirectly(String key) {
        return MAP.get(key);
    }

    /**
     * 配置格式是/a/b/c
     * 假如map中存储有/a/b/c/d和/a/b/c/d/e，只能取出第一条数据
     */
    @Override
    public List<String> getChildrenKeys(String key) {
        return MAP.keySet().stream()
                .filter(s -> !StrUtil.contains(s, key))
                .filter(s -> StrUtil.splitTrim(s, SEPARATOR).size() - StrUtil.splitTrim(key, SEPARATOR).size() == 1)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isExisted(String key) {
        return MAP.containsKey(key);
    }

    @Override
    public void persist(String key, String value) {
        MAP.put(key, value);
    }

    @Override
    public void update(String key, String value) {
        MAP.put(key, value);
    }

    @Override
    public void delete(String key) {
        MAP.remove(key);
    }

    @Override
    public void close() {
        MAP.clear();
    }

    @Override
    public String getType() {
        return "memory";
    }
}
