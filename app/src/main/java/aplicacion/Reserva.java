package aplicacion;

public class Reserva {
    private int id;
    private String nombre;
    private String fechaIngreso;
    private String fechaSalida;
    private double precio;
    private String metodoPago;

    public Reserva(int id, String nombre, String fechaIngreso, String fechaSalida, double precio, String metodoPago) {
        this.id = id;
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
        this.metodoPago = metodoPago;
    }

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
