package sample;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.SubtitleTrack;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;

public class Controller  {

    private ActionEvent actionEvent;
    private String videoString;
    private MediaPlayer mediaPlayer;
    private File file;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider slider , slider2 ;
    @FXML
    private TextField duration;

    private Duration elapsed;

    private double vol;





    @FXML
    private void OpenFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a video (*mp4)","*mp4");
        fileChooser.getExtensionFilters().add(filter);
        file = fileChooser.showOpenDialog(null);
        videoString = file.toURI().toString();
        if(videoString != null){
            Media media = new Media(videoString);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();

            width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));


            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    slider.setValue(mediaPlayer.getVolume() * 100);
                    slider.valueProperty().addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                            mediaPlayer.setVolume(slider.getValue() / 100);

                        }
                    });


                 /* mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                        @Override
                        public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                            slider2.maxProperty().bind(Bindings.createDoubleBinding(
                                    () -> mediaPlayer.getTotalDuration().toSeconds(),
                                    mediaPlayer.totalDurationProperty()
                            ));
                            if(!slider2.isValueChanging()){
                                newValue= mediaPlayer.getCurrentTime();
                                slider2.setValue(newValue.toSeconds());
                            }

                        }
                    });


/*
                    slider2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            mediaPlayer.seek(Duration.seconds(slider2.getValue()));
                        }
                    });*/
                    InvalidationListener sliderChangeListener = o-> {
                        Duration seekTo = Duration.seconds(slider2.getValue());
                        mediaPlayer.seek(seekTo);


                    };
                    slider2.valueProperty().addListener(sliderChangeListener);
                    mediaPlayer.currentTimeProperty().addListener(l-> {
                        slider2.valueProperty().removeListener(sliderChangeListener);
                        slider2.maxProperty().bind(Bindings.createDoubleBinding(
                                () -> mediaPlayer.getTotalDuration().toSeconds(),
                                mediaPlayer.totalDurationProperty()
                        ));
                        if(!slider2.isValueChanging()){
                            Duration newValue= mediaPlayer.getCurrentTime();
                            slider2.setValue(newValue.toSeconds());
                        }
                        slider2.valueProperty().addListener(sliderChangeListener);

                    });

                    mediaPlayer.play();

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                while(true){
                                    duration.setText(formatTime(mediaPlayer.getCurrentTime(), mediaPlayer.getTotalDuration()));
                                    Thread.sleep(100);
                                }

                            }
                            catch(Exception e){
                                e.getStackTrace();
                            }
                        }
                    });
                    t.setDaemon(true);
                    t.start();





                }

            });

        }

    }

    @FXML
    private void Pause(ActionEvent actionEvent) {
        mediaPlayer.pause();
    }
    @FXML
    private void img(ActionEvent actionEvent) {

    }

    @FXML
    private void PlayPause(ActionEvent actionEvent){
        if(mediaPlayer.onPausedProperty() != null){
            mediaPlayer.play();
        }
        if(mediaPlayer.onPlayingProperty() != null){
            mediaPlayer.pause();
        }

    }


    @FXML
    private void Stop(ActionEvent actionEvent) {
        mediaPlayer.stop();

    }

    @FXML
    private void Forward(ActionEvent actionEvent){
        if(mediaPlayer.getRate() == .75){
            mediaPlayer.setRate(1);
        }
        else
            mediaPlayer.setRate(1.5);

    }

    @FXML
    private void Backward(ActionEvent actionEvent){
        if(mediaPlayer.getRate() == 1.5){
            mediaPlayer.setRate(1);
        }
        else
            mediaPlayer.setRate(.75);
    }


    @FXML
    private void Exit(ActionEvent actionEvent){
        System.exit(0);
    }

    @FXML
    private void Mute(ActionEvent actionEvent){
        boolean mode = mediaPlayer.isMute();


        if(mode == false ){
            vol = slider.getValue();
            mediaPlayer.setMute(true);
            slider.setValue(0);
        }
        if(mode == true){

            mediaPlayer.setMute(false);
            slider.setValue(vol);

        }

    }

    public void Download (ActionEvent actionEvent) {
        // this.actionEvent = actionEvent;

        NewDisplay.display();
    }
    private static String formatTime(Duration elapsed, Duration duraTion){
        int intElapsed = (int)Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedMinutes * 60;


        if (duraTion.greaterThan(Duration.ZERO)) {
            int intDuration = (int)Math.floor(duraTion.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration -durationMinutes * 60;

            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds,durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }


}
