package com.phase1;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class UpdateDialog extends JDialog {
    private int updatedID;
    private JTextField nameField;
    private JTextField classField;
    private JTextField totalFeesField;
    private JTextField paidField;

    public UpdateDialog(int idToUpdate) {
        this.updatedID = idToUpdate;

        // Initialize the dialog
        setLayout(new GridLayout(5, 2));
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);
        add(new JLabel("Class:"));
        classField = new JTextField();
        add(classField);
        add(new JLabel("Total Fees:"));
        totalFeesField = new JTextField();
        add(totalFeesField);
        add(new JLabel("Paid:"));
        paidField = new JTextField();
        add(paidField);

        // Add code to set initial values in the fields based on the selected record
        // You might need to retrieve the existing record based on idToUpdate
    }

    public int getUpdatedID() {
        return updatedID;
    }

    public String getUpdatedName() {
        return nameField.getText();
    }

    public String getUpdatedClass() {
        return classField.getText();
    }

    public double getUpdatedTotalFees() {
        return Double.parseDouble(totalFeesField.getText());
    }

    public double getUpdatedPaid() {
        return Double.parseDouble(paidField.getText());
    }
}
