package Interface;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.gson.reflect.TypeToken;

import java.awt.Point;
import java.lang.reflect.Type;

import Main.FileManager;
import Main.LocationManager;
import Main.TransportUnit;

public class PnlTransport {

    private static List<TransportUnit> transportList = new ArrayList<>();
    private static DefaultTableModel transportTableModel;
    private static PnlMap pnlMap;

    protected static JPanel createTransportPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // (String resourceID, String location, String status, String fuelType, int
        // capacity,int currentPassengers)
        // 1. ID
        JLabel lblID = new JLabel("ID: ");
        lblID.setBounds(20, 20, 100, 25);
        panel.add(lblID);

        JTextField txtID = new JTextField();
        txtID.setBounds(130, 20, 150, 25);
        panel.add(txtID);

        // 2. location

        JLabel lblLocation = new JLabel("Location: ");
        lblLocation.setBounds(20, 60, 100, 25);
        panel.add(lblLocation);

        JTextField txtLocation = new JTextField();
        txtLocation.setBounds(130, 60, 150, 25);
        panel.add(txtLocation);

        // 3. status
        JLabel lblstatus = new JLabel("Status");
        lblstatus.setBounds(20, 100, 100, 25);
        panel.add(lblstatus);

        JComboBox<String> comboBoxStatus = new JComboBox<>(new String[] { "Active", "Inactive", "MainTainenece" });
        comboBoxStatus.setBounds(130, 100, 150, 25);
        panel.add(comboBoxStatus);

        // 4. fueltype
        JLabel lblFuelType = new JLabel("Fuel Type: ");
        lblFuelType.setBounds(20, 140, 100, 25);
        panel.add(lblFuelType);

        JTextField txtFuel = new JTextField();
        txtFuel.setBounds(130, 140, 150, 25);
        panel.add(txtFuel);

        // 5. capacity
        JLabel lblCapacity = new JLabel("Capacity:");
        lblCapacity.setBounds(20, 180, 100, 25);
        panel.add(lblCapacity);

        JTextField txtCapacity = new JTextField();
        txtCapacity.setBounds(130, 180, 150, 25);
        panel.add(txtCapacity);

        // 6. passengers

        // creating the table
        String[] columns = { "ID", "Location", "Status", "Fuel Type", "Capacity" };
        transportTableModel = new DefaultTableModel(columns, 0);

        JTable table = new JTable(transportTableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(320, 20, 600, 300);
        panel.add(scrollPane);

        // button add
        JButton btnAdd = new JButton("Add Transport");
        btnAdd.setBounds(20, 220, 260, 30);
        panel.add(btnAdd);

        // button Delete
        JButton btnDelete = new JButton("Delete TransportUnit");
        btnDelete.setBounds(20, 260, 260, 30);
        panel.add(btnDelete);

        // button calculate maintainence Cost
        JButton btnCalcMaintainenceCost = new JButton("Calculate Maintainence Cost");
        btnCalcMaintainenceCost.setBounds(20, 300, 260, 30);
        panel.add(btnCalcMaintainenceCost);

        // button Generate Usage Report
        JButton btnGenerateUserReport = new JButton("Generate Usage Report");
        btnGenerateUserReport.setBounds(20, 340, 260, 30);
        panel.add(btnGenerateUserReport);

        pnlMap = new PnlMap(new ArrayList<>(transportList));
        pnlMap.setBounds(20, 400, 900, 200);
        panel.add(pnlMap);

        // listener for the transport unit add btn
        btnAdd.addActionListener(e -> {
            try {
                String id = txtID.getText();
                String location = txtLocation.getText();
                String status = (String) comboBoxStatus.getSelectedItem();
                String fuelType = txtFuel.getText();
                int capacity = Integer.parseInt(txtCapacity.getText());

                Point locationCoordinate = LocationManager.assignLoaction();
                TransportUnit newUnit = new TransportUnit(id, location, status, locationCoordinate.x,
                        locationCoordinate.y, fuelType, capacity, capacity);

                transportList.add(newUnit);
                transportTableModel.addRow(newUnit.toTableRow());
                pnlMap.updateMap(new ArrayList<>(transportList));

                // Clear text fields
                txtID.setText("");
                txtLocation.setText("");
                comboBoxStatus.setSelectedIndex(0);
                txtFuel.setText("");
                txtCapacity.setText("");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Listner for the delete btn
        btnDelete.addActionListener(e -> {
            int toDeleteRow = table.getSelectedRow();

            if (toDeleteRow != -1) {
                transportList.remove(toDeleteRow);
                refreshTableModel();
                pnlMap.updateMap(new ArrayList<>(transportList));
            } else {
                JOptionPane.showMessageDialog(panel, "Select a row first!");
            }
        });

        // Action listner for the claculate maintainence cost
        btnCalcMaintainenceCost.addActionListener(e -> {
            try {

                int selectedRow = table.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(panel, "Select a Transport Unit from the Table and then Calculate");

                    return;
                }

                TransportUnit selectedUnit = transportList.get(selectedRow);

                double cost = selectedUnit.calculateMaintenanceCost();

                String report = selectedUnit.generateUserReport();

                String messge = "Maintainence Cost : " + cost + "\n\n" + report;

                JOptionPane.showMessageDialog(panel, messge, "Maintainence Cost Report",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Action listner for the usage report
        btnGenerateUserReport.addActionListener(e -> {
            try {

                int selectedRow = table.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(panel, "Select a Transport Unit to generate the report");
                } else {
                    TransportUnit selectedUnit = transportList.get(selectedRow);
                    String message = selectedUnit.generateUserReport();

                    JOptionPane.showMessageDialog(panel, message, "Usage Report", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JButton btnSave = new JButton("Save to JSON");
        btnSave.setBounds(560, 340, 150, 30);
        panel.add(btnSave);

        JButton btnLoad = new JButton("Load from JSON");
        btnLoad.setBounds(720, 340, 150, 30);
        panel.add(btnLoad);

        // Save Logic
        btnSave.addActionListener(e -> {
            FileManager.save(panel, transportList, "Transport Units", "json");
        });

        // Load Logic
        btnLoad.addActionListener(e -> {
            Type listType = new TypeToken<List<TransportUnit>>() {
            }.getType();

            List<TransportUnit> loaded = FileManager.load(panel, listType, "Transport Units", "json");

            if (loaded != null) {
                transportList.clear();
                transportTableModel.setRowCount(0);
                for (TransportUnit t : loaded) {
                    transportList.add(t);
                    transportTableModel.addRow(t.toTableRow());
                }
                pnlMap.updateMap(new ArrayList<>(transportList));
                JOptionPane.showMessageDialog(panel, "Transport units loaded from JSON.");
            } else {
                JOptionPane.showMessageDialog(panel, "Failed to load file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private static void refreshTableModel() {
        transportTableModel.setRowCount(0);

        for (TransportUnit transportUnit : transportList) {
            transportTableModel.addRow(transportUnit.toTableRow());
        }
    }

}
