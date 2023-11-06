package com.example.slidepuzzlegame;

public class MyDatabaseModel {

    private int id;
    private String Name;
    private String Image;

    public MyDatabaseModel(int id, String name, String image) {
        this.id = id;
        this.Name = name;
        this.Image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
