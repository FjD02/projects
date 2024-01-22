/**
 * Brick Class
 *
 * This class represents a brick in the Brick Breaker game. Each brick has visibility
 * status and is defined by its position (row and column) as well as dimensions (width and height).
 *
 * @author [Fco. Javier]
 * @version 1.0
 * @since [01-17-2024]
 */

package ies.davinci.brickbreaker;

public class Brick {

    // Visibility status of the brick
    private boolean isVisible;

    // Position and dimensions of the brick
    public int row, column, width, height;

    /**
     * Constructor
     *
     * Initializes a new brick with default visibility set to true.
     *
     * @param row The row index of the brick.
     * @param column The column index of the brick.
     * @param width The width of the brick.
     * @param height The height of the brick.
     */
    public Brick(int row, int column, int width, int height) {
        isVisible = true;

        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
    }

    /**
     * setInvisible Method
     *
     * Sets the visibility of the brick to false.
     */
    public void setInvisible() {
        isVisible = false;
    }

    /**
     * getVisibility Method
     *
     * Retrieves the visibility status of the brick.
     *
     * @return True if the brick is visible, false otherwise.
     */
    public boolean getVisibility() {
        return isVisible;
    }
}
