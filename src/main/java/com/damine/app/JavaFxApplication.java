package com.damine.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URL;

public class JavaFxApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    private Object createControllerForType(Class<?> type) {
        return this.applicationContext.getBean(type);
    }

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(SpringFxApplication.class)
                .run(args);
    }

    public static void moveStage(Stage primaryStage, Scene scene) {
        final double[] xOffset = {0};
        final double[] yOffset = {0};
        scene.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
            primaryStage.setOpacity(0.2f);
        });
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset[0]);
            primaryStage.setY(event.getScreenY() - yOffset[0]);
            primaryStage.setOpacity(0.2f);
        });
        scene.setOnDragDone(event -> primaryStage.setOpacity(1.0f));
        scene.setOnMouseReleased(event -> primaryStage.setOpacity(1.0f));
    }

    public void setStage(Stage primaryStage, URL url, String name) {
        FXMLLoader fxmlLoader;
        try {
            fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(this::createControllerForType);
            Parent root = fxmlLoader.load();
            primaryStage.setTitle(name);
            Scene scene = new Scene(root);

            // primaryStage.initStyle(StageStyle.TRANSPARENT);
            // scene.setFill(Color.TRANSPARENT);

            moveStage(primaryStage, scene);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        URL url = getClass().getResource("/fxml/home.fxml");
        setStage(primaryStage, url, "Main");
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
