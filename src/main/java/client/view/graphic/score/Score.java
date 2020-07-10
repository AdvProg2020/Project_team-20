package client.view.graphic.score;

import javafx.scene.image.Image;

import java.io.File;

public class Score {
    private double score;

    public Score(double score) {
        this.score = score;
    }

    public Image getScoreImg() {
        long i = Math.round(score);
        try {
            return new Image(new File("src/main/java/client.view/graphic/score/Score" + i + ".png").toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
