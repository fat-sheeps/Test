package com.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class JavaFXClockExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // 创建用于显示时间的标签
        Label timeLabel = new Label();
        timeLabel.setStyle("-fx-font-size: 30px;");

        // 创建垂直布局容器，将标签放入其中
        VBox vbox = new VBox(timeLabel);
        vbox.setAlignment(Pos.CENTER);

        // 创建时间轴，用于定时更新时间显示
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    // 获取当前本地时间并更新标签显示内容 年月日时分秒
                    java.time.LocalDate currentDate = java.time.LocalDate.now();
                    java.time.LocalTime currentTime = java.time.LocalTime.now();
                    timeLabel.setText("当前时间：\n" + currentDate + String.format(" %02d:%02d:%02d", currentTime.getHour(), currentTime.getMinute(),
                            currentTime.getSecond()));
                    //字体颜色随机 大小
                    String color = String.format("#%06X", random.nextInt(0xffffff + 1));
                    String bgColor = String.format("#%06X", random.nextInt(0xffffff + 1));
                    timeLabel.setStyle("-fx-text-fill: " + color +";-fx-font-size: 45px;" + "-fx-background-color: " + bgColor);

                    vbox.setStyle("-fx-background-color: " + bgColor);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);



        // 创建场景并设置到舞台
        Scene scene = new Scene(vbox, 500, 300);
        primaryStage.setTitle("JavaFX Clock");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 启动时间轴动画，开始更新时间显示
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
        /*ThreadLocalRandom random = ThreadLocalRandom.current();
        //颜色随机生成
        for (int i = 0; i < 100; i++) {
            String color = String.format("#%06X", random.nextInt(0xffffff + 1));
            System.out.println(color);
        }*/
    }
}
