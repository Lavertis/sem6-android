package com.lavertis.project4;

import android.graphics.Color;

public enum DrawingColor {
    RED(Color.rgb(231, 29, 54)),
    GREEN(Color.rgb(14, 173, 105)),
    BLUE(Color.rgb(67, 97, 238)),
    YELLOW(Color.rgb(255, 214, 10));

    public final int value;

    DrawingColor(int value) {
        this.value = value;
    }
}
