package edu.nob.liceo.ejerevaluacionnob.dao;

import edu.nob.liceo.ejerevaluacionnob.db.DataBaseConnection;
import edu.nob.liceo.ejerevaluacionnob.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public boolean validarCredenciales(String username, String password) {
        String sql="SELECT * from usuarios where nickname=? and password=?";

        try(Connection c= DataBaseConnection.getConnection()){
            PreparedStatement ps= c.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            try(ResultSet rs= ps.executeQuery()){
                return  (rs.next());
            }
        }catch (SQLException e){
            System.err.println("Error al obtener credenciales");
        }

        return false;
    }

    public Usuario getUsuarioPorUsername(String username){
String sql="SELECT * from usuarios where nickname=?";
Usuario usuario=null;
try(Connection c= DataBaseConnection.getConnection()) {
    PreparedStatement ps= c.prepareStatement(sql);
    ps.setString(1, username);

    try(ResultSet rs= ps.executeQuery()){
        if(rs.next()){
            usuario=new Usuario();
            usuario.setUsuario_id(rs.getInt("usuario_id"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellido(rs.getString("apellido"));
            usuario.setNickname(rs.getString("nickname"));
            usuario.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            usuario.setEmail(rs.getString("email"));
            usuario.setPassword(rs.getString("password"));

        }
    }
}catch (SQLException e){
    System.err.println("Error al obtener usuario");
}

        return null;
    }

}
