package aplicacion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RegistroController {

    @FXML
    private ImageView Background;

    @FXML
    private PasswordField Contraseña;

    @FXML
    private TextField Dni;

    @FXML
    private ImageView Logo;

    @FXML
    private TextField Nombre;

    @FXML
    private ImageView Typo;

    @FXML
    private TextField Usuario;

    @FXML
    private Button Boton;

    private DatabaseManager databaseManager;

    public RegistroController() {
        // Inicializa la instancia de DatabaseManager en el constructor
        databaseManager = new DatabaseManager();
    }

public void initialize() {
        // Cargar y mostrar las imágenes en el controlador de registro (ajusta las rutas)
        Image imagenBackground = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/RegistroBackground.jpg");
        Image imagenLogo = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/Logo-removebg-preview.png");
        Image imagenTypo = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/Typo.jpg");

        Typo.setImage(imagenTypo);
        Background.setImage(imagenBackground);
        Logo.setImage(imagenLogo);

    }

    @FXML
    void Registrar(ActionEvent event) {
        // Obtiene los valores de los campos del formulario
        String dni = Dni.getText();
        String nombre = Nombre.getText();
        String usuario = Usuario.getText();
        String contraseña = Contraseña.getText();

        // Llama al método para insertar el nuevo usuario en la base de datos
        boolean registroExitoso = databaseManager.registrarUsuario(dni, nombre, usuario, contraseña);

        if (registroExitoso) {
            System.out.println("RegistroExitoso");
        } else {
            System.out.println("RegistroFallido");

        }
    }

    @FXML
    void Registrarse(ActionEvent event) {
        // Obtén los valores de los campos del formulario
        String dni = Dni.getText();
        String nombre = Nombre.getText();
        String usuario = Usuario.getText();
        String contraseña = Contraseña.getText();
    
        // Llama al método para insertar el nuevo usuario en la base de datos
        boolean registroExitoso = databaseManager.registrarUsuario(dni, nombre, usuario, contraseña);
    
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(Boton.getScene().getWindow());


        if (registroExitoso) {
            System.out.println("Registro Exitoso");
            // Realiza cualquier acción adicional que desees después del registro
            alert.setTitle("Registro Exitoso");
            alert.setHeaderText("Usuario creado con éxito.");

             Stage stage = (Stage) Boton.getScene().getWindow();
             stage.close();
            
        } else {
            System.out.println("Registro Fallido");
            alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Registro Fallido");
            alert.setHeaderText("No se pudo crear el usuario.");
            // Muestra un mensaje de error al usuario si el registro falla
        }

        alert.showAndWait();

    }
    
}
