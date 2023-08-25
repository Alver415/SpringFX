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
    private final Map<Class<?>, SpringFXBuilder<?>> builderMap;

    public SpringFXBuilderFactory(
            ApplicationContext context,
            Map<Class<?>, SpringFXBuilder<?>> builderMap) {
        this.context = context;
        this.builderMap = builderMap;
    }

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        if (builderMap.containsKey(type)) {
            return builderMap.get(type);
        }
        if (AnnotationUtils.findAnnotation(type, FXMLAutoLoad.class) != null) {
            return new SpringProxyBuilder<>(context, type);
        }
        return DEFAULT_BUILDER_FACTORY.getBuilder(type);
    }
}