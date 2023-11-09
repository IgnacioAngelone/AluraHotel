package aplicacion;

/*

Clase que representa una reserva en el sistema.

*/

public class Reserva {

private int id;
private String nombre;
private String fechaIngreso;
private String fechaSalida;
private double precio;
private String metodoPago;

/**
* 
*Constructor de la clase Reserva.
*
* @param id    
* @param nombre     
* @param fechaIngreso 
* @param fechaSalida  
* @param precio      
* @param metodoPago 
*
*/

public Reserva(int id, String nombre, String fechaIngreso, String fechaSalida, double precio, String metodoPago) {
this.id = id;
this.nombre = nombre;
this.fechaIngreso = fechaIngreso;
this.fechaSalida = fechaSalida;
this.precio = precio;
this.metodoPago = metodoPago;
}

/**
* 
* Getters y setters.
*
* @return el identificador de la reserva.
*
*/

public int getId() {
return id;
}

public String getNombre() {
return nombre;
}

public String getFechaIngreso() {
return fechaIngreso;
}


public String getFechaSalida() {
return fechaSalida;
}

public double getPrecio() {
return precio;
}

public String getMetodoPago() {
return metodoPago;
}

}
