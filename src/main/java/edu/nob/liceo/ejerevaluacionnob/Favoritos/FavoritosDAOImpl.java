package edu.nob.liceo.ejerevaluacionnob.Favoritos;

import edu.nob.liceo.ejerevaluacionnob.db.DataBaseConnection;
import edu.nob.liceo.ejerevaluacionnob.model.Golfistas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoritosDAOImpl implements FavoritosDAO {
    @Override
    public List<Golfistas> getFavoritosPorUsuario(int usuario_id) {
        List<Golfistas> listafav = new ArrayList<>();
        String sql = "SELECT g.* FROM golfistas g " +
                "INNER JOIN favoritos f ON g.id_golfista = f.id_jugador " +
                "WHERE f.usuario_id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuario_id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listafav.add(new Golfistas(
                            rs.getInt("id_golfista"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getInt("edad"),
                            rs.getString("pais"),
                            rs.getString("tipopalo")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listafav;
    }
}
