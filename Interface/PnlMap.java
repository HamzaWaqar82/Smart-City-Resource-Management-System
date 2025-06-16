package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JPanel;

import Main.CityResource;

public class PnlMap extends JPanel {
    private List<CityResource> resources;

    public PnlMap(List<CityResource> resources) {
        this.resources = resources;
        setPreferredSize(new Dimension(800, 250));
        setBackground(Color.LIGHT_GRAY);
    }


    public void updateMap(List<CityResource> resources){
        this.resources= resources;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // color coding
        for (CityResource cResource : resources) {
            switch (cResource.getStatus().toLowerCase()) {
                case "active":
                    g.setColor(Color.GREEN);
                    break;

                case "inactive":
                    g.setColor(Color.RED);
                    break;

                case "under maintainence":
                    g.setColor(Color.yellow);
                    break;

                default:
                    g.setColor(Color.GRAY);
                    break;
            }

            // drawing circle
            g.fillOval(cResource.getX(), cResource.getY(), 15, 15);

            g.setColor(Color.BLACK);
            g.drawString(cResource.getLocation(), cResource.getX(), cResource.getY() - 5);
        }

    }
}
