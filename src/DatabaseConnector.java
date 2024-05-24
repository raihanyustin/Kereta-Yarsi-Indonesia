/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author mraih
 */
public class DatabaseConnector {
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
}
