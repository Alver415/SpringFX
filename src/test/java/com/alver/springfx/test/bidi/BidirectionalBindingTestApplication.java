package com.alver.springfx.test.bidi;

import com.alver.springfx.SpringFX;
import com.alver.springfx.SpringFXApplication;
import com.alver.springfx.model.FXMLControllerAndView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class BidirectionalBindingTestApplication extends SpringFXApplication {

	public static class Launcher {
		public static void main(String... args) {
			Application.launch(BidirectionalBindingTestApplication.class);
		}
	}

	@SpringBootApplication
	public static class SpringApplication {}

	@Override
	public ApplicationContext initApplicationContext() {
		return new SpringApplicationBuilder(SpringApplication.class).headless(false).run();
	}

	@Override
	public void start(Stage primaryStage) {
		ApplicationContext applicationContext = getApplicationContext();
		SpringFX springFX = applicationContext.getBean(SpringFX.class);
		FXMLControllerAndView<BidirectionalBindingTestController, Scene> load = springFX.load(
				BidirectionalBindingTestController.class);
		primaryStage.setTitle(getClass().getSimpleName());
		primaryStage.show();
		primaryStage.setScene(load.view());
	}
}
