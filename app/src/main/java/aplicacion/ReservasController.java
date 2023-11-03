package aplicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.beans.value.ChangeListener; // Importa esta clase
import javafx.beans.value.ObservableValue; // Importa esta clase
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ReservasController {

    private String[] opcionesPago = {"EFECTIVO", "TARJETA"};

    private double precioPorDiaPredeterminado = 200.0;

    @FXML
    public Pane AnchorPane;

    @FXML
    private ImageView Background;

    @FXML
    private Hyperlink HyperReservar;

    @FXML
    private DatePicker Ingreso;

    @FXML
    public ComboBox<String> Metodo_pago; // Define el tipo genérico del ComboBox

    @FXML
    private TextField Nombre;

    @FXML
    private TextField Precio;

    @FXML
    private Button Reservar;

    @FXML
    private DatePicker Salida;

    @FXML
    private Button Tabla;

    @FXML
    private ImageView Tabla_boton;

    @FXML
    private ImageView Typo;

    @FXML
    private ImageView Personitas;

    private void calcularPrecio() {
        LocalDate ingreso = Ingreso.getValue();
        LocalDate salida = Salida.getValue();

        // Verifica si se han seleccionado fechas válidas
        if (ingreso != null && salida != null) {
            try {
                // Calcula la diferencia en días
                long díasDeEstadía = ChronoUnit.DAYS.between(ingreso, salida);

                // Calcula el precio total
                double precioTotal = díasDeEstadía * precioPorDiaPredeterminado;

                // Muestra el precio total en el campo de precio
                Precio.setText(String.valueOf(precioTotal));
            } catch (NumberFormatException e) {
                // Manejo de errores al calcular el precio
                e.printStackTrace();
                System.err.println("Error al calcular el precio.");
            }
        } else {
            // Manejo de fechas nulas
            System.err.println("Por favor, seleccione fechas de ingreso y salida válidas.");
        }
    }
    
    
    @FXML
    void Reservar(ActionEvent event) {
        String precioPorDia = Precio.getText(); // Precio por día
        String ingresoStr = Ingreso.getValue().toString();
        String salidaStr = Salida.getValue().toString();
    
        try {
            // Convierte las fechas de String a objetos LocalDate
            LocalDate ingreso = LocalDate.parse(ingresoStr);
            LocalDate salida = LocalDate.parse(salidaStr);
    
            // Calcula la diferencia en días
            long díasDeEstadía = ChronoUnit.DAYS.between(ingreso, salida);
    
            // Convierte el precio por día a número
            double precioPorDíaNum = Double.parseDouble(precioPorDia);
    
            // Calcula el precio total
            double precioTotal = díasDeEstadía * precioPorDíaNum;
    
            // Obtén el nombre y método de pago (asegúrate de vincular estos campos en tu interfaz)
            String nombreCliente = Nombre.getText();
            String metodoDePago = Metodo_pago.getValue().toString();
    
            // Insertar los datos en la base de datos
            Connection conn = obtenerConexionBaseDatos();
    
            // Consulta SQL para insertar los datos
            String sql = "INSERT INTO arkane_reservas (nombre, fecha_ingreso, fecha_salida, precio, metodo_pago) VALUES (?, ?, ?, ?, ?)";
    
            // Preparar la sentencia SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombreCliente);
            pstmt.setString(2, ingresoStr);
            pstmt.setString(3, salidaStr);
            pstmt.setDouble(4, precioTotal);
            pstmt.setString(5, metodoDePago);
    
            // Ejecutar la inserción
            int filasAfectadas = pstmt.executeUpdate();
    
            if (filasAfectadas > 0) {
                System.out.println("Inserción exitosa");
            } else {
                System.err.println("Error al insertar en la base de datos");
            }
    
            // Cerrar la conexión
            conn.close();
    
            // Muestra el precio total en el campo de precio
            Precio.setText(String.valueOf(precioTotal));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al procesar la reserva.");
        }
    }
    
    public void initialize() {
        // Cargar y mostrar las imágenes en el controlador de registro (ajusta las rutas)
        Image imagenBackground = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/Reservas_Background.jpg");
        Image imagenLogo = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/java.png");
        Image imagenTypo = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/Typo.jpg");
        Image imagenpersonas = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/usuarios.png");

        Metodo_pago.getItems().addAll(opcionesPago);

        Typo.setImage(imagenTypo);
        Background.setImage(imagenBackground);
        Tabla_boton.setImage(imagenLogo);
        Personitas.setImage(imagenpersonas);

        Ingreso.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                calcularPrecio();
            }
        });

        Salida.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                calcularPrecio();
            }
        });
    }

    @FXML
    private AnchorPane contenidoPane; // Asegúrate de vincular esto en Scene Builder

    @FXML
    void Boton_tabla(ActionEvent event) {
        try {
            // Carga el nuevo FXML para la tabla
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Tabla.fxml"));
            Parent nuevoContenido = loader.load();

            // Reemplaza el contenido actual en el AnchorPane
            contenidoPane.getChildren().setAll(nuevoContenido);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obtener una conexión a la base de datos
    public Connection obtenerConexionBaseDatos() {
        Connection conexion = null;
        try {
            // Carga el controlador JDBC de MySQL (asegúrate de que el archivo JAR esté en tu proyecto)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // URL de conexión a la base de datos
            String url = "jdbc:mysql://localhost:3306/arkane_database";
            String usuario = "root";
            String contraseña = "root1234";

            // Establece la conexión
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectarse a la base de datos.");
        }
        return conexion;
    }
}
