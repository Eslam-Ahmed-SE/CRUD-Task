/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud_task;

import crud_task.DB.Employees;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Eslam
 */
public class CRUD_Task {

    static Object[][] data;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        start();
    }

    static void start() {
        //        Object[][] data = {
//            {"Kathy", "Smith",
//                "Snowboarding", new Integer(5), new Boolean(false)},
//            {"John", "Doe",
//                "Rowing", new Integer(3), new Boolean(true)},
//            {"Sue", "Black",
//                "Knitting", new Integer(2), new Boolean(false)},
//            {"Jane", "White",
//                "Speed reading", new Integer(20), new Boolean(true)},
//            {"Joe", "Brown",
//                "Pool", new Integer(10), new Boolean(false)}
//        };

        data = new Employees().getAllEmp();

        String[] columnNames = {"ID",
            "Name",
            "Address",
            "Phone",
            "Level"};

        JFrame frame = new JFrame("CRUD Task");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

//        JPanel mainContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel mainControls = new JPanel(new GridLayout(1, 0, 0, 0));
        JPanel mainPage = new JPanel();
        mainPage.setLayout(new BoxLayout(mainPage, BoxLayout.Y_AXIS));

        JButton InsertBtn = new JButton("Insert");
        JButton deleteBtn = new JButton("Delete");
        JButton updateBtn = new JButton("Update");
//        JButton btn3 = new JButton("asd");

        InsertBtn.setPreferredSize(new Dimension(25, 25));

        mainControls.add(InsertBtn);
        mainControls.add(updateBtn);
        mainControls.add(deleteBtn);

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        mainPage.add(scrollPane);
        
        InsertBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new AddUpdateEmployeeScreen();
                frame.setVisible(false); //you can't see me!
                frame.dispose();

            }
        });
        deleteBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!table.getSelectionModel().isSelectionEmpty()) {
                    new Employees().deleteEmp(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
                    start();

                    frame.setVisible(false); //you can't see me!
                    frame.dispose();
                }
            }
        });
        updateBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!table.getSelectionModel().isSelectionEmpty()) {
                    new AddUpdateEmployeeScreen(true,
                            Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()),
                            table.getValueAt(table.getSelectedRow(), 1).toString(),
                            table.getValueAt(table.getSelectedRow(), 2).toString(),
                            Integer.parseInt(table.getValueAt(table.getSelectedRow(), 3).toString()),
                            table.getValueAt(table.getSelectedRow(), 4).toString());
                    frame.setVisible(false); //you can't see me!
                    frame.dispose();

                }
            }
        });

        frame.getContentPane().add(mainControls, BorderLayout.PAGE_START);
        frame.getContentPane().add(mainPage, BorderLayout.CENTER);
        frame.setVisible(true);
//        new Employees().addEmp("eslam5", "15 st sadljasd", 0100234567, "senior");

    }
}

class HintTextField extends JTextField implements FocusListener { //https://stackoverflow.com/users/50476/bart-kiers

    private final String hint;
    private boolean showingHint;

    public HintTextField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }

}
