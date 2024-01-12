package com.example.stickhero;

public class Pillar {

    double width, position;

    public Pillar(double width, double position) {
        this.width = width;
        this.position = position;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }
}
