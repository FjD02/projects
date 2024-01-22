/**
 * MainMenu Class
 *
 * This class represents the main menu of the Brick Breaker game. It extends the BaseActivity class
 * and includes functionality for starting the game, exiting the game, and displaying game information.
 *
 * @author [Fco. Javier]
 * @version 1.0
 * @since [01-19-2024]
 */

package ies.davinci.brickbreaker;

import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends BaseActivity {

    /**
     * onCreate Method
     *
     * Called when the activity is first created. Initializes the main menu layout and starts
     * playing the main theme music.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // Start playing the main theme music.
        startThemeMusic(R.raw.main_theme);
    }

    /**
     * onPause Method
     *
     * Called when the activity is paused. Stops the main theme music.
     */
    @Override
    protected void onPause() {
        super.onPause();
        stopThemeMusic();
    }

    /**
     * onDestroy Method
     *
     * Called when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * startGame Method
     *
     * Starts the game by creating a new Game object and setting it as the content view.
     *
     * @param view The view that triggered the method.
     */
    public void startGame(View view) {
        buttonSound(R.raw.button);
        Game game = new Game(this);
        setContentView(game);
    }

    /**
     * exitGameMainMenu Method
     *
     * Exits the game by stopping the main theme music and finishing the activity.
     *
     * @param view The view that triggered the method.
     */
    public void exitGameMainMenu(View view) {
        buttonSound(R.raw.button);
        stopThemeMusic();
        finish();
    }

    /**
     * showInfo Method
     *
     * Displays an AlertDialog with information about the game rules and objectives.
     *
     * @param view The view that triggered the method.
     */
    public void showInfo(View view) {
        buttonSound(R.raw.button);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Info");

        builder.setMessage("The rules for playing Brick Breaker are very simple." +
                "\nYou use your finger to move the paddle and hit the ball to break all the bricks. To win, you must break all the bricks." +
                "\nYou start the game with 3 health points; you can see the health bar at the top-right of the screen. Each time you miss hitting the ball with the paddle, you lose one life. " +
                "The game is over when you lose all the health points.");

        builder.setPositiveButton("Accept", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
