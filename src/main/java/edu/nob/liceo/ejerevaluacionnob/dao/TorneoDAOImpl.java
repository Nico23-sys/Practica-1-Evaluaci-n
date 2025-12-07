package edu.nob.liceo.ejerevaluacionnob.dao;

import edu.nob.liceo.ejerevaluacionnob.db.DataBaseConnection;
import edu.nob.liceo.ejerevaluacionnob.model.Golfistas;
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
        String sql = "select * from torneos";

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
}
