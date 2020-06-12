package com.example.jobportalapp.Model_helper_classes;

public class AttractionsHelper {

    int attractionsImage;
    String attractionsName;
    String attractionsDescription;

    public int getAttractionsImage() {
        return attractionsImage;
    }

    public void setAttractionsImage(int attractionsImage) {
        this.attractionsImage = attractionsImage;
    }

    public String getAttractionsName() {
        return attractionsName;
    }

    public void setAttractionsName(String attractionsName) {
        this.attractionsName = attractionsName;
    }

    public String getAttractionsDescription() {
        return attractionsDescription;
    }

    public void setAttractionsDescription(String attractionsDescription) {
        this.attractionsDescription = attractionsDescription;
    }

    public AttractionsHelper() {
    }

    public AttractionsHelper(int attractionsImage, String attractionsName, String attractionsDescription) {
        this.attractionsImage = attractionsImage;
        this.attractionsName = attractionsName;
        this.attractionsDescription = attractionsDescription;
    }
}
