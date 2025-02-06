
package com.mycompany.bodegaapp;

public class Elemento {
    String nombre;
    int precio;
    
    public Elemento(String nombre, int precio) {
        this.nombre = nombre;
        this.precio = precio;
     
    }
    // Constructor vacío necesario para Firebase y deserialización
    public Elemento() {
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    //Para que pueda leer el objeto para subir
    @Override
    public String toString() {
        return "Persona{" +
                "nombre ='" + nombre + '\'' +
                ", precio =" + precio ;
    }
}
