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

private void calcularPrecio() {

// #1 Fecha de ingreso
LocalDate ingreso = Ingreso.getValue();  

// #2 Fecha de salida
LocalDate salida = Salida.getValue();    

// #3 Verifica las fechas
if (ingreso != null && salida != null) {

     try {

         // #4.1 Calcula la diferencia en días entre la fecha de ingreso y salida
         long díasDeEstadía = ChronoUnit.DAYS.between(ingreso, salida);

         // #4.2 Calcula el precio total multiplicando los días por el precio por día predeterminado
         double precioTotal = díasDeEstadía * precioPorDiaPredeterminado;

     // #4.3 Muestra el precio total en el campo de precio
     Precio.setText(String.valueOf(precioTotal));

     } catch (NumberFormatException e) {
         e.printStackTrace();
         System.err.println("Error al calcular el precio.");
     }

} else {

// 5 En el caso de tener fechas nulas
System.err.println("Fechas de ingreso y salida válidas.");

}

}

/*
* Metodo para reservar.
*/

@FXML void Reservar(ActionEvent event) {

// #1 Se almacenan los datos ingresados
String precioPorDia = Precio.getText(); 
String ingresoStr = Ingreso.getValue().toString();
String salidaStr = Salida.getValue().toString();
    
     try {

// #2 Convierte las fechas a objetos LocalDate
LocalDate ingreso = LocalDate.parse(ingresoStr);
LocalDate salida = LocalDate.parse(salidaStr);
    
     // #3 Calcula la diferencia
     long díasDeEstadía = ChronoUnit.DAYS.between(ingreso, salida);
    
// #4 Convierte el precio por día a número
double precioPorDíaNum = Double.parseDouble(precioPorDia);
    
     // #5 Calcula el precio total
     double precioTotal = díasDeEstadía * precioPorDíaNum;
    
// #6 Se almacena el nombre y el metodo de pago
String nombreCliente = Nombre.getText();

String metodoDePago = Metodo_pago.getValue().toString();
    
// #7 Se obtiene la conexion a la base de datos
Connection conn = obtenerConexionBaseDatos();
    
// #8 Consulta mysql para insertar los valores
String sql = "INSERT INTO arkane_reservas (nombre, fecha_ingreso, fecha_salida, precio, metodo_pago) VALUES (?, ?, ?, ?, ?)";
    
// #9 Establece los valores de la consulta de mysql
PreparedStatement pstmt = conn.prepareStatement(sql);

pstmt.setString(1, nombreCliente);
pstmt.setString(2, ingresoStr);
pstmt.setString(3, salidaStr);
pstmt.setDouble(4, precioTotal);
pstmt.setString(5, metodoDePago);
    
// #10 Ejecuta la update a la base de datos
int filasAfectadas = pstmt.executeUpdate();
    
if (filasAfectadas > 0) {
    System.out.println("Registrado exitosamente");
} else {
    System.err.println("Error al registrar");
}
    
// #11 Cerrar la conexión
conn.close();
    
// #12 Muestra el precio total
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