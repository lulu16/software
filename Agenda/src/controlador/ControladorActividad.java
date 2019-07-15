/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Actividad;

/**
 *
 * @author ADRIANA
 */
public class ControladorActividad {

    private Conexion con;
    PreparedStatement ps;
    ResultSet rs;
    private Statement statement;

    public ControladorActividad() {
        con = new Conexion();

    }

    public boolean crearActividad(Actividad usr) {
        boolean bandera = false;
        String sql = "INSERT INTO Actividad(nombre, descripcion, fecha, lugar"
                + ") " + "VALUES (?,?,?,?);";
        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, usr.getNombre());
            ps.setString(2, usr.getDescripcion());
            ps.setString(3, usr.getFecha());
            ps.setString(4, usr.getLugar());

            ps.execute();
            bandera = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            con.desconectar();
        }

        return bandera;
    }

    public boolean modificarActividad(Actividad usr, int cod) {
        boolean bandera = false;
        String sql = "UPDATE Actividad SET  nombre=?, descripcion=?, fecha=?, lugar=?"
                + "WHERE id_act= '" + cod + "'";;
        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, usr.getNombre());
            ps.setString(2, usr.getDescripcion());
            ps.setString(3, usr.getFecha());
            ps.setString(4, usr.getLugar());

            ps.executeUpdate();
            bandera = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            con.desconectar();
        }

        return bandera;
    }

    public void eliminarTarea(Actividad a) {
        try {
            con = new Conexion();
            statement = con.getConexion().createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_UPDATABLE);
            statement.executeUpdate("DELETE FROM Actividad WHERE id_act =" + a.getId() + ";");
            JOptionPane.showMessageDialog(null, "El tareaa ha sido Eliminado");
        } catch (SQLException ex) {
//            System.out.println("Hubo un problema al intentar conectarse con la base de datos " + url);
            System.out.println("Error " + ex);
            JOptionPane.showMessageDialog(null, "El tareaaa no ha sido Eliminado");
        }
    }

    public List<Actividad> listar() {
        con = new Conexion();
        con.desconectar();
        List<Actividad> lista = new ArrayList<>();
        Actividad a;

        try {
            statement = con.getConexion().createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_UPDATABLE);
            rs = statement.executeQuery("SELECT * FROM Actividad Order by nombre ASC;");
            while (rs.next()) {
                a = new Actividad();
                int id = rs.getInt(1);
                String nombres = rs.getString(2);
                String descripcion = rs.getString(3);
                String fecha = rs.getString(4);
                String lugar = rs.getString(5);

                a.setId(id);
                a.setNombre(nombres);
                a.setDescripcion(descripcion);
                a.setFecha(fecha);
                a.setLugar(lugar);

                lista.add(a);
            }
            rs.close();
            statement.close();
            con.desconectar();
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
        return lista;
    }

    public Actividad buscarActividad(int codigo) {
        con = new Conexion();
        Actividad a = new Actividad();

        try {
            statement = con.getConexion().createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_UPDATABLE);
            rs = statement.executeQuery("SELECT * FROM Actividad WHERE id_act= " + codigo + ";");
            while (rs.next()) {
                int id = rs.getInt(1);

                String nombres = rs.getString(2);
                String descripcion = rs.getString(3);
                String fecha = rs.getString(4);
                String lugar = rs.getString(5);

                a.setId(id);
                a.setNombre(nombres);
                a.setDescripcion(descripcion);
                a.setFecha(fecha);
                a.setLugar(lugar);

            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos tabla alumno ");
        }
        return a;
    }

}
