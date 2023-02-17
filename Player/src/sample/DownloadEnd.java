package sample;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class DownloadEnd {
    public static void downloadComplete(){
        Stage window = new Stage();
        StackPane stackPane = new StackPane();
        Label label = new Label("DOWNLOAD COMPLETE!!!!!!");
        stackPane.setPrefSize(300,200);
        stackPane.getChildren().add(label);
        Scene scene = new Scene(stackPane, 300, 200);
        window.setScene(scene);
        window.getIcons().add(new Image("file:dowCom.jpg"));
        window.show();

    }
    public static void downloadFailed(){
        Stage window = new Stage();
        StackPane stackPane = new StackPane();
        Label label = new Label("DOWNLOAD FAILED!!!!!!");
        stackPane.setPrefSize(300,200);
        stackPane.getChildren().add(label);
        Scene scene = new Scene(stackPane, 300, 200);
        window.setScene(scene);
        window.getIcons().add(new Image("file:dowFail.png"));
        window.show();

    }
}
