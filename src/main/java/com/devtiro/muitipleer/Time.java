package com.devtiro.muitipleer;

import javafx.util.Duration;

public class Time {
    public static String formatTime(Duration current, Duration total) {
        int currSeconds = (int) current.toSeconds();
        int totalSeconds = (int) total.toSeconds();

        int currMinutes = currSeconds / 60;
        int totalMinutes = totalSeconds / 60;

        return String.format("%02d:%02d/%02d:%02d",
                currMinutes, currSeconds % 60,
                totalMinutes, totalSeconds % 60);
    }
}
