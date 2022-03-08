package com.shrinkcom.alsaadceramicapp.utils;

import java.io.Serializable;

public class FloorModel implements Serializable {
    double width;
    double height;

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "FloorModel{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
