package com.alver.springfx.annotations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Prototype
@FXMLAutoLoad
public @interface FXMLPrototype {

    @AliasFor(annotation = Component.class)
    String value() default "";

    @AliasFor(annotation = FXMLAutoLoad.class)
    String location() default "";

    @AliasFor(annotation = FXMLAutoLoad.class)
    String resourceBundle() default "";

}