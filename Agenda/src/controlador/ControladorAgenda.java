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
import modelo.Agenda;
import modelo.Tarea;
import modelo.Usuario;

/**
 *
 * @author ADRIANA
 */
public class ControladorAgenda {

    private Conexion con;
    PreparedStatement ps;
    ResultSet rs;
    private Statement statement;
    
    public ControladorAgenda() {
        con = new Conexion();
        
    }
    
    public boolean crearTarea(Agenda usr) {
        boolean bandera = false;
        String sql = "INSERT INTO Agenda(Actividad_id_act, Tarea_id_tar, Usuario_id_usu"
                + ") " + "VALUES (?,?,?);";
        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setInt(1, usr.getActividad().getId());
            ps.setInt(2, usr.getTarea().getId());
            ps.setInt(3, usr.getUsuario().getId());
//            
            
            ps.execute();
            bandera = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            con.desconectar();
        }
        
        return bandera;
    }

    public boolean modificarTarea(Agenda usr, int cod) {
        boolean bandera = false;
        String sql = "UPDATE Agenda SET  Actividad_id_act=?, Tarea_id_tar=?, Usuario_id_usu=?"
                + "WHERE id_age= '" + cod + "'";;
        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setInt(1, usr.getActividad().getId());
            ps.setInt(2, usr.getTarea().getId());
            ps.setInt(3, usr.getUsuario().getId());
//            
            
            ps.executeUpdate();
            bandera = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            con.desconectar();
        }
        
        return bandera;
    }
    
    public void eliminarTarea(Agenda a) {
        try {
            con = new Conexion();
            statement = con.getConexion().createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_UPDATABLE);
            statement.executeUpdate("DELETE FROM Agenda WHERE id_age =" + a.getId() + ";");
            JOptionPane.showMessageDialog(null, "El tareaa ha sido Eliminado");
        } catch (SQLException ex) {
//            System.out.println("Hubo un problema al intentar conectarse con la base de datos " + url);
            System.out.println("Error " + ex);
            JOptionPane.showMessageDialog(null, "El tareaaa no ha sido Eliminado");
        }
    }

    public List<Agenda> listar() {
        con = new Conexion();
        con.desconectar();
        List<Agenda> lista = new ArrayList<>();
        ControladorActividad controladorActividad = new ControladorActividad();
        ControladorTarea controladorTarea = new ControladorTarea();
        ControladorUsuario controladorUsuario = new ControladorUsuario();
        Agenda a;
        
        try {
            statement = con.getConexion().createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_UPDATABLE);
            rs = statement.executeQuery("SELECT * FROM Agenda;");
            while (rs.next()) {
                a = new Agenda();
                int id = rs.getInt(1);
                int actividad = rs.getInt(2);
                int tarea = rs.getInt(3);
                int usuario = rs.getInt(4);
                
                Actividad act = controladorActividad.buscarActividad(actividad);
                Usuario usu = controladorUsuario.buscarUsuario(usuario);
                Tarea tar = controladorTarea.buscarTarea(tarea);
                
                a.setId(id);
                a.setActividad(act);
                a.setTarea(tar);
                a.setUsuario(usu);
                
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
    
}
