package Interface;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Main.SmartGrid;

public class UserInterface extends JFrame {
    private SmartGrid smartGrid = new SmartGrid();

    public void renderDashboard() {
        JFrame frame = new JFrame("Smart City Resource Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 10, 960, 740);

        tabbedPane.add("Transport Unit", PnlTransport.createTransportPanel());

        tabbedPane.add("Power Stations", PnlPowerStation.createPowerPanel());

        tabbedPane.add("Emergency Services", PnlEmergeny.createEmergenyJPanel());

        tabbedPane.add("Smart Grid", PnlSmartGrid.createSmartGridPanel());

        add(tabbedPane);
        setVisible(true);
    }

}