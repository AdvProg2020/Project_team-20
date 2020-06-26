package controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MediaController {

    Media media;
    MediaPlayer mediaPlayer;

    public void mainTheme() {
        try {
            // src/main/resources/sound/startup.m4a  src/main/resources/sound/spring-weather-1.mp3
            String path = "src/main/resources/sound/startup.m4a";
            this.media = new Media(new File(path).toURI().toString());
            this.mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
            mediaPlayer.setVolume(0.6);
            mediaPlayer.setAutoPlay(true);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public void clickOnButton() {

    }

    public void stop() {
        if (mediaPlayer != null)
            mediaPlayer.stop();
    }
}
