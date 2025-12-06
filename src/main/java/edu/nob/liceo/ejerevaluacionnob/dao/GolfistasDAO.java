package edu.nob.liceo.ejerevaluacionnob.dao;

import edu.nob.liceo.ejerevaluacionnob.model.Golfistas;

import java.util.List;

public interface GolfistasDAO {
    List<Golfistas> getAllGolfistas();

    void addGolfistas(Golfistas newgolfista);

    void actuGolfista(Golfistas golfistaSeleccionado);
}
