/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud_task.DB;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eslam
 */
public class Employees {

//    static final String DB_URL = "jdbc:mysql://localhost/CRUD";
    static final String databaseURL = "jdbc:ucanaccess://C://Users//Eslam//Documents//CRUD_Task.accdb";
//    static final String USER = "root";
//    static final String PASS = "root";

    public void start() {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "SELECT * FROM tst";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("ID");
                String fullname = result.getString("name");
                String email = result.getString("address");
                int phone = result.getInt("Phone");
                String level = result.getString("level");

                System.out.println(id + ", " + fullname + ", " + email + ", " + phone);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Employees.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object[][] getAllEmp() {
        String QUERY = "SELECT * FROM employees";
        List<List<Object>> data = new ArrayList<List<Object>>();
        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QUERY);
            // Extract data from result set
            while (rs.next()) {
                data.add(Arrays.asList(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        new Integer(rs.getInt("phone")),
                        rs.getString("level")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] returnData = new Object[data.size()][5];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < 5; j++) {
                returnData[i][j] = data.get(i).get(j);
                System.out.println("i: " + i + " " + data.get(i).get(j));
            }
        }

        return returnData;

    }

    private int getLastRecord() {
        String QUERY = "SELECT * FROM employees ORDER BY id ASC";

        int i = 0;
        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QUERY);
            while (rs.next()) {
                i = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("last id : " + i);
        return i;
    }

    public Boolean addEmp(String name, String address, int phone, String level) {
        int newID = getLastRecord() + 1;
        String QUERY = "insert into employees (id, name, address, phone, level) values (" + newID + ", '" + name + "', '" + address + "', " + phone + ", '" + level + "')";
        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            Statement statement = connection.createStatement();
            int rs = statement.executeUpdate(QUERY);
            System.out.println("q: " + QUERY);
            System.out.println("inserted");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean deleteEmp(int id) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String QUERY = "DELETE FROM employees "
                    + "WHERE id = " + id;

            Statement statement = connection.createStatement();
            int rs = statement.executeUpdate(QUERY);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean updateEmp(int id, String name, String address, int phone, String level) {

//        String sql = "INSERT INTO Contacts (Full_Name, Email, Phone) VALUES (?, ?, ?)";
             
        String QUERY = "update employees set "
                + "name=?, address=?, phone=?, level=? where id=" + id;
        
        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setInt(3, phone);
            preparedStatement.setString(4, level);
            
            int row = preparedStatement.executeUpdate();
             
            if (row > 0) {
                System.out.println("A row has been inserted successfully.");
            }
            
            System.out.println("q: " + QUERY);
            System.out.println("updated");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
