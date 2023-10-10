package com.phase1;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class fees {

    private JFrame frame;
    private JButton btnAdd;
    private JButton btnShow;
    private JButton btnDelete;
    private JPanel panelResult;
    private JTable table;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    fees window = new fees();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public fees() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setForeground(new Color(255, 255, 255));
        frame.getContentPane().setBackground(new Color(0, 153, 204));
        frame.setBounds(100, 100, 662, 587);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Fees Management System");
        lblTitle.setForeground(new Color(255, 255, 255));
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 35));
        lblTitle.setBounds(134, 10, 393, 59);
        frame.getContentPane().add(lblTitle);

        // Initialize the panel for displaying the table
        panelResult = new JPanel();
        panelResult.setBounds(10, 157, 628, 383);
        frame.getContentPane().add(panelResult);
        panelResult.setLayout(null);
        panelResult.setVisible(false); // Initially, hide the panel

        // Create a table and add it to a scroll pane
        table = new JTable();
        tableModel = new DefaultTableModel();

        // Add columns to the table model
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Class");
        tableModel.addColumn("Total Fees");
        tableModel.addColumn("Paid");
        tableModel.addColumn("Pending");

        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 608, 363);
        panelResult.add(scrollPane);

        // Set up actions for the buttons
        btnShow = new JButton("Show");
        btnShow.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnShow.setBounds(136, 79, 120, 46);
        frame.getContentPane().add(btnShow);

        // Set up actions for the "Show" button
        btnShow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Load data from the database into the table
                loadDataFromDatabase();
                panelResult.setVisible(true); // Show the panel when the "Show" button is clicked
            }
        });

        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAdd.setBounds(266, 79, 120, 46);
        frame.getContentPane().add(btnAdd);

     // Add your code to handle the "Add" button action here
        btnAdd.addActionListener(e -> {
            // Create a panel to hold the input fields
            JPanel addPanel = new JPanel();
            addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

            // Define input fields
            JTextField idField = new JTextField(10);
            JTextField nameField = new JTextField(20);
            JTextField classField = new JTextField(10);
            JTextField totalFeesField = new JTextField(10);
            JTextField paidField = new JTextField(10);

            // Add input fields to the panel
            addPanel.add(new JLabel("ID:"));
            addPanel.add(idField);
            addPanel.add(new JLabel("Name:"));
            addPanel.add(nameField);
            addPanel.add(new JLabel("Class:"));
            addPanel.add(classField);
            addPanel.add(new JLabel("Total Fees:"));
            addPanel.add(totalFeesField);
            addPanel.add(new JLabel("Paid:"));
            addPanel.add(paidField);


            // Show the input dialog
            int result = JOptionPane.showConfirmDialog(null, addPanel, "Add Student Details", JOptionPane.OK_CANCEL_OPTION);

            // Check if the user clicked "OK" to add the details
            if (result == JOptionPane.OK_OPTION) {
                String id = idField.getText();
                String name = nameField.getText();
                String studentClass = classField.getText();
                String totalFees = totalFeesField.getText();
                String paid = paidField.getText();

                // Add code to save these details to the database
                try {
                    // Connection details
                    String url = "jdbc:mysql://localhost:3306/fees_management?useSSL=false";
                    String username = "root";
                    String password = "system";

                    // Create a database connection
                    Connection connection = DriverManager.getConnection(url, username, password);

                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Insert data into the database
                    String insertQuery = "INSERT INTO student_fees (ID, Name, Class, TotalFees, Paid, Pending) " +
                            "VALUES (" + id + ", '" + name + "', '" + studentClass + "', " + totalFees + ", " + paid + ", " + (Double.parseDouble(totalFees) - Double.parseDouble(paid)) + ")";
                    statement.executeUpdate(insertQuery);

                    // Close the statement and connection
                    statement.close();
                    connection.close();
                    
                    // After adding, you can reload the data using loadDataFromDatabase()
                    loadDataFromDatabase();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnDelete.setBounds(396, 79, 120, 46);
        frame.getContentPane().add(btnDelete);

     // Add your code to handle the "Delete" button action here
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                // If no row is selected, show a message to the user
                JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Delete Record", JOptionPane.WARNING_MESSAGE);
            } else {
                int idToDelete = (int) tableModel.getValueAt(selectedRow, 0);

                int confirmDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete Record", JOptionPane.YES_NO_OPTION);

                if (confirmDelete == JOptionPane.YES_OPTION) {
                    // Add code to delete the selected record from the database
                    try {
                        // Connection details
                        String url = "jdbc:mysql://localhost:3306/fees_management?useSSL=false";
                        String username = "root";
                        String password = "system";

                        // Create a database connection
                        Connection connection = DriverManager.getConnection(url, username, password);

                        // Create a statement
                        Statement statement = connection.createStatement();

                        // Delete the data from the database
                        String deleteQuery = "DELETE FROM student_fees WHERE ID = " + idToDelete;
                        statement.executeUpdate(deleteQuery);

                        // Close the statement and connection
                        statement.close();
                        connection.close();
                        
                        // After deleting, you can reload the data using loadDataFromDatabase()
                        loadDataFromDatabase();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
        
    private void loadDataFromDatabase() {
        try {
            // Connection details
            String url = "jdbc:mysql://localhost:3306/fees_management?useSSL=false";
            String username = "root";
            String password = "system";

            // Create a database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to retrieve data
            String query = "SELECT * FROM student_fees";
            ResultSet resultSet = statement.executeQuery(query);

            // Clear any existing data in the table
            tableModel.setRowCount(0);

            // Iterate through the result set and add data to the table
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                    resultSet.getInt("ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Class"),
                    resultSet.getDouble("TotalFees"),
                    resultSet.getDouble("Paid"),
                    resultSet.getDouble("Pending")
                });
            }

            // Close the statement and connection
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
