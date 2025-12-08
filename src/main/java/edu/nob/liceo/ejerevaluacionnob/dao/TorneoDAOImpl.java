package edu.nob.liceo.ejerevaluacionnob.dao;

import edu.nob.liceo.ejerevaluacionnob.db.DataBaseConnection;

import edu.nob.liceo.ejerevaluacionnob.model.Torneo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TorneoDAOImpl implements TorneoDAO {
    @Override
    public List<Torneo> getAllTorneos() {
        List<Torneo> torneosBD= new ArrayList<>();
        String sql = "select * from torneo";

        try(Connection conn= DataBaseConnection.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql);
            ResultSet rs= pstmt.executeQuery()){

            while(rs.next()){
                Torneo torneos = new Torneo(
                        rs.getInt("id_torneo"),
                        rs.getString("nombre"),
                        rs.getInt("anho"),
                        rs.getString("modalidad"),
                        rs.getString("pais")
                );
                torneosBD.add(torneos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return torneosBD;
    }

    @Override
    public List<Torneo> filtrarTorneos(String termino) {
        List<Torneo> listaTorneos = new ArrayList<>();


        String sql = "SELECT * FROM torneo WHERE nombre LIKE ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            String terminoBusq = "%" + termino.toLowerCase() + "%";

            pstmt.setString(1, terminoBusq);



            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    Torneo tor = new Torneo(
                            rs.getInt("id_torneo"),
                            rs.getString("nombre"),
                            rs.getInt("anho"),
                            rs.getString("modalidad"),
                            rs.getString("pais")
                    );

                    listaTorneos.add(tor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaTorneos;
    }

    @Override
    public void addTorneos(Torneo newTorneo) {
        String sql= "INSERT INTO torneo(nombre, anho, modalidad, pais) VALUES (?, ?, ?, ?)";

        try(Connection conn= DataBaseConnection.getConnection()){
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setString(1, newTorneo.getNombre());
            ps.setInt(2, newTorneo.getAnho());
            ps.setString(3, newTorneo.getModalidad());
            ps.setString(4, newTorneo.getPais());
            ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actuTorneos(Torneo torneoSeleccionado) {
        String sql = "UPDATE torneo SET nombre=?, anho=?, modalidad=?, pais=? WHERE id_torneo=?";

        try (Connection conn = DataBaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            // Parámetros en orden:
            ps.setString(1, torneoSeleccionado.getNombre());
            ps.setInt(2, torneoSeleccionado.getAnho());
            ps.setString(3, torneoSeleccionado.getModalidad());
            ps.setString(4, torneoSeleccionado.getPais());
            ps.setInt(5, torneoSeleccionado.getId_torneo());

            ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarTorneo(int idTorneo) {
        String sql= "DELETE FROM torneo WHERE id_torneo=?";
        try(Connection conn= DataBaseConnection.getConnection()){
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setInt(1, idTorneo);
            ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
}

