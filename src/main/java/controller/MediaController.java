package controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MediaController {

    boolean isFirst;
    Media media;
    MediaPlayer mediaPlayer;

    public MediaController() {
        this.isFirst = true;
    }

    public void mainTheme() {
        try {
            String path = "src/main/resources/sound/McCoy-Tyner-Trio-When-Sunny-Gets-Blue_2889354 (2).mp3";
            if (isFirst)
                path = "src/main/resources/sound/startup.m4a";
            isFirst = false;
            play(path);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public void clickOnButton() {
        this.media = new Media(new File("src/main/resources/sound/button.mp3").toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.6);
        mediaPlayer.setAutoPlay(true);
    }

    public void stop() {
        if (mediaPlayer != null)
            mediaPlayer.stop();
    }

    public void registerAndLogin() {
        play("src/main/resources/sound/Sleep Away.mp3");
    }

    public void allProducts() {
        play("src/main/resources/sound/Ludovico-Einaudi-Una-Mattina-128.mp3");
    }

    public void productMenu() {
        play("src/main/resources/sound/81276965.mp3");
    }

    private void play(String path) {
        this.media = new Media(new File(path).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.setVolume(0.6);
        mediaPlayer.setAutoPlay(true);
    }

    public void buyerMenu() {
        play("src/main/resources/sound/Iday Vals (320).mp3");
    }

    public void managerMenu() {
        play("src/main/resources/sound/Lobster Soup.mp3");
    }

    public void sellerMenu() {
        play("src/main/resources/sound/04 Porz Goret.mp3");
    }
}