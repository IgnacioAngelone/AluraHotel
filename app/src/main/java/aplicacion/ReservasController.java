package aplicacion;

     import java.sql.Connection;
     import java.sql.DriverManager;
     import java.sql.PreparedStatement;
     import java.time.LocalDate;
     import java.time.temporal.ChronoUnit;
     import javafx.beans.value.ChangeListener;
     import javafx.beans.value.ObservableValue; 
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

/*

Clase de controlador para las reservas.

*/

public class ReservasController {

// Panel para manipular.

@FXML
private AnchorPane contenidoPane;

@FXML
public Pane AnchorPane;

// Formulario de la interfaz gráfica

@FXML
private TextField Nombre;

@FXML
private DatePicker Ingreso;

@FXML
private DatePicker Salida;

@FXML
public ComboBox<String> Metodo_pago;

// Imágenes de la interfaz gráfica

private String[] opcionesPago = {"EFECTIVO", "TARJETA"};

private double precioPorDiaPredeterminado = 200.0;

@FXML
private TextField Precio;

@FXML
private ImageView Background;

@FXML
private Hyperlink HyperReservar;

// Imágenes de la interfaz gráfica

@FXML
private ImageView Tabla_boton;

@FXML
private ImageView Typo;

@FXML
private ImageView Personitas;

// Botones de la interfaz grafica

@FXML
private Button Reservar;

@FXML
private Button Tabla;

/*
* Calcula el precio total y actualiza el campo de precio.
*/

private double calcularPrecio() {

// #1 Se obtiene el valor de ingreso
LocalDate ingreso = Ingreso.getValue();

// #2 Se obtiene el valor de salida
LocalDate salida = Salida.getValue();
 
if (ingreso != null && salida != null) {
try {

// #3 Calcula la diferencia en días entre la fecha de ingreso y salida
long díasDeEstadía = ChronoUnit.DAYS.between(ingreso, salida);

// #4 Calcula el precio total multiplicando los días por el precio por día predeterminado
return díasDeEstadía * precioPorDiaPredeterminado;

} catch (NumberFormatException e) {
e.printStackTrace();
System.err.println("Error al calcular el precio.");
}
} else {
System.err.println("Fechas de ingreso y salida válidas.");
}

// Devuelve un valor predeterminado en caso de error
return 0.0; 

 }

/*
* Metodo para reservar.
*/

@FXML
void Reservar(ActionEvent event) {

try {

 // #1 Obtiene los datos del formulario

String nombreCliente = Nombre.getText();

String ingresoStr = Ingreso.getValue().toString();

String salidaStr = Salida.getValue().toString();

String metodoDePago = Metodo_pago.getValue().toString();

// #2 Obtiene el precio total
double precioTotal = calcularPrecio();

// #3 Establece una conexion con la base de datos
Connection conn = obtenerConexionBaseDatos();

// #4 Define la query para insertar los valores
String sql = "INSERT INTO arkane_reservas (nombre, fecha_ingreso, fecha_salida, precio, metodo_pago) VALUES (?, ?, ?, ?, ?)";
PreparedStatement pstmt = conn.prepareStatement(sql);

// #5 Establece los valores de la consulta con un index
pstmt.setString(1, nombreCliente);
pstmt.setString(2, ingresoStr);
pstmt.setString(3, salidaStr);
pstmt.setDouble(4, precioTotal);
pstmt.setString(5, metodoDePago);

// #6 Ejecuta la consulta y almacena las filas
int filasAfectadas = pstmt.executeUpdate();

if (filasAfectadas > 0) {
System.out.println("Registrado");
} else {
System.err.println("Error al registrar");
}

// #7 Cierra la conexion
conn.close();

// #8 Actualiza el campo de precio
Precio.setText(String.valueOf(precioTotal));

} catch (Exception e) {
e.printStackTrace();
System.err.println("Error al reservar.");
}

}

/*
* Inicializa el controlador.
*/

public void initialize() {

// #1 Carga todas las imagenes
Image imagenBackground = new Image(getClass().getResource("/Reservas_Background.jpg").toExternalForm());
Image imagenLogo = new Image(getClass().getResource("/java.png").toExternalForm());
Image imagenTypo = new Image(getClass().getResource("/Typo.jpg").toExternalForm());
Image imagenpersonas = new Image(getClass().getResource("/usuarios.png").toExternalForm());

     // #2 Agrega opciones de pago al componente
     Metodo_pago.getItems().addAll(opcionesPago);

// #3 Asigna las imagenes a los componentes
Typo.setImage(imagenTypo);
Background.setImage(imagenBackground);
Tabla_boton.setImage(imagenLogo);
Personitas.setImage(imagenpersonas);

// #4 Calcula los precios de ingreso y salida
Ingreso.valueProperty().addListener(new ChangeListener<LocalDate>() {
@Override
public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
calcularPrecio();
}});

Salida.valueProperty().addListener(new ChangeListener<LocalDate>() {
@Override
public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
     calcularPrecio();
}});

}

/*
* Cambia el panel.
*/

@FXML
void Boton_tabla(ActionEvent event) {
try {
     // #1 Carga el nuevo FXML
     FXMLLoader loader = new FXMLLoader(getClass().getResource("Tabla.fxml"));

     // #2 Pasa el FXML a la raiz
     Parent nuevoContenido = loader.load();

     // #3 Reemplaza el contenido actual en el AnchorPane
     contenidoPane.getChildren().setAll(nuevoContenido);

} catch (Exception e) {
     e.printStackTrace();
}

}

/*
* Obtener una conexion a la base de datos.
*/

public Connection obtenerConexionBaseDatos() {

// #1 Establece una conexion nula
Connection conexion = null;

try {
     // #2 Carga el controlador JDBC
     Class.forName("com.mysql.cj.jdbc.Driver");

     // #3 Se almacena la informacion para establecer una conexion con la base de datos
     String url = "jdbc:mysql://localhost:3306/arkane_database";
     String usuario = "root";
     String contraseña = "Root1234";

     // #4 Establece la conexión
     conexion = DriverManager.getConnection(url, usuario, contraseña);

} catch (Exception e) {
     e.printStackTrace();
     System.err.println("Error al conectarse.");
}

// #5 Retorna la conexion
return conexion;

}
    
}