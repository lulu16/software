package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lucy
 * 
 * esto es una prueba
 */
public class Conexion {

    private static Connection conn;
    private final String driver;
    private final String user;
    private final String password;
    private final String url;

    public Conexion() {
        conn = null;
        driver = "com.mysql.jdbc.Driver";
        user = "root";
        password = "password"; 
        url = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";
        //?autoReconnect=true&useSSL=false
    }

    public Connection getConexion() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                //System.out.println("Conexion exitosa");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en la conexion: " +e.getMessage());
        }
        return conn;
    }

    public void desconectar() {
        conn = null;
        if (conn == null) {
            System.out.println("Conexion terminada");
        }
    }

    public static void main(String[] args) {
        Conexion con = new Conexion();
        con.getConexion();
    }
}
