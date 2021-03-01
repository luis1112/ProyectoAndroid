package com.example.empleos.modelos;

public class Anuncios {
    private int anuncio_id;
    private String titulo;
    private String puesto;
    private String descripcion;
    private String area;
    private String pais;
    private String provincia;
    private String ciudad;
    private String direccion;
    private int persona_id;

    public Anuncios(){

    }

    public Anuncios(int anuncio_id, String titulo, String puesto, String descripcion, String area, String pais, String provincia, String ciudad, String direccion, int persona_id) {
        this.anuncio_id = anuncio_id;
        this.titulo = titulo;
        this.puesto = puesto;
        this.descripcion = descripcion;
        this.area = area;
        this.pais = pais;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.persona_id = persona_id;
    }

    public int getAnuncio_id() {
        return anuncio_id;
    }

    public void setAnuncio_id(int anuncio_id) {
        this.anuncio_id = anuncio_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(int persona_id) {
        this.persona_id = persona_id;
    }
}
