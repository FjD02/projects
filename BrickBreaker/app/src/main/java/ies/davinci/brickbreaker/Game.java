/**
 * Game Class
 *
 * This class represents the main game logic for the Brick Breaker Android game. It handles the
 * movement of the ball, collisions, scoring, and manages the game elements such as the paddle,
 * bricks, and health. Additionally, it includes functionality for touch input and game over handling.
 *
 * @author [Fco. Javier]
 * @version 1.0
 * @since [01-17-2024]
 */

package ies.davinci.brickbreaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

import java.util.Random;

public class Game extends View {

    Context context;
    float ballX, ballY;
    Velocity velocity = new Velocity(25, 30);
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    Paint brickPaint = new Paint();
    float paddleX, paddleY, oldX, oldPaddleX;
    int points = 0, life = 3, numBricks = 0, brokenBricks = 0;
    int screenWidth, screenHeight, ballWidth, ballHeight;
    Bitmap ball, paddle;
    MediaPlayer mpHit,  mpBreak;
    Random random;
    Brick[] bricks = new Brick[30];
    boolean gameOver = false;

    /**
     * Constructor
     *
     * Initializes the game elements, handlers, and media players.
     *
     * @param context The context of the game.
     */
    public Game(Context context) {
        super(context);
        this.context = context;
        ball = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        // Media.
        mpHit = MediaPlayer.create(context, R.raw.beep);
        mpBreak = MediaPlayer.create(context, R.raw.block_hit);

        // Points.
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(120);
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Health Bar.
        healthPaint.setColor(Color.GREEN);

        // Bricks.
        brickPaint.setColor(Color.BLUE);

        // Calculate Display's Width and Height.
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // Ball and Paddle Positions.
        random = new Random();
        ballX = random.nextInt(screenWidth - 50);
        ballY = (float) screenHeight / 3;
        paddleX = ((float) screenWidth / 2) - ((float) paddle.getWidth() / 2);
        paddleY = (float) (screenHeight * 4) / 5;

        // Ball and Paddle Size.
        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();

        // Call the Method to Create the Bricks.
        createBricks();
    }

    /**
     * createBricks Method
     *
     * Creates an array of bricks with specified dimensions.
     */
    private void createBricks() {
        int brickWidth = screenWidth / 8;
        int brickHeight = screenHeight / 16;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                bricks[numBricks] = new Brick(j, i, brickWidth, brickHeight);
                numBricks++;
            }
        }
    }

    // Method to draw the game elements on the canvas.
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        // Update ball position.
        ballX += velocity.getX();
        ballY += velocity.getY();

        // // Ball collisions with screen boundaries.
        if((ballX >= screenWidth - ball.getWidth()) || ballX <= 0) {
            velocity.setX(velocity.getX() * -1);
        }

        if (ballY <=0) {
            velocity.setY(velocity.getY() * -1);
        }

        // Reset ball position and reduce life if it goes below the paddle.
        if (ballY > paddleY + paddle.getHeight()) {
            ballX = 1 + random.nextInt(screenWidth - ball.getWidth() - 1);
            ballY = (float) screenHeight / 3;
            velocity.setX(xVelocity());
            velocity.setY(32);
            // Game Over.
            life--;

            // Game over condition
            if (life == 0) {
                gameOver = true;
                launchGameOver();
            }
        }

            // Ball and Paddle Collisions.
            if ((ballX + ball.getWidth() >= paddleX) && (ballX <= paddleX + paddle.getWidth())
            && (ballY + ball.getHeight() >= paddleY) && (ballY + ball.getHeight() <= paddleY + paddle.getHeight())) {
                if (mpHit != null) {
                    mpHit.start();
                }
                // Increase Difficulty.
                velocity.setX(velocity.getX() + 2);
                velocity.setY((velocity.getY() + 2) * -1);
            }

            // Draw Ball, Paddle and Bricks on Canvas.
            canvas.drawBitmap(ball, ballX, ballY, null);
            canvas.drawBitmap(paddle, paddleX, paddleY, null);
            for (int i = 0; i < numBricks ; i++) {
                if (bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].column * bricks[i].width + 1,
                            bricks[i].row * bricks[i].height + 1,
                            bricks[i].column * bricks[i].width + bricks[i].width - 1,
                            bricks[i].row * bricks[i].height + bricks[i].height - 1, brickPaint);
                }
            }

            // Draw Points.
            canvas.drawText("" + points, 20, screenHeight, textPaint);

            // Draw and Change Health Bar.
            if (life == 2) {
                healthPaint.setColor(Color.YELLOW);
            } else if (life == 1) {
                healthPaint.setColor(Color.RED);
            }
            canvas.drawRect(screenWidth - 200, 30, screenWidth - 200 + 60 * life, 80, healthPaint);

            // Collisions.
            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getVisibility()) {
                    if (ballX + ballWidth >= bricks[i].column * bricks[i].width
                    && ballX <= bricks[i].column * bricks[i].width + bricks[i].width
                    && ballY <= bricks[i].row * bricks[i].height + bricks[i].height
                    && ballY >= bricks[i].row * bricks[i].height) {
                        if (mpBreak != null) {
                            mpBreak.start();
                        }
                        // Increase Velocity.
                        velocity.setY((velocity.getY() + 1) * -1);

                        // "Break Brick"
                        bricks[i].setInvisible();

                        // Sum Points and Broken Bricks.
                        points += 10;
                        brokenBricks++;

                        if (brokenBricks == 24) {
                            launchGameOver();
                        }
                    }
                }
            }
            if (brokenBricks == numBricks) {
                gameOver = true;
            }
            if (!gameOver) {
                handler.postDelayed(runnable,UPDATE_MILLIS);
            }
        }

    /**
     * onTouchEvent Method
     *
     * Handles touch input for moving the paddle.
     *
     * @param event The MotionEvent representing the touch event.
     * @return True if the touch event is handled.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        // Check if touch is below the paddle.
        if (touchY >= paddleY) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                // Save initial touch position.
                oldX = event.getX();
                oldPaddleX = paddleX;
            }
            if (action == MotionEvent.ACTION_MOVE) {
                // Calculate paddle movement based on touch displacement.
                float shift = oldX - touchX;
                float newPaddleX = oldPaddleX - shift;

                // Limit paddle movement within screen boundaries.
                if (newPaddleX <= 0) {
                    paddleX = 0;
                } else if (newPaddleX >= screenWidth - paddle.getWidth()) {
                    paddleX = screenWidth - paddle.getWidth();
                } else {
                    paddleX = newPaddleX;
                }
            }
        }
        return true;
    }

    /**
     * xVelocity Method
     *
     * Generates a random x velocity for the ball.
     *
     * @return The randomly generated x velocity.
     */
    private int xVelocity() {
        int[] values = {-35, -30, -25, 25, 30, 35};
        int index = random.nextInt(6);
        return  values[index];
    }

    /**
     * launchGameOver Method
     *
     * Launches the Game Over activity and finishes the current activity.
     */
    private void launchGameOver() {
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(context, GameOver.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
