package client.model;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

public class GraphicPackage {
    private Image mainImage;
    private ArrayList<Image> images;
    private ArrayList<File> videos;
    private ArrayList<File> sounds;

    public GraphicPackage() {
    }

    public Image getMainImage() {
        return mainImage;
    }

    public void setMainImage(Image mainImage) {
        this.mainImage = mainImage;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void addImageView(Image image) {
        this.images.add(image);
    }

    public ArrayList<File> getVideos() {
        return videos;
    }

    public void addVideo(File video) {
        this.videos.add(video);
    }

    public ArrayList<File> getSounds() {
        return sounds;
    }

    public void addSound(File sound) {
        this.sounds.add(sound);
    }
}
