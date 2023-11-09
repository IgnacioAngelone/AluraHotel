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

/*

Clase de controlador para el registro.

*/

public class RegistroController {

// Objeto de la clase para manipular la base de datos

private DatabaseManager databaseManager; 

// Imágenes de la interfaz gráfica

@FXML
private ImageView Background;
@FXML
private ImageView Logo;
@FXML
private ImageView Typo;

// Formulario de la interfaz gráfica

@FXML
private TextField Usuario;
@FXML
private TextField Nombre;
@FXML
private TextField Dni;
@FXML
private PasswordField Contraseña;
   
// Botones de la interfaz grafica

@FXML
private Button Boton;

/*
* Inicializa el objeto para gestionar la base de datos.
*/

public RegistroController() {
     databaseManager = new DatabaseManager();
}

/*
* Inicializa el controlador.
*/

public void initialize() {

// Carga todas las imagenes
Image imagenBackground = new Image(getClass().getResource("/RegistroBackground.jpg").toExternalForm());
Image imagenLogo = new Image(getClass().getResource("/Logo-removebg-preview.png").toExternalForm());
Image imagenTypo = new Image(getClass().getResource("/Typo.jpg").toExternalForm());

// Asigna las imagenes a los componentes
Typo.setImage(imagenTypo);
Background.setImage(imagenBackground);
Logo.setImage(imagenLogo);

}

/**
* 
* Maneja el evento de registro de usuario.
*
* @param event es el evento de clic en el botón.
*
*/

@FXML
void Registrarse(ActionEvent event) {

// #1 Recibe los valores ingresados en los campos
String usuario = Usuario.getText();

String nombre = Nombre.getText();

String dni = Dni.getText();

String contraseña = Contraseña.getText();
    
// #2 Llama al método de databasemanager para los registros
boolean registroExitoso = databaseManager.registrarUsuario(dni, nombre, usuario, contraseña);

// #3 inicializa una alerta por si el registro es correcto o incorrecto
Alert alert = new Alert(AlertType.INFORMATION);
alert.initOwner(Boton.getScene().getWindow());

if (registroExitoso) {
System.out.println("Registro finalizado");

     // #4.1 Cambia el texto de la alerta
     alert.setTitle("Registro finalizado");

     // #4.2 Se cambia el header de la alerta
     alert.setHeaderText("Usuario creado.");
        
     // #4.3 Toma el boton de la escena
     Stage stage = (Stage) Boton.getScene().getWindow();

     // #4.4 Cierra la escena
     stage.close();

} else {
System.out.println("Error al registrarse");

     // #4.1 Se cambia el tipo de la alerta de informativo a error
     alert.setAlertType(AlertType.ERROR);

     // #4.2 Cambia el texto de la alerta
     alert.setTitle("Error al registrarse");
     
     // #4.3 Cambia el header de la alerta
     alert.setHeaderText("No se creo el usuario.");
}

// #5 La alerta se mantiene y espera al usuario para cerrarla
alert.showAndWait();

}

} // Finaliza el controlador de registro