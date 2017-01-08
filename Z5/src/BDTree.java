import treecomplements.BDTreeElement;
import treecomplements.KinderInfo;
import treecomplements.Leaf;
import treecomplements.Node;

import java.awt.*;
import java.util.ArrayList;


public class BDTree {
    Node root;

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

    public void insert(KinderInfo geschenk) {
        Leaf l = findLeaf(geschenk, root, false);
        l.list.add(geschenk);
        splitLeaf(l);
    }

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
                Leaf leaf = findLeaf((KinderInfo) l.list.get(i), newNode, true);

                Log(l.list.get(i).toString());
                Log(leaf.parent.lowerLeftPoint);
                Log(leaf.parent.height);
                Log(leaf.parent.width);
                Log(leaf.parent.splitX);
                Log(leaf.parent.splitPosition);
                leaf.list.add((KinderInfo) l.list.get(i));
                splitLeaf(leaf);
            }
        }
    }


    private Leaf findLeaf(KinderInfo geschenk, BDTreeElement node, boolean debug) {
        if (node instanceof Leaf) {
            return (Leaf) node;
        } else {
            Node n = (Node) node;
            if (geschenk.isLeft(n)) {
                if (debug) {
                    Log("Go Left");
                }
                return findLeaf(geschenk, n.left, debug);
            } else {
                if (debug) {
                    Log("Go Right");
                }
                return findLeaf(geschenk, n.right, debug);
            }
        }
    }

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

    public ArrayList<Object> findWithSection(int x1, int y1, int x2, int y2) {
        int width = x2 - x1;
        int height = y2 - y1;
        return findWithSection(x1, y1, width, height, root);
    }

    public ArrayList<Object> findWithSection(int x1, int y1, int width, int height, BDTreeElement node) {
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