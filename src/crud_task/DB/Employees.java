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
            
        } catch (SQLException ex) {
            Logger.getLogger(Employees.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object[][] getAllEmp() {
        String QUERY = "SELECT * FROM employees ORDER BY created ASC";
        List<List<Object>> data = new ArrayList<List<Object>>();
        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QUERY);
            // Extract data from result set
            while (rs.next()) {
                data.add(Arrays.asList(
                        decrypt(rs.getInt("id")),
                        rs.getString("name"),
                        rs.getString("address"),
                        "0" + rs.getInt("phone"),
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
//                System.out.println("i: " + i + " " + data.get(i).get(j));
            }
        }

        return returnData;

    }

    private int getLastRecord() {
        String QUERY = "SELECT * FROM employees ORDER BY created ASC";

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
        int lastID = getLastRecord();
        System.out.println("last id : " + lastID);
        int newID = ((lastID==0)? 0:decrypt(lastID)) + 1;
        System.out.println("new id : " + newID + " encrypt: " + encrypt(newID));
        String QUERY = "insert into employees (id, name, address, phone, level, created) values (" + encrypt(newID) + ", '" + name + "', '" + address + "', " + phone + ", '" + level + "', '" + new java.sql.Timestamp(new java.util.Date().getTime()) +"')";
        System.out.println("q: " + QUERY);
        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            Statement statement = connection.createStatement();
            int rs = statement.executeUpdate(QUERY);
            
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
                    + "WHERE id = " + encrypt(id);

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
                + "name=?, address=?, phone=?, level=? where id=" + encrypt(id);
        
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
    
    int encrypt(int id) {
        String strID = Integer.toString(id);
        byte[] bytesEncoded = Base64.getEncoder().encode(strID.getBytes());

        int value = 0;
        for (byte b : bytesEncoded) {
            value = (value << 8) + (b & 0xFF);
        }

//        System.out.println("encoded value is " + new String(bytesEncoded));
//        System.out.println("encoded value is " + value);
        return value;
    }

    int decrypt(int encoded) {
        byte[] bytesEncoded = new byte[Integer.BYTES];
        int length = bytesEncoded.length;
        for (int i = 0; i < length; i++) {
            bytesEncoded[length - i - 1] = (byte) (encoded & 0xFF);
            encoded >>= 8;
        }

        byte[] valueDecoded = Base64.getDecoder().decode(bytesEncoded);

        int value = Integer.valueOf(new String(valueDecoded));
//        System.out.println("Decoded value is " + new String(valueDecoded));
//        System.out.println("Decoded value is " + value + "-");
        
        return value;

    }

}
