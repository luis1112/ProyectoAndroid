package com.example.empleos.modelos;

public class Postulaciones {
    private int postulacion_id;
    private int salario;
    private String mensaje;
    private int anuncio_id;
    private int persona_id;

    public Postulaciones(){

    }

    public Postulaciones(int postulacion_id, int salario, String mensaje, int anuncio_id, int persona_id) {
        this.postulacion_id = postulacion_id;
        this.salario = salario;
        this.mensaje = mensaje;
        this.anuncio_id = anuncio_id;
        this.persona_id = persona_id;
    }

    public int getPostulacion_id() {
        return postulacion_id;
    }

    public void setPostulacion_id(int postulacion_id) {
        this.postulacion_id = postulacion_id;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getAnuncio_id() {
        return anuncio_id;
    }

    public void setAnuncio_id(int anuncio_id) {
        this.anuncio_id = anuncio_id;
    }

    public int getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(int persona_id) {
        this.persona_id = persona_id;
    }
}
