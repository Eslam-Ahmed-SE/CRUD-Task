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

    static final String DB_URL = "jdbc:mysql://localhost/CRUD";
    static final String USER = "root";
    static final String PASS = "root";

    public void start() {
        String QUERY = "SELECT * FROM tst";
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", First: " + rs.getString("name"));
                System.out.println(", Last: " + rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object[][] getAllEmp() {
        String QUERY = "SELECT * FROM employees";
        List<List<Object>> data = new ArrayList<List<Object>>();
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);) {

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
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);) {
            while (rs.next()) {
                i=rs.getInt("id");
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
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            System.out.println("q: " + QUERY);
            stmt.executeUpdate(QUERY);
            System.out.println("inserted");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean deleteEmp(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            String sql = "DELETE FROM employees "
                    + "WHERE id = " + id;
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean updateEmp(int id, String name, String address, int phone, String level) {

        String QUERY = "update employees set "
                + "name='" + name + "',"
                + "address='" + address + "',"
                + "phone='" + phone + "',"
                + "level='" + level + "' where id=" + id;
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            System.out.println("q: " + QUERY);
            stmt.executeUpdate(QUERY);
            System.out.println("updated");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
