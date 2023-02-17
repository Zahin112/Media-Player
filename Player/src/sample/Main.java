package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(root,800,600);
            primaryStage.setTitle("Media Player");
            scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                    if(click.getClickCount() == 2){
                        primaryStage.setFullScreen(true);
                    }
                }
            });
            //Image image = new Image("download.png");
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image("file:Icon1.png"));
            primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
