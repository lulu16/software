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
import modelo.Usuario;

/**
 *
 * @author ADRIANA
 */
public class ControladorUsuario {

    private Conexion con;
    PreparedStatement ps;
    ResultSet rs;
    private String nomUsuario;
    private Statement statement;

    public ControladorUsuario() {
        con = new Conexion();
        nomUsuario = new String();

    }

    public Usuario existeUsuario(String usr, String pass) {
         con = new Conexion();
        Usuario a = new Usuario();

        try {
            statement = con.getConexion().createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_UPDATABLE);
            rs = statement.executeQuery("SELECT * FROM usuario WHERE correo = '" + usr + "' and contrasenia = '" + pass + "';");
        while (rs.next()) {
                int id = rs.getInt(1);

                String nombres = rs.getString(2);
                String apellidos = rs.getString(3);
                String correo = rs.getString(4);
                String contrasenia = rs.getString(5);

                a.setId(id);
                a.setNombre(nombres);
                a.setApellido(apellidos);
                a.setCorreo(correo);
                a.setContrasenia(contrasenia);

            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos tabla alumno ");
        }
        return a;
        

    }

    
    public boolean crearUsuario(Usuario usr) {
        boolean bandera = false;
        String sql = "INSERT INTO usuario(nombre, apellido, correo, contrasenia"
                + ") " + "VALUES (?,?,?,?);";
        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, usr.getNombre());
            ps.setString(2, usr.getApellido());
            ps.setString(3, usr.getCorreo());
            ps.setString(4, usr.getContrasenia());

            ps.execute();
            bandera = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            con.desconectar();
        }

        return bandera;
    }

    public boolean actualizarUsuario(Usuario usr, String correo) {
        boolean bandera = false;
        String sql = "UPDATE usuario SET  nombre=?, apellido=?, correo=?, contrasenia=?"
                + "WHERE correo= '" + correo + "'";;
        try {
            ps = con.getConexion().prepareStatement(sql);
            ps.setString(1, usr.getNombre());
            ps.setString(2, usr.getApellido());
            ps.setString(3, usr.getCorreo());
            ps.setString(4, usr.getContrasenia());
            ps.executeUpdate();
            bandera = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            con.desconectar();
        }

        return bandera;
    }

    public void eliminarUsuario(Usuario a) {
        try {
            con = new Conexion();
            statement = con.getConexion().createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_UPDATABLE);
            statement.executeUpdate("DELETE FROM Usuario WHERE id_usu =" + a.getId() + ";");
            JOptionPane.showMessageDialog(null, "El Usuario ha sido Eliminado");
        } catch (SQLException ex) {
//            System.out.println("Hubo un problema al intentar conectarse con la base de datos " + url);
            System.out.println("Error " + ex);
            JOptionPane.showMessageDialog(null, "El usuariooo no ha sido Eliminado");
        }
    }

    public List<Usuario> listar() {
        con = new Conexion();
        con.desconectar();
        List<Usuario> lista = new ArrayList<>();
        Usuario a;

        try {
            statement = con.getConexion().createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_UPDATABLE);
            rs = statement.executeQuery("SELECT * FROM Usuario Order by apellido ASC;");
            while (rs.next()) {
                a = new Usuario();
                int id = rs.getInt(1);
                String nombres = rs.getString(2);
                String apellidos = rs.getString(3);
                String correo = rs.getString(4);
                String contrasenia = rs.getString(5);

                a.setId(id);
                a.setNombre(nombres);
                a.setApellido(apellidos);
                a.setCorreo(correo);
                a.setContrasenia(contrasenia);

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

    public Usuario buscarUsuario(int codigo) {
        con = new Conexion();
        Usuario a = new Usuario();

        try {
            statement = con.getConexion().createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_UPDATABLE);
            rs = statement.executeQuery("SELECT * FROM Usuario WHERE id_usu= " + codigo + ";");
            while (rs.next()) {
                int id = rs.getInt(1);

                String nombres = rs.getString(2);
                String apellidos = rs.getString(3);
                String correo = rs.getString(4);
                String contrasenia = rs.getString(5);

                a.setId(id);
                a.setNombre(nombres);
                a.setApellido(apellidos);
                a.setCorreo(correo);
                a.setContrasenia(contrasenia);

            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos tabla alumno ");
        }
        return a;
    }
    
    public Usuario buscarUsuarioNombre(String nombre, String apellido) {
        con = new Conexion();
        Usuario a = new Usuario();

        try {
            statement = con.getConexion().createStatement(rs.TYPE_SCROLL_SENSITIVE, rs.CONCUR_UPDATABLE);
            rs = statement.executeQuery("SELECT * FROM Usuario WHERE nombre = '" + nombre + "' and apellido = '" + apellido + "';");
            while (rs.next()) {
                int id = rs.getInt(1);

                String nombres = rs.getString(2);
                String apellidos = rs.getString(3);
                String correo = rs.getString(4);
                String contrasenia = rs.getString(5);

                a.setId(id);
                a.setNombre(nombres);
                a.setApellido(apellidos);
                a.setCorreo(correo);
                a.setContrasenia(contrasenia);

            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos tabla alumno ");
        }
        return a;
    }
    
    

//         public Usuario buscarUsuarioCedula(String cedula) {
//        Usuario usr=new Usuario();
//        ControladorTipoUsuario contTipoUser=new ControladorTipoUsuario();
//        String sql = "SELECT * FROM usuario, tipousuario WHERE cedula = '" + cedula + "' and id_TipoUsuario=TipoUsuario_id_TipoUsuario;";
//        try {
//            ps = con.getConexion().prepareStatement(sql);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//              usr.setUsuario(rs.getString("usuario"));
//              usr.setContrasenia(rs.getString("contrasenia"));
//              usr.setNombre(rs.getString("nombre"));
//              usr.setApellido(rs.getString("apellido"));
//              usr.setCedula(rs.getString("cedula"));
//              usr.setObservacion(rs.getString("observacion"));
//              TipoUsuario tu=contTipoUser.buscarCedula(usr.getCedula());
//              usr.setTipoUser(tu);
////              
////            } else {
////                JOptionPane.showMessageDialog(null, "No existe usuario");
////            }
////            con.desconectar();
////        } catch (SQLException ex) {
////            JOptionPane.showMessageDialog(null, "No existe");
////        }
////        return usr;
////
////    }
//        public boolean verificarExisteCedula(String cedula) {
//        boolean bandera=false;
//        String sql = "SELECT * FROM usuario, tipousuario WHERE cedula = '" + cedula + "' and id_TipoUsuario=TipoUsuario_id_TipoUsuario;";
//        try {
//            ps = con.getConexion().prepareStatement(sql);
//            rs = ps.executeQuery();
//            if (rs.next()) {  
//              bandera=true;
//            } else {
//                bandera=false;
//            }
//            con.desconectar();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "No existe");
//        }
//        return bandera;
//    }
//        public boolean verificarExisteUsuario(String usuario) {
//        boolean bandera=false;
//        String sql = "SELECT * FROM usuario, tipousuario WHERE usuario='"+usuario+"' and id_TipoUsuario=TipoUsuario_id_TipoUsuario;";
//        try {
//            ps = con.getConexion().prepareStatement(sql);
//            rs = ps.executeQuery();
//            if (rs.next()) {  
//              bandera=true;
//            } else {
//                bandera=false;
//            }
//            con.desconectar();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "No existe");
//        }
//        return bandera;
//    }
////    public static void main(String[] args) {
//        ControladorUsuario conu = new ControladorUsuario();
//        System.out.println(conu.verificarUsuria("1900790682", "apaqui"));
//    }
}
