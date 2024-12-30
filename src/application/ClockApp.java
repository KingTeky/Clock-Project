package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockApp extends Application {

    private static final String NAME = "Elieser C's Clock App"; // Text to display on clock face
    private volatile boolean running = true;

    @Override
    public void start(Stage primaryStage) {
        Text clockText = new Text();
        clockText.setFont(Font.font("Verdana", 50));
        clockText.setFill(Color.WHITE);

        Text nameText = new Text(NAME);
        nameText.setFont(Font.font("Verdana", 20));
        nameText.setFill(Color.WHITE);

        StackPane root = new StackPane();
        root.getChildren().addAll(clockText, nameText);
        StackPane.setAlignment(nameText, javafx.geometry.Pos.BOTTOM_CENTER);

        Scene scene = new Scene(root, 400, 200);
        scene.setFill(Color.BLACK);

        // Thread for updating the time
        Thread updateTimeThread = new Thread(new UpdateTimeTask(clockText));
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.start();

        primaryStage.setTitle("Programming 2 CS1103 Assignment: Clock Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Stop the thread when the application is closed
        primaryStage.setOnCloseRequest(event -> running = false);
    }

    private class UpdateTimeTask implements Runnable {

        private final Text clockText;

        public UpdateTimeTask(Text clockText) {
            this.clockText = clockText;
        }

        @Override
        public void run() {
            while (running) {
                Platform.runLater(() -> {
                    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a dd-MM-yyyy");
                    Date date = new Date();
                    clockText.setText(formatter.format(date));
                });
                try {
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
