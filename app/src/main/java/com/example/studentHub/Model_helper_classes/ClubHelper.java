package com.example.studentHub.Model_helper_classes;

public class ClubHelper {

    int image;
    String clubName;
    String clubDescription;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public ClubHelper() {
    }

    public ClubHelper(int image, String clubName, String clubDescription) {
        this.image = image;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
    }
}