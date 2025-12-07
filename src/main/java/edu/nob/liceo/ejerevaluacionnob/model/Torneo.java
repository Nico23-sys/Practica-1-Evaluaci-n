package edu.nob.liceo.ejerevaluacionnob.model;

public class Torneo {
    private int id_torneo;
    private String nombre;
    private int anho;
    private String modalidad;

    public Torneo(int id_torneo, String nombre, int anho, String modalidad, String pais) {
        this.id_torneo = id_torneo;
        this.nombre = nombre;
        this.anho = anho;
        this.modalidad = modalidad;
        this.pais = pais;
    }
    public Torneo(String nombre, int anho, String modalidad, String pais) {
        this.nombre = nombre;
        this.anho = anho;
        this.modalidad = modalidad;
        this.pais = pais;
    }


    public int getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(int id_torneo) {
        this.id_torneo = id_torneo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }


    private String pais;
}
