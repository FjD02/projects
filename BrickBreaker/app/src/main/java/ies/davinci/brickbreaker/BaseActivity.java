/**
 * BaseActivity Class
 *
 * This class extends AppCompatActivity and serves as a base class for other activities in the
 * Brick Breaker Android game. It includes methods to manage background theme music,
 * stop theme music, and play button sound effects.
 *
 * @author [Fco. Javier]
 * @version 1.0
 * @since [01-15-2024]
 */

package ies.davinci.brickbreaker;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    // Media players for background theme music and button sound
    protected MediaPlayer mediaPlayer, mediaSound;

    // Flag to track whether the theme music is currently playing
    private boolean themePlaying = false;

    // Delay time in milliseconds before starting theme music
    private final int DELAY_TIME_MILLIS = 1000;

    /**
     * onCreate Method
     *
     * Overrides the onCreate method of AppCompatActivity.
     * No additional functionality is added in this class, so it calls the superclass method.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * startThemeMusic Method
     *
     * Method to start the background theme music with a delay.
     *
     * @param rawResourceId The resource ID of the music to be played.
     */
    protected void startThemeMusic(int rawResourceId) {
        // Check if the theme music is not already playing
        if (!themePlaying) {
            // Use Handler to introduce a delay before starting the music
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Start the music and update the flag
                    themePlaying = startMusic(rawResourceId);
                }
            }, DELAY_TIME_MILLIS);
        }
    }

    /**
     * startMusic Method
     *
     * Helper method to start the background theme music.
     *
     * @param rawResourceId The resource ID of the music to be played.
     * @return True if the music is successfully started.
     */
    private boolean startMusic(int rawResourceId) {
        // Stop and release the existing media player, if any
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        // Set volume, create, configure, and start the new media player
        float volume = 0.6f;
        mediaPlayer = MediaPlayer.create(this, rawResourceId);
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        return true;
    }

    /**
     * stopThemeMusic Method
     *
     * Method to stop the background theme music.
     */
    protected void stopThemeMusic() {
        // Check if the media player is not null
        if (mediaPlayer != null) {
            // Stop and release the media player, and update the flag
            mediaPlayer.stop();
            mediaPlayer.release();
            themePlaying = false;
        }
    }

    /**
     * buttonSound Method
     *
     * Method to play a sound effect when a button is pressed.
     *
     * @param rawResourceId The resource ID of the sound effect to be played.
     */
    protected void buttonSound(int rawResourceId) {
        // Set volume, create, configure, and start the button sound media player
        float volumeB = 1.0f;
        mediaSound = MediaPlayer.create(this, rawResourceId);
        mediaSound.setVolume(volumeB, volumeB);
        mediaSound.start();
    }
}
