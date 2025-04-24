package com.devtiro.muitipleer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.devtiro.muitipleer.Time.formatTime;

public class HelloApplication extends Application {
    ArrayList<File> songs= new ArrayList<>();
    ArrayList<String > namesOfSongs= new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private boolean playing = false;
    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();
        Button openDir = new Button("Open Directory");
        ListView<String> playList = new ListView<>();
        Label currentTrack = new Label("Текущий трек");
        Label nameTrack= new Label();
        Slider timeTrack = new Slider();
        timeTrack.setMin(0);
        timeTrack.setValue(0);
        Label timeTrackLabel = new Label("00:00/00:00");
        HBox buttonContrainer= new HBox();

        Button prev= new Button("Prev");
        Button play= new Button("Play");
        Button pause= new Button("Pause");
        Button stop= new Button("Stop");
        Button next= new Button("Next");
        Button reFresh= new Button("Re-Fresh");




//    ////        Перемотка
            timeTrack.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
                if (!isChanging) {
                    mediaPlayer.seek(Duration.seconds(timeTrack.getValue()));
                }
            });



        openDir.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            playList.getItems().clear();
            if (selectedDirectory != null) {
                File[] audioFiles = selectedDirectory.listFiles(file ->
                        file.getName().endsWith(".mp3") || file.getName().endsWith(".wav"));
                for(File file:audioFiles) {
                    playList.getItems().add(file.getName());
                    songs.add(file);
                }
                namesOfSongs.addAll(playList.getItems());
            }
        });


        play.setOnAction(e -> {
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }
            int index= playList.getSelectionModel().getSelectedIndex();
            Media song= new Media(songs.get(index).toURI().toString());
            currentTrack.setText(namesOfSongs.get(index));
            mediaPlayer = new MediaPlayer(song);


            mediaPlayer.setOnReady(() -> {
                Duration total = mediaPlayer.getTotalDuration();
                timeTrack.setMax(total.toSeconds());
                timeTrackLabel.setText(formatTime(Duration.ZERO, total));
            });

            //         Автоматическое обновление слайдера по времени
            mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                timeTrack.setValue(newTime.toSeconds());
                timeTrackLabel.setText(formatTime(newTime, mediaPlayer.getTotalDuration()));
            });
            mediaPlayer.play();
            playing = true;

        });


        pause.setOnAction(e -> {
            if(mediaPlayer != null) {
               MediaPlayer.Status status = mediaPlayer.getStatus();
               if(status == MediaPlayer.Status.PLAYING) {
                   mediaPlayer.pause();
                   playing = false;
               }else if(status == MediaPlayer.Status.PAUSED) {
                   mediaPlayer.play();
                   playing = true;
               }
            }
        });


        stop.setOnAction(e -> {
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                playing = false;
            }
        });


        prev.setOnAction(e -> {
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
                int preIndex= playList.getSelectionModel().getSelectedIndex()-1;
                if(preIndex >=0) {
                    playList.getSelectionModel().select(preIndex);
                    Media preSong= new Media(songs.get(preIndex).toURI().toString());
                    mediaPlayer = new MediaPlayer(preSong);
                    mediaPlayer.play();

                    mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                        timeTrack.setValue(newTime.toSeconds());
                        timeTrackLabel.setText(formatTime(newTime, mediaPlayer.getTotalDuration()));
                    });

                    playing = true;
                }
            }else{
                System.out.println("Nothing to play");
            }
        });


        next.setOnAction(e -> {
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
                int nextIndex= playList.getSelectionModel().getSelectedIndex()+1;
                if(nextIndex < songs.size()) {
                    playList.getSelectionModel().select(nextIndex);
                    Media nextSong= new Media(songs.get(nextIndex).toURI().toString());
                    mediaPlayer = new MediaPlayer(nextSong);
                    mediaPlayer.play();

                    mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                        timeTrack.setValue(newTime.toSeconds());
                        timeTrackLabel.setText(formatTime(newTime, mediaPlayer.getTotalDuration()));
                    });

                    playing = true;
                    mediaPlayer.setOnEndOfMedia(new Runnable() {
                        @Override
                        public void run() {
                            next.fire();
                        }
                    });
                } else {
                    System.out.println("end");
                }
            }
        });





        buttonContrainer.getChildren().addAll(openDir,play,pause,stop,next,prev,reFresh);
        root.getChildren().addAll(openDir, playList,buttonContrainer,nameTrack,currentTrack,timeTrackLabel,timeTrack);

        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}