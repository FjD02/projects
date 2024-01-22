/**
 * GameOver Class
 *
 * This class represents the screen displayed when the game is over in the Brick Breaker game.
 * It extends the BaseActivity class and includes functionality for restarting the game and exiting.
 *
 * @author [Fco. Javier]
 * @version 1.0
 * @since [01-20-2024]
 */

package ies.davinci.brickbreaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;

public class GameOver extends BaseActivity {

    /**
     * onCreate Method
     *
     * Called when the activity is first created. Initializes the game over layout and starts
     * playing the game over theme music.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        // Start playing the game over theme music.
        startThemeMusic(R.raw.game_over_theme);
    }

    /**
     * onPause Method
     *
     * Called when the activity is paused. Stops the game over theme music.
     */
    @Override
    protected void onPause() {
        super.onPause();
        stopThemeMusic();
    }

    /**
     * onDestroy Method
     *
     * Called when the activity is destroyed. Finishes the activity.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    /**
     * restartGame Method
     *
     * Restarts the game by stopping the game over theme music, creating a new intent to start
     * the main menu activity, and finishing the current activity.
     *
     * @param view The view that triggered the method.
     */
    public void restartGame(View view) {
        buttonSound(R.raw.button);
        stopThemeMusic();
        Intent intent = new Intent(GameOver.this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    /**
     * exitGameGameOver Method
     *
     * Exits the game over screen by stopping the game over theme music and finishing the activity.
     *
     * @param view The view that triggered the method.
     */
    public void exitGameGameOver(View view) {
        buttonSound(R.raw.button);
        stopThemeMusic();
        finish();
    }
}
