//package com.devtiro.muitipleer;
//
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.util.Duration;
//
//import static com.devtiro.muitipleer.Time.formatTime;
//
//public class PlayMusic {
//    private Media media;
//    private MediaPlayer mediaPlayer;
//
//    public  static void Play(int index){
//        if (index >= 0 && index < songs.size()) {
//            if (mediaPlayer != null) {
//                mediaPlayer.stop();
//                mediaPlayer.dispose();
//            }
//
//            Media song = new Media(songs.get(index).toURI().toString());
//            mediaPlayer = new MediaPlayer(song);
//
//            mediaPlayer.setOnReady(() -> {
//                Duration total = mediaPlayer.getTotalDuration();
//                timeTrack.setMax(total.toSeconds());
//                timeTrackLabel.setText(formatTime(Duration.ZERO, total));
//            });
//
//            mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
//                timeTrack.setValue(newTime.toSeconds());
//                timeTrackLabel.setText(formatTime(newTime, mediaPlayer.getTotalDuration()));
//            });
//
//            mediaPlayer.play();
//        }
//    }
//}
