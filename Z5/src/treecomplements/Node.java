package treecomplements;

import java.awt.*;

/**
 * Created by qwert on 07/01/2017.
 */
public class Node extends BDTreeElement{
    public BDTreeElement left;
    public BDTreeElement right;
    public int splitPosition;
    public Point lowerLeftPoint = new Point();
    public int width, height;
    public boolean splitX;
}