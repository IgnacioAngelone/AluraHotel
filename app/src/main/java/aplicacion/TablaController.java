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

public class TablaController {

    @FXML
    private Pane AnchorPane;

    @FXML
    private ImageView Background;

    @FXML
    private TextField Buscar;

    @FXML
    private Button Home_boton;

    @FXML
    private ImageView Homeloguito;

    @FXML
    private Hyperlink HyperFrecuentes;

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
    @FXML
    private ImageView Datos;

    @FXML
    private AnchorPane segundocontenidoPane;

    @FXML
    private ImageView typo;

    @FXML
    void Filtrar(ActionEvent event) {
        String textoBuscado = Buscar.getText().toLowerCase(); // Obtén el texto ingresado en minúsculas
    
        // Verifica si el campo de búsqueda está vacío
        if (textoBuscado.isEmpty()) {
            cargarDatosEnTabla(); // Cargar todos los datos nuevamente
        } else {
            // Crear una lista observable para almacenar las reservas filtradas
            ObservableList<Reserva> reservasFiltradas = FXCollections.observableArrayList();
    
            // Iterar a través de las reservas en la tabla
            for (Reserva reserva : tabla.getItems()) {
                // Verifica si el nombre de la reserva contiene el texto buscado (en minúsculas)
                if (reserva.getNombre().toLowerCase().contains(textoBuscado)) {
                    reservasFiltradas.add(reserva); // Agrega la reserva a la lista filtrada
                }
            }
    
            // Actualiza la tabla con las reservas filtradas
            tabla.setItems(reservasFiltradas);
        }
    }
    
    

    public void initialize() {
        // Cargar y mostrar las imágenes en el controlador de registro (ajusta las rutas)
        Image imagenBackground = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/Reservas_Background.jpg");
        Image imagenLogo = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/usuarios.png");
        Image imagenTypo = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/Typo.jpg");
        Image imagendata = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/java.png");

        typo.setImage(imagenTypo);
        Homeloguito.setImage(imagenLogo);
        Background.setImage(imagenBackground);
        Datos.setImage(imagendata);


        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
        fechaSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        metodoPago.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));

        tabla.setEditable(true);


        // Desactivar la edición y ordenación de las columnas
        id.setEditable(false);
        nombre.setEditable(true);
        fechaIngreso.setEditable(true);
        fechaSalida.setEditable(true);
        precio.setEditable(true);
        metodoPago.setEditable(true);
        fechas.setEditable(false);

        // Desactivar la redimensión de las columnas
        id.setResizable(false);
        nombre.setResizable(false);
        fechaIngreso.setResizable(false);
        fechaSalida.setResizable(false);
        precio.setResizable(false);
        metodoPago.setResizable(false);
        fechas.setResizable(false);

        cargarDatosEnTabla();

        // Establecer el evento para el botón Filtrar
        Buscar.setOnAction(event -> Filtrar(new ActionEvent()));

        Buscar.setOnKeyPressed(event -> {
             if (event.getCode() == KeyCode.ENTER) {
                  Filtrar(new ActionEvent());
             }
        });

        nombre.setCellFactory(TextFieldTableCell.forTableColumn());
        fechaIngreso.setCellFactory(TextFieldTableCell.forTableColumn());
        fechaSalida.setCellFactory(TextFieldTableCell.forTableColumn());
        precio.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        metodoPago.setCellFactory(TextFieldTableCell.forTableColumn());
        
    
        setCellEditCommitHandlers();

    
    }

    @FXML
    void Regresar(ActionEvent event) {
        try {
            // Carga el nuevo FXML para la vista anterior (ajusta la ruta al FXML que deseas cargar)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Reservas.fxml"));
            Parent nuevoContenido = loader.load();

            // Reemplaza el contenido actual en el AnchorPane
            segundocontenidoPane.getChildren().setAll(nuevoContenido);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosEnTabla() {
        try {
            Connection conn = obtenerConexionBaseDatos();

            if (conn != null) {
                // Consulta SQL para seleccionar todos los datos de las reservas
                String sql = "SELECT * FROM arkane_reservas";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                // Crear una lista observable para almacenar las reservas
                ObservableList<Reserva> reservas = FXCollections.observableArrayList();

                // Iterar a través de los resultados y agregarlos a la lista
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombreReserva = rs.getString("nombre");
                    String fechaIngresoReserva = rs.getString("fecha_ingreso");
                    String fechaSalidaReserva = rs.getString("fecha_salida");
                    double precioReserva = rs.getDouble("precio");
                    String metodoPagoReserva = rs.getString("metodo_pago");

                    Reserva reserva = new Reserva(id, nombreReserva, fechaIngresoReserva, fechaSalidaReserva, precioReserva, metodoPagoReserva);
                    reservas.add(reserva);
                }

                // Agregar las reservas a la tabla
                tabla.setItems(reservas);

                // Cerrar la conexión
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    private void setCellEditCommitHandlers() {
        nombre.setOnEditCommit(event -> updateDatabaseAndRefreshTable(event));
        fechaIngreso.setOnEditCommit(event -> updateDatabaseAndRefreshTable(event));
        fechaSalida.setOnEditCommit(event -> updateDatabaseAndRefreshTable(event));
        precio.setOnEditCommit(event -> updateDatabaseAndRefreshTable(event));
        metodoPago.setOnEditCommit(event -> updateDatabaseAndRefreshTable(event));
    }

    private void updateDatabaseAndRefreshTable(TableColumn.CellEditEvent<Reserva, ?> event) {
        try {
            Reserva reserva = event.getRowValue();
            String columnName = event.getTableColumn().getId();
            Object newValue = event.getNewValue();

            // Validación de entrada (puedes agregar más validaciones según tus requisitos)

            if (newValue == null) {
                return; // No actualizar si el nuevo valor es nulo
            }

            Connection conn = obtenerConexionBaseDatos();

            if (conn != null) {
                String sql = "UPDATE arkane_reservas SET " + columnName + " = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                if (newValue instanceof String) {
                    stmt.setString(1, (String) newValue);
                } else if (newValue instanceof Double) {
                    stmt.setDouble(1, (Double) newValue);
                }

                stmt.setInt(2, reserva.getId());
                stmt.executeUpdate();

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Recargar datos en la tabla para reflejar los cambios en la interfaz de usuario
        cargarDatosEnTabla();
    }

}