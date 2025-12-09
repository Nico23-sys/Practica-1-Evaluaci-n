package edu.nob.liceo.ejerevaluacionnob.model;

public class Golfistas {
    private int id_golfista;
    private String nombre;
    private String apellido;

    private int edad;
    private String pais;
    private String tipoPalo;


    public Golfistas(int id_golfista, String nombre, String apellido, int edad , String pais, String tipoPalo) {
        this.id_golfista = id_golfista;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.pais = pais;
        this.tipoPalo = tipoPalo;
    }

    public Golfistas( String nombre, String apellido, int edad , String pais, String tipoPalo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.pais = pais;
        this.tipoPalo = tipoPalo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }



    public Golfistas() {}

    public int getId_golfista() {
        return id_golfista;
    }

    public void setId_golfista(int id_golfista) {
        this.id_golfista = id_golfista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTipoPalo() {
        return tipoPalo;
    }

    public void setTipoPalo(String tipoPalo) {
        this.tipoPalo = tipoPalo;
    }

    public String getCategoria() {
        if (this.edad <= 20) {
            return "Junior";
        } else if (this.edad >= 50) {
            return "Veterano";
        } else {
            return "Senior";
        }
    }
}
