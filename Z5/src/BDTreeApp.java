import treecomplements.BDTreeElement;
import treecomplements.KinderInfo;
import treecomplements.Leaf;
import treecomplements.Node;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by qwert on 07/01/2017.
 */
public class BDTreeApp extends JPanel {
    private static final Random r = new Random();
    static BDTree bdTree = new BDTree(1000, 1000);

    public static void main(String[] args) {
        JFrame f = new JFrame("BD Tree");
        BDTreeApp frame = new BDTreeApp();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1100, 1100);
        f.add(frame);
        f.setVisible(true);
        int counter = 0;
        int numberOfGeschenke = 100;
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(new File("geschenke.csv")));
            while ((line = br.readLine()) != null ) {
                String[] info = line.split(",");
                String name = info[0];
                String geschenk = info[1];
                int x = Integer.parseInt(info[2]);
                int y = Integer.parseInt(info[3]);
                KinderInfo kinderInfo = new KinderInfo(name, geschenk, x, y);
                bdTree.insert(kinderInfo);
                Log(kinderInfo);
                frame.repaint();
                counter++;
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            Log("File not found ! ");
        } catch (IOException e) {
            Log("Error by reading csv");
        } catch (NumberFormatException e) {
            Log("Error by converting String to int");
        }


        //Aufgabe 5.4 c
        ArrayList<Object> list = bdTree.findWithSection(0, 0, 400, 600);
        Log("Bereichsanfragen: " + list.size());

        ArrayList<Object> list1 = bdTree.findWithSection(0, 600, 400, 1000);
        Log("Bereichsanfragen: " + list1.size());

        ArrayList<Object> list2 = bdTree.findWithSection(400, 0, 1000, 1000);
        Log("Bereichsanfragen: " + list2.size());

        //Aufgabe 5.4 d
        KinderInfo info = bdTree.find(629,496);
        Log(info);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawNode(g, bdTree.root);
    }

    private void drawNode(Graphics g, BDTreeElement node) {
        if (node instanceof Leaf) {
            g.setColor(Color.red);
            Leaf temp = (Leaf) node;
            if (!temp.list.isEmpty()) {
                KinderInfo item = (KinderInfo) temp.list.get(0);
                g.fillOval(item.x-2, item.y-2, 4, 4);
                //g.drawString(item.name, item.x, item.y);
            }
        } else {
            Node n = (Node) node;
            g.setColor(Color.black);
            g.drawRect(n.lowerLeftPoint.x, n.lowerLeftPoint.y, n.width, n.height);
            if (n.splitX) {
                g.drawLine(n.splitPosition, n.lowerLeftPoint.y,  n.splitPosition, n.lowerLeftPoint.y + n.height);
            } else {
                g.drawLine(n.lowerLeftPoint.x, n.splitPosition, n.lowerLeftPoint.x + n.width, n.splitPosition);
            }
            drawNode(g, n.left);
            drawNode(g, n.right);
        }
    }
    private static void Log(Object o) {
        System.out.println(o);
    }
}
