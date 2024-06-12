/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mraih
 */
public class DatabaseConnector {

    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost/database_kyi";
    private static final String user = "root";
    private static final String password = "";

    private static java.sql.Connection connect;
    public static java.sql.Connection getConnect(){ 
        if (connect == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/database_kyi";
                String user = "root";
                String password = "";
                connect = DriverManager.getConnection(url, user, password);
                System.out.println("Connection Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error!");
            }
        }
        return connect;
    }
    private static Statement st;
    private static ResultSet rs;
    private static PreparedStatement ps;

    private static boolean response;

    public DatabaseConnector() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            connect = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    public String[] getColumnName(String table) {
        String[] columnName = new String[0];
        try {
            ResultSetMetaData metaData = findAll(table).getMetaData();
            int count = metaData.getColumnCount();
            columnName = new String[count];
            for (int i = 0; i < count; i++) {
                columnName[i] = metaData.getColumnName(i);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnName;
    }

    public int getColumnCount(String table) {
        int count = 0;
        ResultSetMetaData metaData;
        try {
            metaData = findAll(table).getMetaData();
            count = metaData.getColumnCount();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public ResultSet findAll(String table) {
        return executeQuery("SELECT * FROM " + table);
    }

    public ResultSet executeQuery(String execute) {
        try {
            st = connect.createStatement();
            rs = st.executeQuery(execute);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    public boolean insert(String table, String... value) {
        String[] columnName = getColumnName(table);
        if (columnName.length < 1) {
            return false;
        }
        String query = "INSERT INTO " + table + " (";
        for (String row : columnName) {
            query += row + ", ";
        }
        query += ") VALUES (";
        for (int i = 0; i < getColumnCount(table); i++) {
            query += "?";
            if (i + 1 < getColumnCount(table)) {
                query += ", ";
            }
        }
        query += ")";
        System.out.println(query);
        try {
            ps = connect.prepareStatement(query);
            for (int i = 1; i <= getColumnCount(table); i++) {
                ps.setString(i, value[i - 1]);
            }
            response = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }
}
