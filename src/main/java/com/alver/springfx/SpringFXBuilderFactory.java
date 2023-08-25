package com.alver.springfx;

import com.alver.springfx.annotations.FXMLAutoLoad;
import com.alver.springfx.annotations.FXMLComponent;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SpringFXBuilderFactory implements BuilderFactory {

    private static final JavaFXBuilderFactory DEFAULT_BUILDER_FACTORY = new JavaFXBuilderFactory();

    private final ApplicationContext context;
    private final List<SpringFXBuilder<?>> builders;

    @Bean
    private Map<Class<?>, SpringFXBuilder<?>> getSpringFXBuilders() {
        return builders.stream().collect(Collectors.toMap(
                SpringFXBuilder::getType,
                Function.identity()));
    }

    public SpringFXBuilderFactory(
            ApplicationContext context,
            List<SpringFXBuilder<?>> springFXBuilders) {
        this.context = context;
        this.builders = springFXBuilders;
    }

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        Map<Class<?>, SpringFXBuilder<?>> map = getSpringFXBuilders();
        if (map.containsKey(type)) {
            return map.get(type);
        }
        if (AnnotationUtils.findAnnotation(type, FXMLAutoLoad.class) != null) {
            return new SpringProxyBuilder<>(context, type);
        }
        return DEFAULT_BUILDER_FACTORY.getBuilder(type);
    }
}