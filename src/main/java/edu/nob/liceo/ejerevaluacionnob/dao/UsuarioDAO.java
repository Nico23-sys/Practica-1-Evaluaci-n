package edu.nob.liceo.ejerevaluacionnob.dao;

import edu.nob.liceo.ejerevaluacionnob.model.Usuario;

public interface UsuarioDAO {
    boolean validarCredenciales(String username, String password);
    Usuario getUsuarioPorUsername(String username);

}
