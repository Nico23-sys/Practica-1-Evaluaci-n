package navegacion;

import edu.nob.liceo.ejerevaluacionnob.model.Usuario;

public class SessionManager {
    private static SessionManager instance;
    private Usuario usuarioactual;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Usuario getUsuarioactual() {
        return usuarioactual;
    }

    public void setUsuarioactual(Usuario usuarioactual) {
        this.usuarioactual = usuarioactual;
    }

    public void logout() {
        this.usuarioactual = null;
    }

}
