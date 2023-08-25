package com.alver.springfx.util;

import javafx.util.Callback;

public interface ControllerFactory<T> extends Callback<Class<T>, T> {

}
