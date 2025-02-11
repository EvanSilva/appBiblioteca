package edu.badpals.recyclerview.entities;

public class Tarjeta {

    private int img;
    private String descripcion;
    private String nombre;
    boolean isVisited = false;

    public Tarjeta(int img, String descripcion, String nombre, boolean isVisited) {
        this.img = img;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.isVisited = isVisited;
    }

    public Tarjeta(int img, String descripcion, String nombre) {
        this.img = img;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited() {
        isVisited = true;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
