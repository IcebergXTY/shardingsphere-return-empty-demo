package com.example.sharding;

import cn.hutool.core.io.FileUtil;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;
import org.apache.shardingsphere.infra.config.mode.ModeConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.infra.util.yaml.YamlEngine;
import org.apache.shardingsphere.infra.yaml.config.pojo.YamlRootConfiguration;
import org.apache.shardingsphere.infra.yaml.config.swapper.mode.YamlModeConfigurationSwapper;
import org.apache.shardingsphere.infra.yaml.config.swapper.resource.YamlDataSourceConfigurationSwapper;
import org.apache.shardingsphere.infra.yaml.config.swapper.rule.YamlRuleConfigurationSwapperEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.File;
import java.util.Collection;
import java.util.Map;

@SpringBootApplication
public class ShardingExecutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingExecutorApplication.class, args);
    }

    @Bean
    public DataSource dataSource() throws Exception {
        File file = FileUtil.file("classpath:shardingsphere-config.yaml");
        YamlRootConfiguration rootConfig = YamlEngine.unmarshal(file, YamlRootConfiguration.class);
        YamlDataSourceConfigurationSwapper dataSourceSwapper = new YamlDataSourceConfigurationSwapper();
        Map<String, DataSource> dataSourceMap = dataSourceSwapper.swapToDataSources(rootConfig.getDataSources());

        YamlRuleConfigurationSwapperEngine swapperEngine = new YamlRuleConfigurationSwapperEngine();
        Collection<RuleConfiguration> ruleConfigs = swapperEngine.swapToRuleConfigurations(rootConfig.getRules());


        ModeConfiguration modeConfig = null == rootConfig.getMode() ? null : new YamlModeConfigurationSwapper().swapToObject(rootConfig.getMode());
        return new ShardingSphereDataSource(rootConfig.getDatabaseName(), modeConfig, dataSourceMap, ruleConfigs, rootConfig.getProps());
    }
}
