package sample;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;


public class VideoDownloader{
        //private static int n;
   /* private Parent createContent(){
        VBox root = new VBox();
        root.setPrefSize(400,600);

        TextField field  = new TextField();
        ProgressBar progressBar = new ProgressBar();
        field.setOnAction(event -> {
            Task<Void> task = new DownloadTask(field.getText());
            field.clear();

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        });

        root.getChildren().addAll(field, progressBar);
        return root;
    }*/
    public static class DownloadTask extends  Task<Void>{
        private String url ;
        private String out;
        public  DownloadTask(String url){
            this.url = url ;

        }
        @Override
        protected Void call() throws Exception{
            String ext = url.substring(url.lastIndexOf("."),url.length());
            URLConnection connection = new URL(url).openConnection();
            long fileLength = connection.getContentLength();

            try(InputStream is = new URL(url).openStream();
                OutputStream os = Files.newOutputStream(Paths.get("olala"+ext));
            ){
                //Files.copy(is, Paths.get("Whatev"+ext));
                long nread = 0L ;
                byte[] buff = new byte[8192];
                int n;
                while((n = is.read(buff))>0){
                    os.write(buff,0,n);
                    nread += n ;
                    updateProgress(nread,fileLength);

                }
            }
            return  null;
        }
        @Override
        protected  void failed(){
            DownloadEnd.downloadFailed();
            System.out.println("Failed");
        }

        @Override
        protected void succeeded(){
            DownloadEnd.downloadComplete();
            System.out.println("Downloaded");

        }


    }


    /*@Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.show();
    }
    /*public static void main(String[] args) {
        launch(args);
    }*/
}
