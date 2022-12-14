/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud_task;

import crud_task.DB.Employees;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Base64;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author Eslam
 */
public class AddUpdateEmployeeScreen {

    Boolean isUpdateScreen;

    int id;

    JTextField nameTextField;
    JTextField addressTextField;
    JTextField phoneTextField;
    JTextField levelTextField;

    public AddUpdateEmployeeScreen(Boolean isUpdate, int id, String name, String address, int phone, String level) {

        initComponents();
        this.id = id;
        isUpdateScreen = isUpdate;
        nameTextField.setText(name);
        addressTextField.setText(address);
        phoneTextField.setText("0" + Integer.toString(phone));
        levelTextField.setText(level);
        start();
    }

    public AddUpdateEmployeeScreen() {
        initComponents();
        start();
    }

    void initComponents() {
        isUpdateScreen = false;
        nameTextField = new JTextField(25);
        addressTextField = new JTextField(25);
        phoneTextField = new JTextField(25);
        levelTextField = new JTextField(25);

    }

    public void start() {
        JFrame frame = new JFrame("Add Employee");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 225);
        frame.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setPreferredSize(new Dimension(60, 25));
        JLabel nameError = new JLabel("Name can only be letters");
        nameError.setForeground(Color.red);
        nameError.setVisible(false);
        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setPreferredSize(new Dimension(60, 25));
        JLabel addressError = new JLabel("address can only be letters and numbers");
        addressError.setForeground(Color.red);
        addressError.setVisible(false);
        JLabel phoneLabel = new JLabel("Phone: ");
        phoneLabel.setPreferredSize(new Dimension(60, 25));
        JLabel phoneError = new JLabel("Phone can be numbers only");
        phoneError.setForeground(Color.red);
        phoneError.setVisible(false);
        JLabel levelLabel = new JLabel("Level: ");
        levelLabel.setPreferredSize(new Dimension(60, 25));
        JLabel levelError = new JLabel("level can only be letters");
        levelError.setForeground(Color.red);
        levelError.setVisible(false);

        JLabel addInfo = new JLabel("Info");
        addInfo.setForeground(Color.green);
        addInfo.setVisible(false);

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                nameTextField.setText("");
                addressTextField.setText("");
                phoneTextField.setText("");
                levelTextField.setText("");

                phoneTextField.setBorder(new LineBorder(Color.black, 1));
                phoneError.setVisible(false);
                nameTextField.setBorder(new LineBorder(Color.black, 1));
                nameError.setVisible(false);
            }
        });

        JButton saveUpdateBtn = new JButton(isUpdateScreen ? "Update" : "Save");
        saveUpdateBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean err = false;

                nameTextField.setBorder(new LineBorder(Color.black, 1));
                nameError.setVisible(false);
                addressTextField.setBorder(new LineBorder(Color.black, 1));
                addressError.setVisible(false);
                phoneTextField.setBorder(new LineBorder(Color.black, 1));
                phoneError.setVisible(false);
                levelTextField.setBorder(new LineBorder(Color.black, 1));
                levelError.setVisible(false);
                addInfo.setVisible(false);

                String phone = phoneTextField.getText();
                if (!Pattern.matches("^\\d*$", phone)) {
                    phoneError.setText("Phone can be numbers only");
                    phoneTextField.setBorder(new LineBorder(Color.red, 1));
                    phoneError.setVisible(true);

                    err = true;
                }
                else if (phone.length()!=11){
                    phoneError.setText("Phone can only be 11 numbers");
                    phoneTextField.setBorder(new LineBorder(Color.red, 1));
                    phoneError.setVisible(true);

                    err = true;
                }
                else if (!phone.startsWith("01")) {
                    System.out.println("phone : " + phone + "pattern : " + Pattern.matches("^01", phone) );
                    phoneError.setText("Phone Should start with '01'");
                    phoneTextField.setBorder(new LineBorder(Color.red, 1));
                    phoneError.setVisible(true);

                    err = true;
                }

//                System.out.println("matches: ? " + nameTextField.getText().matches("[A-Za-z ]*"));
                if (!nameTextField.getText().matches("[A-Za-z ]*")) {
                    nameTextField.setBorder(new LineBorder(Color.red, 1));
                    nameError.setVisible(true);

                    err = true;
                    System.out.println("name wrong");
                }

                System.out.println("matches: ? " + addressTextField.getText().matches("[A-Za-z \\d]*"));
                if (!addressTextField.getText().matches("[A-Za-z \\d\\.,]*")) {
                    addressTextField.setBorder(new LineBorder(Color.red, 1));
                    addressError.setVisible(true);

                    err = true;
                    System.out.println("address wrong");
                }

                if (!levelTextField.getText().matches("[A-Za-z ]*")) {
                    levelTextField.setBorder(new LineBorder(Color.red, 1));
                    levelError.setVisible(true);

                    err = true;
                    System.out.println("level wrong");
                }

                if (nameTextField.getText().equals("")
                        || addressTextField.getText().equals("")
                        || phoneTextField.getText().equals("")
                        || levelTextField.getText().equals("")) {

                    addInfo.setText("Some fields are empty");
                    addInfo.setForeground(Color.red);
                    addInfo.setVisible(true);

                    err = true;
                }

                System.out.println("err is : " + err);
                if (!err) {
                    System.out.println("inside if err is : " + err);

                    if (!isUpdateScreen) {
                        if (new Employees().addEmp(nameTextField.getText(), addressTextField.getText(), Integer.parseInt(phoneTextField.getText()), levelTextField.getText())) {
                            addInfo.setText("Added successfully");
                            addInfo.setForeground(Color.green);
                            addInfo.setVisible(true);
                        } else {
                            addInfo.setText("Something went wrong");
                            addInfo.setForeground(Color.red);
                            addInfo.setVisible(true);
                        }

                    } else {
                        if (new Employees().updateEmp(id, nameTextField.getText(), addressTextField.getText(), Integer.parseInt(phoneTextField.getText()), levelTextField.getText())) {
                            addInfo.setText("Updated successfully");
                            addInfo.setVisible(true);
                        } else {
                            addInfo.setText("Something went wrong");
                            addInfo.setForeground(Color.red);
                            addInfo.setVisible(true);
                        }
                    }
                }
            }
        });

        JPanel flow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        flow1.add(nameLabel);
        flow1.add(nameTextField);
        flow1.add(nameError);

        flow2.add(addressLabel);
        flow2.add(addressTextField);
        flow2.add(addressError);

        flow3.add(phoneLabel);
        flow3.add(phoneTextField);
        flow3.add(phoneError);

        flow4.add(levelLabel);
        flow4.add(levelTextField);
        flow4.add(levelError);

        flow5.add(addInfo);
        flow5.add(clearBtn);
        flow5.add(saveUpdateBtn);

        mainPanel.add(flow1);
        mainPanel.add(flow2);
        mainPanel.add(flow3);
        mainPanel.add(flow4);
        mainPanel.add(flow5);

        frame.getContentPane().add(mainPanel, BorderLayout.PAGE_START);
//        frame.getContentPane().add(mainPage, BorderLayout.CENTER);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Closed");
                new CRUD_Task().start();
                e.getWindow().dispose();
            }
        });
    }

    

}
