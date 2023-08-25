package com.alver.springfx.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@FXMLAutoLoad
public @interface FXMLComponent {

    @AliasFor(annotation = Component.class)
    String value() default "";

    @AliasFor(annotation = FXMLAutoLoad.class)
    String location() default "";

    @AliasFor(annotation = FXMLAutoLoad.class)
    String resourceBundle() default "";

}