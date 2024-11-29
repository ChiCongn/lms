package edu.lms.controllers.common;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    private static boolean isMuted = false;

    private SoundManager() {
    }

    // Play a sound file
    public static void playMusic(String soundFilePath) {
        if (isMuted) {
            System.out.println("sound is muted");
            return;
        }

        try {
            Media media = new Media("file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/sounds/" + soundFilePath);
            MediaPlayer player = new MediaPlayer(media);
            System.out.println("play sound");
            player.play();
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void playSound(String soundFilePath) {
        if (isMuted) {
            return;
        }

        try {
            AudioClip audioClip = new AudioClip("file:/E:/AllSemesters/ThirdSemester/OOP/lms/target/classes/edu/lms/sounds/" + soundFilePath);
            audioClip.play();
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    // Toggle mute
    public static void toggleMute() {
        isMuted = !isMuted;
        System.out.println(isMuted ? "Sound muted" : "Sound unmuted");
    }

    // Check if muted
    public static boolean isMuted() {
        return isMuted;
    }
}

