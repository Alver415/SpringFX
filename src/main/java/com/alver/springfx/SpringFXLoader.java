package com.alver.springfx;

import com.alver.springfx.annotations.FXMLAutoLoad;
import com.alver.springfx.model.FXMLControllerAndView;
import com.alver.springfx.util.ControllerFactory;
import javafx.fxml.FXMLLoader;
import javafx.util.BuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class SpringFXLoader {

    private static final Logger log = LoggerFactory.getLogger(SpringFXLoader.class);

    protected ApplicationContext context;
    protected BuilderFactory builderFactory;

    @Autowired
    public SpringFXLoader(ApplicationContext context, BuilderFactory builderFactory) {
        this.context = context;
        this.builderFactory = builderFactory;
    }

    public <Controller> Controller loadController(Class<Controller> clazz) {
        return load(clazz).controller();
    }
    public Object loadView(Class<?> clazz) {
        return load(clazz).view();
    }
    public <Controller, View> FXMLControllerAndView<Controller, View> load(Class<Controller> clazz) {
        return load(clazz, null);
    }

    public <Controller> Controller loadController(Controller controller) {
        return load(controller).controller();
    }
    public Object loadView(Object controller) {
        return load(controller).view();
    }
    @SuppressWarnings("unchecked")
    public <Controller, View> FXMLControllerAndView<Controller, View> load(Controller controller) {
        Class<Controller> clazz = (Class<Controller>) controller.getClass();
        return load(clazz, controller);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected <Controller, View> FXMLControllerAndView<Controller, View> load(Class<Controller> clazz, Controller controller) {
        Objects.requireNonNull(AnnotationUtils.findAnnotation(clazz, FXMLAutoLoad.class),
                "%s is not annotated with @FXMLLoaded.".formatted(clazz.getSimpleName()));

        URL location = getLocation(clazz);
        ResourceBundle resources = getResources(clazz);
        ClassLoader classLoader = clazz.getClassLoader();
        ControllerFactory controllerFactory = new SpringFXControllerFactory(context, controller);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setResources(resources);
        fxmlLoader.setClassLoader(classLoader);
        fxmlLoader.setRoot(controller);
        fxmlLoader.setControllerFactory(controllerFactory);
        fxmlLoader.setBuilderFactory(builderFactory);

        return load(fxmlLoader);
    }

    private static <Controller, View> FXMLControllerAndView<Controller, View> load(FXMLLoader fxmlLoader) {
        try {
            View view = fxmlLoader.load();
            Controller controller = fxmlLoader.getController();
            return new FXMLControllerAndView<>(controller, view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getLocation(Class<?> clazz) {
        FXMLAutoLoad annotation = Objects.requireNonNull(
                AnnotationUtils.findAnnotation(clazz, FXMLAutoLoad.class));
        String location = annotation.location().isBlank() ?
                clazz.getSimpleName() + ".fxml" :
                annotation.location();
        return clazz.getResource(location);
    }

    private ResourceBundle getResources(Class<?> clazz) {
        FXMLAutoLoad annotation = Objects.requireNonNull(
                AnnotationUtils.findAnnotation(clazz, FXMLAutoLoad.class));
        String name = annotation.resourceBundle().isBlank() ?
                clazz.getName() :
                annotation.resourceBundle();
        try {
            return ResourceBundle.getBundle(name);
        } catch (MissingResourceException e) {
            log.trace("Failed to find Resource Bundle: " + name, e);
            return null;
        }
    }
}