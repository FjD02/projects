/**
 * Velocity Class
 *
 * This class represents the velocity (speed) of an object in a 2D space, with separate
 * components for the x and y axes.
 *
 * @author [Fco. Javier]
 * @version 1.0
 * @since [01-17-2024]
 */

package ies.davinci.brickbreaker;

public class Velocity {

    // Components of velocity along the x and y axes
    private int x, y;

    /**
     * Constructor
     *
     * Initializes the velocity with given x and y components.
     *
     * @param x The x component of the velocity.
     * @param y The y component of the velocity.
     */
    public Velocity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getX Method
     *
     * Retrieves the x component of the velocity.
     *
     * @return The x component of the velocity.
     */
    public int getX() {
        return x;
    }

    /**
     * setX Method
     *
     * Sets the x component of the velocity.
     *
     * @param x The new value for the x component of the velocity.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * getY Method
     *
     * Retrieves the y component of the velocity.
     *
     * @return The y component of the velocity.
     */
    public int getY() {
        return y;
    }

    /**
     * setY Method
     *
     * Sets the y component of the velocity.
     *
     * @param y The new value for the y component of the velocity.
     */
    public void setY(int y) {
        this.y = y;
    }
}
