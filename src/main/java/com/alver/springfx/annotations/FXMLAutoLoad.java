package com.alver.springfx.annotations;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLAutoLoad {

    String location() default "";
    String resourceBundle() default "";

}