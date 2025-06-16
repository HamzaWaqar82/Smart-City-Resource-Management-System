package Interface;

import java.awt.Point;
import java.io.File;
import java.lang.reflect.Type;
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

import Main.EmergencyService;
import Main.FileManager;
import Main.LocationManager;

public class PnlEmergeny {
    public static List<EmergencyService> emergencyList = new ArrayList<>();
    private static DefaultTableModel emergencyTableModel;
    public static PnlMap pnlMap;

    protected static JPanel createEmergenyJPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Labels
        // 1.
        JLabel lblID = new JLabel("ID:");
        lblID.setBounds(20, 20, 100, 25);
        panel.add(lblID);

        JTextField txtID = new JTextField();
        txtID.setBounds(130, 20, 150, 25);
        panel.add(txtID);

        // 2. location
        JLabel lblLocation = new JLabel("Loaction:");
        lblLocation.setBounds(20, 60, 100, 25);
        panel.add(lblLocation);

        JTextField txtLocation = new JTextField();
        txtLocation.setBounds(130, 60, 150, 25);
        panel.add(txtLocation);

        // 3. lable status
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(20, 100, 100, 25);
        panel.add(lblStatus);

        String options[] = { "Active", "Inactive", "Under Maintainence" };
        JComboBox comboBoxStatus = new JComboBox<>(options);
        comboBoxStatus.setBounds(130, 100, 150, 25);
        panel.add(comboBoxStatus);

        // 4. labal response time
        JLabel lblResponseTime = new JLabel("Response Time: (minutes)");
        lblResponseTime.setBounds(20, 140, 120, 25);
        panel.add(lblResponseTime);

        JTextField txtResponsetime = new JTextField();
        txtResponsetime.setBounds(150, 140, 130, 25);
        panel.add(txtResponsetime);

        // 5. label unit tpye
        JLabel lblunitType = new JLabel("Emergency Unit Type: ");
        lblunitType.setBounds(20, 180, 120, 25);
        panel.add(lblunitType);

        JTextField txtUnitType = new JTextField();
        txtUnitType.setBounds(130, 180, 150, 25);
        panel.add(txtUnitType);

        JLabel lblNumUnits = new JLabel("Number Of Units: ");
        lblNumUnits.setBounds(20, 220, 120, 25);
        panel.add(lblNumUnits);

        JTextField txtNumUnits = new JTextField();
        txtNumUnits.setBounds(150, 220, 130, 25);
        panel.add(txtNumUnits);

        // now lets build table

        String tableColumns[] = { "ID", "Location", "Status", "responset time", "Unit type", "Number of Units" };

        emergencyTableModel = new DefaultTableModel(tableColumns, 0);

        JTable table = new JTable(emergencyTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(320, 20, 600, 300);
        panel.add(scrollPane);

        JButton btnAdd = new JButton("Add EmergencyService");
        btnAdd.setBounds(20, 260, 260, 30);
        panel.add(btnAdd);

        JButton btnDelete = new JButton("Delete EmergencyService");
        btnDelete.setBounds(20, 300, 260, 30);
        panel.add(btnDelete);

        JButton btnCalcCost = new JButton("Calculate maintainenece cost");
        btnCalcCost.setBounds(20, 340, 260, 30);
        panel.add(btnCalcCost);

        JButton btnReport = new JButton("generate Usage report");
        btnReport.setBounds(20, 380, 260, 30);
        panel.add(btnReport);

        JButton btnAlert = new JButton("Send emergency Alert");
        btnAlert.setBounds(20, 420, 260, 30);
        panel.add(btnAlert);

        JButton btnSave = new JButton("Save to JSON");
        btnSave.setBounds(560, 340, 150, 30);
        panel.add(btnSave);

        JButton btnLoad = new JButton("Load from JSON");
        btnLoad.setBounds(720, 340, 150, 30);
        panel.add(btnLoad);

        pnlMap = new PnlMap(new ArrayList<>(emergencyList));
        pnlMap.setBounds(20, 380, 700, 300);
        panel.add(pnlMap);

        // ADD button listener
        btnAdd.addActionListener(e -> {
            try {
                String id = txtID.getText();
                String Location = txtLocation.getText();
                String status = (String) comboBoxStatus.getSelectedItem();
                int responseTime = Integer.parseInt(txtResponsetime.getText());
                String unitType = txtUnitType.getText();
                int numUnit = Integer.parseInt(txtNumUnits.getText());

                Point locationCoordinate = LocationManager.assignLoaction();
                EmergencyService es = new EmergencyService(id, Location, status, locationCoordinate.x,
                        locationCoordinate.y, responseTime, unitType, numUnit);

                emergencyList.add(es);
                emergencyTableModel.addRow(es.toTableRow());
                pnlMap.updateMap(new ArrayList<>(emergencyList));

                txtID.setText("");
                txtLocation.setText("");
                comboBoxStatus.setSelectedIndex(0);
                txtResponsetime.setText("");
                txtUnitType.setText("");
                txtNumUnits.setText("");

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(panel, "Enter Valid Entries.");
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                emergencyList.remove(selectedRow);
                refreshEmergencyTableModel();
                pnlMap.updateMap(new ArrayList<>(emergencyList));
            } else {
                JOptionPane.showMessageDialog(panel, "Select a Table row first to Delete.");
            }
        });

        btnCalcCost.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                EmergencyService es = emergencyList.get(selectedRow);
                double cost = es.calculateMaintenanceCost();
                JOptionPane.showMessageDialog(panel, "Maintainence Cost: " + cost, "Maintainence Cost",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Select an Emrgency Service");
            }
        });

        btnReport.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                EmergencyService es = emergencyList.get(selectedRow);
                String report = es.generateUserReport();
                JOptionPane.showMessageDialog(panel, report, "Usage Report", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Select an Emrgency Service");
            }
        });

        btnAlert.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                EmergencyService es = emergencyList.get(selectedRow);
                es.sendEmergencyAlert();
                JOptionPane.showMessageDialog(panel, "Emergeny Alert has been sent Successfully, see console", "Alert",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Select an Emrgency Service");
            }
        });

        btnSave.addActionListener(e -> {
            FileManager.save(panel, emergencyList, "emergency_services", "json");
        });

        btnLoad.addActionListener(e -> {
            Type listType = new TypeToken<List<EmergencyService>>() {
            }.getType();

            List<EmergencyService> loaded = FileManager.load(panel, listType, "emergency_services", "json");

            if (loaded != null) {
                emergencyList.clear();
                emergencyTableModel.setRowCount(0);

                for (EmergencyService es : loaded) {
                    emergencyList.add(es);
                    emergencyTableModel.addRow(es.toTableRow());
                }

                JOptionPane.showMessageDialog(panel, "Emergency services loaded successfully from file");
            } else {
                JOptionPane.showMessageDialog(panel, "failed to load the file");
            }

        });

        return panel;
    }

    private static void refreshEmergencyTableModel() {
        emergencyTableModel.setRowCount(0);

        for (EmergencyService emergencyService : emergencyList) {
            emergencyTableModel.addRow(emergencyService.toTableRow());
        }
    }
}
