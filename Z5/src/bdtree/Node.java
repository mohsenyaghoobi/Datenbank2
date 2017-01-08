package bdtree;

import java.awt.*;

/**
 * @class Node is a node of BD Tree, it contains no information of any object
 */
public class Node extends BDTreeComponent {
    /**
     * left child if this node
     * */
    public BDTreeComponent left;

    /**
     * right child of this node
     * */
    public BDTreeComponent right;

    /**
     * position where this node will be split
     * */
    public int splitPosition;

    /**
     * A rectangle which is based on a lower left point, width and height.
     * This rectangle contains all the data belong to this node
     * */
    public Point lowerLeftPoint = new Point();
    public int width, height;

    /**
     * this node should be split by X achse or Y achse
     * True if this node is split by X, false by Y
     **/
    public boolean splitX;
}