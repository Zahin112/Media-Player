package sample;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NewDisplay {

    public static void display() {
        final String[] s1 = {""};
        final String[] s2 = {""};
        String out;
        Stage window = new Stage();

        //StackPane layout = new StackPane();

        VBox root = new VBox();
        TextField dir = new TextField();
        dir.setPromptText("PATH :");
        TextField fileName = new TextField();
        fileName.setPromptText("File Name");
        Button button = new Button("Browse");
        //button.setAlignment(Pos.CENTER_RIGHT);
        button.setOnAction(e ->{
             DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = directoryChooser.showDialog(null);
            dir.setText(file.getAbsolutePath());
        });
        root.setPrefSize(400, 600);

        TextField field = new TextField();
        field.setPromptText("Download link");
        dir.setOnAction(event -> {
            s1[0] = dir.getText();
        });
        fileName.setOnAction(event -> {
            s2[0] = fileName.getText();
        });
        out = s1[0]+"//"+s2[0]+".mp4";
        //System.out.println(out);
        //ProgressBar progressBar = new ProgressBar();

        field.setOnAction(event -> {
            Task<Void> task = new VideoDownloader.DownloadTask(field.getText());
            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(400);
            progressBar.progressProperty().bind(task.progressProperty());
            root.getChildren().add(progressBar);
            field.clear();

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        });


        root.getChildren().addAll(dir,button,fileName,field);


        Scene scene = new Scene(root, 400, 300);

        window.setScene(scene);
        window.getIcons().add(new Image("file:download.png"));

        window.show();


    }
}