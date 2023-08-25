package com.alver.springfx.autoconfigure;

import com.alver.springfx.SpringFXBuilder;
import com.alver.springfx.SpringFXBuilderFactory;
import com.alver.springfx.SpringFXLoader;
import javafx.util.BuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AutoConfiguration
public class SpringFXAutoConfiguration {

    private final ApplicationContext context;
    private final List<SpringFXBuilder<?>> builders;

    @Autowired
    public SpringFXAutoConfiguration(
            ApplicationContext context,
            List<SpringFXBuilder<?>> builders) {
        this.context = context;
        this.builders = builders;
    }

    @Bean
    public Map<Class<?>, SpringFXBuilder<?>> getSpringFXBuilders() {
        return builders.stream().collect(Collectors.toMap(
                SpringFXBuilder::getType,
                Function.identity()));
    }

    @Bean
    @ConditionalOnMissingBean(BuilderFactory.class)
    public BuilderFactory getBuilderFactory() {
        return new SpringFXBuilderFactory(context, getSpringFXBuilders());
    }

    @Bean
    @ConditionalOnMissingBean(SpringFXLoader.class)
    public SpringFXLoader getSpringFXLoader() {
        return new SpringFXLoader(context, getBuilderFactory());
    }
}
