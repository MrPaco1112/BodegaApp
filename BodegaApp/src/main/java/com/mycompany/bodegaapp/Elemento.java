
package com.mycompany.bodegaapp;

public class Elemento {
    String nombre;
    Double precio;
    
    
    public Elemento(String nombre, Double precio) {
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    //Para que pueda leer el objeto para subir
    @Override
    public String toString() {
        return 
                "Nombre :'" + nombre + '\'' +
                "Precio :" + precio ;
    }
}
