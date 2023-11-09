package aplicacion;

     import java.sql.Connection;
     import java.sql.DriverManager;
     import java.sql.PreparedStatement;
     import java.sql.ResultSet;
     import java.sql.Statement;
     import javafx.collections.FXCollections;
     import javafx.collections.ObservableList;
     import javafx.event.ActionEvent;
     import javafx.fxml.FXML;
     import javafx.fxml.FXMLLoader;
     import javafx.scene.Parent;
     import javafx.scene.control.Button;
     import javafx.scene.control.Hyperlink;
     import javafx.scene.control.TableColumn;
     import javafx.scene.control.TableView;
     import javafx.scene.control.TextField;
     import javafx.scene.control.cell.PropertyValueFactory;
     import javafx.scene.control.cell.TextFieldTableCell;
     import javafx.scene.image.Image;
     import javafx.scene.image.ImageView;
     import javafx.scene.input.KeyCode;
     import javafx.scene.layout.AnchorPane;
     import javafx.scene.layout.Pane;
     import javafx.util.converter.DoubleStringConverter;

/*

Clase de controlador para la tabla.

*/

public class TablaController {

// Imagenes de la interfaz grafica

@FXML
private ImageView Homeloguito;

@FXML
private ImageView Background;

@FXML
private ImageView Datos;

@FXML
private ImageView typo;

// Tabla de la interfaz gráfica

@FXML
private TextField Buscar;

@FXML
private TableView<Reserva> tabla;

@FXML
private TableColumn<?, ?> fechas;

@FXML
private TableColumn<Reserva, String> id;

@FXML
private TableColumn<Reserva, String> nombre;

@FXML
private TableColumn<Reserva, String> fechaIngreso;

@FXML
private TableColumn<Reserva, String> fechaSalida;

@FXML
private TableColumn<Reserva, Double> precio;

@FXML
private TableColumn<Reserva, String> metodoPago;

// Panel de la interfaz gráfica

@FXML
private Pane AnchorPane;

@FXML
private AnchorPane segundocontenidoPane;

// Botones de la interfaz gráfica

@FXML
private Button Home_boton;

// Links de la interfaz gráfica

@FXML
private Hyperlink HyperFrecuentes;

/*
* Inicializa el objeto para gestionar la base de datos.
*/

@FXML
void Filtrar(ActionEvent event) {

// #1 Se convierte el texto a minus
String textoBuscado = Buscar.getText().toLowerCase(); 
    
// #2 Verifica si el campo de búsqueda está vacío
if (textoBuscado.isEmpty()) {

     // #3.1 Cargar todos los datos si esta vacio el filtro
     cargarDatosEnTabla(); 

} else {

// 3.1 Crear una lista observable para almacenar las reservas filtradas
ObservableList<Reserva> reservasFiltradas = FXCollections.observableArrayList();
    
// 3,2 Iterar las reservas en la tabla
for (Reserva reserva : tabla.getItems()) {

if (reserva.getNombre().toLowerCase().contains(textoBuscado)) {

// Agrega la reserva a la lista filtrada
reservasFiltradas.add(reserva); 

}

}
    
// Actualiza la tabla con las reservas filtradas
tabla.setItems(reservasFiltradas);

}

}
    
/*
* Inicializa el objeto para gestionar la base de datos.
*/

public void initialize() {

// #1 Carga todas las imagenes
Image imagenBackground = new Image(getClass().getResource("/Reservas_Background.jpg").toExternalForm());
Image imagenLogo = new Image(getClass().getResource("/usuarios.png").toExternalForm());
Image imagenTypo = new Image(getClass().getResource("/Typo.jpg").toExternalForm());
Image imagendata = new Image(getClass().getResource("/java.png").toExternalForm());

// #2 Asigna las imagenes a los componentes
typo.setImage(imagenTypo);
Homeloguito.setImage(imagenLogo);
Background.setImage(imagenBackground);
Datos.setImage(imagendata);

// #3 Configura las columnas de la tabla
id.setCellValueFactory(new PropertyValueFactory<>("id"));
nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
fechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
fechaSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
metodoPago.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));

// #4 La hace editable
tabla.setEditable(true);

// #5 Desactivar la edición y ordenación de las columnas
id.setEditable(false);
nombre.setEditable(true);
fechaIngreso.setEditable(true);
fechaSalida.setEditable(true);
precio.setEditable(true);
metodoPago.setEditable(true);
fechas.setEditable(false);

// #6 Desactivar la redimensión de las columnas
id.setResizable(false);
nombre.setResizable(false);
fechaIngreso.setResizable(false);
fechaSalida.setResizable(false);
precio.setResizable(false);
metodoPago.setResizable(false);
fechas.setResizable(false);

// #7 Carga los datos en las tablas
cargarDatosEnTabla();

// #8 Establecer el evento para el botón Filtrar
Buscar.setOnAction(event -> Filtrar(new ActionEvent()));

Buscar.setOnKeyPressed(event -> {
if (event.getCode() == KeyCode.ENTER) {
Filtrar(new ActionEvent());
}});

// #9 Configura las celdas de las columnas para edición
nombre.setCellFactory(TextFieldTableCell.forTableColumn());
fechaIngreso.setCellFactory(TextFieldTableCell.forTableColumn());
fechaSalida.setCellFactory(TextFieldTableCell.forTableColumn());
precio.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
metodoPago.setCellFactory(TextFieldTableCell.forTableColumn());
        
// #10 Maneja  la edición de celdas
setCellEditCommitHandlers();

}

/*
* Vuelve a la pestaña de registro.
*/

@FXML
void Regresar(ActionEvent event) {
try {

// #1 Carga el nuevo FXML para la vista anterior
FXMLLoader loader = new FXMLLoader(getClass().getResource("Reservas.fxml"));
Parent nuevoContenido = loader.load();

// #2 Reemplaza el contenido actual en el AnchorPane
segundocontenidoPane.getChildren().setAll(nuevoContenido);

} catch (Exception e) {
e.printStackTrace();
}

}

/*
* Carga los datos en la tabla.
*/

private void cargarDatosEnTabla() {

try {
// #1 Obtiene la conexion a la base de datos
Connection conn = obtenerConexionBaseDatos();

if (conn != null) {

// #2 Si la conexion no es nula se almacena esta query "sql"
String sql = "SELECT * FROM arkane_reservas";

// #3 Se prepara un estado para ejecutar una query
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);

// #4 Crear una lista para almacenar las reservas
ObservableList<Reserva> reservas = FXCollections.observableArrayList();

// #5 Iterar los resultados y los agrega a la lista
while (rs.next()) {
int id = rs.getInt("id");
String nombreReserva = rs.getString("nombre");
String fechaIngresoReserva = rs.getString("fecha_ingreso");
String fechaSalidaReserva = rs.getString("fecha_salida");
double precioReserva = rs.getDouble("precio");
String metodoPagoReserva = rs.getString("metodo_pago");

// #6 Se crea con el constructor un objeto de tipo reserva y se añade a la lista
Reserva reserva = new Reserva(id, nombreReserva, fechaIngresoReserva, fechaSalidaReserva, precioReserva, metodoPagoReserva);
reservas.add(reserva);

}

// #7 Agrega las reservas a la tabla
tabla.setItems(reservas);

// #8 Cierra la conexión
conn.close();

}} catch (Exception e) {
e.printStackTrace();
}

}

/*
* Carga los datos en la tabla.
*/

public Connection obtenerConexionBaseDatos() {

// #1 Establece la conexion
Connection conexion = null;

try {
// #2 Carga el controlador JDBC de MySQL (asegúrate de que el archivo JAR esté en tu proyecto)
Class.forName("com.mysql.cj.jdbc.Driver");

// #3 Almacena los datos para la conexion
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

/*
*
* Maneja la edicion de las celdas, ademas actualiza/reinicia la tabla en la base de datos.
*
*/

private void setCellEditCommitHandlers() {

// Establece un manejador para la edición de la celda 'nombre'
nombre.setOnEditCommit(event -> updateDatabaseAndRefreshTable(event));

// Establece un manejador para la edición de la celda 'fecha de ingreso'
fechaIngreso.setOnEditCommit(event -> updateDatabaseAndRefreshTable(event));

// Establece un manejador para la edición de la celda 'fecha de salida'
fechaSalida.setOnEditCommit(event -> updateDatabaseAndRefreshTable(event));

// Establece un manejador para la edición de la celda 'precio'
precio.setOnEditCommit(event -> updateDatabaseAndRefreshTable(event));

// Establece un manejador para la edición de la celda 'método de pago'
metodoPago.setOnEditCommit(event -> updateDatabaseAndRefreshTable(event));

}

/**
 * 
 * Actualiza la base de datos con el nuevo valor editado en una celda y refleja los cambios.
 * 
 * @param event Un evento de edición de celda.
 * 
 */

private void updateDatabaseAndRefreshTable(TableColumn.CellEditEvent<Reserva, ?> event) {
try {

// #1 Obtiene el anterior valor de la celda editada 
Reserva reserva = event.getRowValue(); 

// #2 Obtiene el nombre de la columna 
String columnName = event.getTableColumn().getId(); 

// #3 Obtiene el nuevo valor editado en la celda
Object newValue = event.getNewValue(); 

// #4 Se valida que el nuevo valor no este vacio
if (newValue == null) {
     return;
}

// #5 Obtiene una conexión a la base de datos
Connection conn = obtenerConexionBaseDatos(); 

if (conn != null) {

// #6 Se crea la consulta junto al nombre de la columna
String sql = "UPDATE arkane_reservas SET " + columnName + " = ? WHERE id = ?";

// #7 Se prepara la query
PreparedStatement stmt = conn.prepareStatement(sql);

// Configura el valor de la columna en query
if (newValue instanceof String) {

     // #8.1 Puede ser un valor de tipo string
     stmt.setString(1, (String) newValue);

} else if (newValue instanceof Double) {

     // #8.1 Puede ser un valor con coma
     stmt.setDouble(1, (Double) newValue);

}

// #9 Establece el ID de la reserva en la consulta
stmt.setInt(2, reserva.getId()); 

// #10 Ejecuta la consulta de actualización
stmt.executeUpdate(); 

// #11 Cierra la conexión
conn.close(); 

}} catch (Exception e) {
e.printStackTrace();
}

// #12 Refleja los cambios
cargarDatosEnTabla(); 

}

}