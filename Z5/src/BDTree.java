import bdtree.BDTreeComponent;
import bdtree.KinderInfo;
import bdtree.Leaf;
import bdtree.Node;

import java.awt.*;
import java.util.ArrayList;

/**
 * Box decomposition Tree
 */
public class BDTree {
    /**
     * root of bd tree
     */
    Node root;

    /**
     * Construction
     * Creates a new tree with root and two leaves at left and right side
     */
    public BDTree(int width, int height) {
        root = new Node();
        root.left = new Leaf();
        root.right = new Leaf();
        root.left.parent = root;
        root.right.parent = root;
        root.lowerLeftPoint = new Point(0, 0);
        root.width = width;
        root.height = height;
        root.splitX = true;
        root.splitPosition = width / 2;
    }

    /**
     * Insert an object to bs tree.
     * 1. Find a leaf where to save the data
     * 2. Add data to the found leaf
     * 3. Split the leaf to 2 parts if needed
     *
     * @param data data to insert ot bd tree
     */
    public void insert(KinderInfo data) {
        Leaf l = findLeaf(data, root, false);
        l.list.add(data);
        splitLeaf(l);
    }

    /**
     * split a leaf if needed
     *
     * @param l
     */
    private void splitLeaf(Leaf l) {
        if (l.list.size() > 1 && l.parent.width > 0 && l.parent.height > 0) {
            Node parent = l.parent;
            Node newNode = new Node();
            if (parent.splitX) {
                newNode.splitX = false;
            } else {
                newNode.splitX = true;
            }

            if (parent.left == l) {
                parent.left = newNode;
                newNode.lowerLeftPoint = parent.lowerLeftPoint;
            } else {
                parent.right = newNode;
                if (parent.splitX) {
                    newNode.lowerLeftPoint = new Point(parent.splitPosition, parent.lowerLeftPoint.y);
                } else {
                    newNode.lowerLeftPoint = new Point(parent.lowerLeftPoint.x, parent.splitPosition);
                }
            }

            if (parent.splitX) {
                newNode.width = parent.width / 2;
                newNode.height = parent.height;
            } else {
                newNode.width = parent.width;
                newNode.height = parent.height / 2;
            }

            if (newNode.splitX) {
                newNode.splitPosition = newNode.lowerLeftPoint.x + newNode.width / 2;
            } else {
                newNode.splitPosition = newNode.lowerLeftPoint.y + newNode.height / 2;
            }

            newNode.left = new Leaf();
            newNode.right = new Leaf();
            newNode.left.parent = newNode;
            newNode.right.parent = newNode;

            newNode.parent = parent;

            for (int i = 0; i < l.list.size(); i++) {
                Leaf leaf = findLeaf((KinderInfo) l.list.get(i), newNode, false);
                leaf.list.add(l.list.get(i));
                splitLeaf(leaf);
            }
        }
    }


    /**
     * Find a leaf where a data should be stored under a node
     *
     * @param data  data to be stored
     * @param node  node to store a data
     * @param debug show info to debug
     * @return a leaf where the data should be stored
     */
    private Leaf findLeaf(KinderInfo data, BDTreeComponent node, boolean debug) {
        if (node instanceof Leaf) {
            return (Leaf) node;
        } else {
            Node n = (Node) node;
            if (data.isLeft(n)) {
                if (debug) {
                    Log("Go Left");
                }
                return findLeaf(data, n.left, debug);
            } else {
                if (debug) {
                    Log("Go Right");
                }
                return findLeaf(data, n.right, debug);
            }
        }
    }

    /**
     * find a data which is stored in the given coordinate
     *
     * @param x
     * @param y
     * @return data
     */
    public KinderInfo find(int x, int y) {
        KinderInfo temp = new KinderInfo("", "", x, y);
        Leaf leaf = findLeaf(temp, root, false);
        for (int i = 0; i < leaf.list.size(); i++) {
            KinderInfo info = (KinderInfo) leaf.list.get(i);
            if (info.x == x && info.y == y) {
                return info;
            }
        }
        return null;
    }

    /**
     * Find all of data in a section of a rectangle
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return a list of found data
     */
    public ArrayList<Object> findWithSection(int x1, int y1, int x2, int y2) {
        int width = x2 - x1;
        int height = y2 - y1;
        return findWithSection(x1, y1, width, height, root);
    }

    /**
     * Help method tpo check if a node intersects with the given rectangle, if true then we will find all of its child,
     * otherwise do nothing
     */
    private ArrayList<Object> findWithSection(int x1, int y1, int width, int height, BDTreeComponent node) {
        if (node instanceof Leaf) {
            return ((Leaf) node).list;
        } else {
            Node n = (Node) node;
            if (n.lowerLeftPoint.x + n.width < x1 || x1 + width < n.lowerLeftPoint.x || n.lowerLeftPoint.y + n.height < y1 || y1 + height < n.lowerLeftPoint.y) {
                return new ArrayList<>();
            } else {
                ArrayList<Object> list1 = findWithSection(x1, y1, width, height, n.left);
                ArrayList<Object> list2 = findWithSection(x1, y1, width, height, n.right);
                list1.addAll(list2);
                return list1;
            }
        }
    }


    private void Log(Object o) {
        System.out.println(o);
    }
}