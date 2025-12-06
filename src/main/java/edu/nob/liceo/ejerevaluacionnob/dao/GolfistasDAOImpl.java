package edu.nob.liceo.ejerevaluacionnob.dao;

import edu.nob.liceo.ejerevaluacionnob.db.DataBaseConnection;
import edu.nob.liceo.ejerevaluacionnob.model.Golfistas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GolfistasDAOImpl implements GolfistasDAO {

    public List<Golfistas> getAllGolfistas() {
        List<Golfistas> golfistasBD= new ArrayList<>();
        String sql = "select * from golfistas";

        try(Connection conn= DataBaseConnection.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql);
            ResultSet rs= pstmt.executeQuery()){

            while(rs.next()){
               Golfistas golfista = new Golfistas(
                        rs.getInt("id_golfista"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getString("pais"),
                        rs.getString("tipopalo")
                );
               golfistasBD.add(golfista);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return golfistasBD;
    }

    public void addGolfistas(Golfistas newgolfista) {
        String sql= "INSERT INTO golfistas(nombre, apellido, edad, pais, tipopalo) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn= DataBaseConnection.getConnection()){
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setString(1, newgolfista.getNombre());
            ps.setString(2, newgolfista.getApellido());
            ps.setInt(3, newgolfista.getEdad());
            ps.setString(4, newgolfista.getPais());
            ps.setString(5, newgolfista.getTipoPalo());
            ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actuGolfista(Golfistas golfistaSeleccionado) {
        String sql= "UPDATE golfistas SET nombre=?, apellido=?, edad=?, pais=?, tipopalo=? WHERE id_golfista=?";

        try(Connection conn= DataBaseConnection.getConnection()){
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setString(1, golfistaSeleccionado.getNombre());
            ps.setString(2, golfistaSeleccionado.getApellido());
            ps.setInt(3, golfistaSeleccionado.getEdad());
            ps.setString(4, golfistaSeleccionado.getPais());
            ps.setString(5, golfistaSeleccionado.getTipoPalo());
            ps.setInt(6, golfistaSeleccionado.getId_golfista());
            ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGolfista(int idGolfista) {
        String sql= "DELETE FROM golfistas WHERE id_golfista=?";
        try(Connection conn= DataBaseConnection.getConnection()){
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setInt(1, idGolfista);
            ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Golfistas> filtrarGolfistas(String termino) {
        List<Golfistas> listaGolfistas = new ArrayList<>();


        String sql = "SELECT * FROM golfistas WHERE nombre LIKE ? OR apellido LIKE ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            String terminoBusq = "%" + termino.toLowerCase() + "%";

            pstmt.setString(1, terminoBusq);
            pstmt.setString(2, terminoBusq);


            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    Golfistas golfista = new Golfistas(
                            rs.getInt("id_golfista"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getInt("edad"),
                            rs.getString("pais"),
                            rs.getString("tipopalo")
                    );

                    listaGolfistas.add(golfista);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaGolfistas;
    }
}
