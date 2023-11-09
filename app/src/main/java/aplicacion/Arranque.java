package aplicacion;

      import java.io.IOException;
      import javafx.event.ActionEvent;
      import javafx.fxml.FXML;
      import javafx.fxml.FXMLLoader;
      import javafx.scene.Parent;
      import javafx.scene.Scene;
      import javafx.scene.control.Button;
      import javafx.scene.control.Hyperlink;
      import javafx.scene.control.PasswordField;
      import javafx.scene.control.TextField;
      import javafx.scene.image.Image;
      import javafx.scene.image.ImageView;
      import javafx.scene.layout.Pane;
      import javafx.stage.Stage;

/*
 
Clase de controlador principal

*/

public class Arranque {

// Objeto de la clase para manipular la base de datos
private DatabaseManager databaseManager; 

// Imágenes de la interfaz gráfica

@FXML
private ImageView Back;
@FXML
private ImageView Logo;
@FXML
private ImageView Typo;

// Formulario de la interfaz gráfica

@FXML
private TextField Usuario;
@FXML
private PasswordField Password;

// Botones de la interfaz grafica

@FXML
private Button Registro;

@FXML
private Button Ingresar;

// Hyperlinks

@FXML
private Hyperlink Hyper;

// Panel

@FXML
private Pane Panel;

/*
* Inicializa el objeto para gestionar la base de datos.
*/

public Arranque() {
databaseManager = new DatabaseManager();
}

/*
* Inicializa el controlador.
*/

public void initialize() {

// Carga todas las imagenes
Image imagenBack = new Image(getClass().getResource("/Background.jpg").toExternalForm());
Image imagenTypo = new Image(getClass().getResource("/Typo.jpg").toExternalForm());
Image imagenLogo = new Image(getClass().getResource("/Logo-removebg-preview.png").toExternalForm());

// Asigna las imagenes a los componentes
Back.setImage(imagenBack);
Typo.setImage(imagenTypo);
Logo.setImage(imagenLogo);

}

/**
* 
* Maneja el evento de registro del usuario.
*
* @param event es el evento de clic en el botón.
*
*/

@FXML
void Registrarse(ActionEvent event) {

try {

// #1 Carga un nuevo FXML en este caso el de registro
FXMLLoader loader = new FXMLLoader(getClass().getResource("Registro.fxml"));
Parent root = loader.load(); // Le pasa el FXML a la raiz

     // #2 Crear una nueva ventana
     Stage stage = new Stage();
     Scene scene = new Scene(root);

// #3 Agrega un icono para lan ueva ventana   
stage.getIcons().add(new Image(getClass().getResource("/2ico.png").toExternalForm()));

// #4 Agrega un titulo a la ventana
stage.setTitle("REGISTRARSE");

// #5 Asigna la escena a la ventana
stage.setScene(scene);

// #6 Mostrar la segunda ventana
stage.show();

}  catch(IOException e) {
     // Maneja la excepción en caso de error.
     e.printStackTrace();
}

}

/**
* 
* Maneja el evento de ingreso del usuario.
*
* @param event es el evento de clic en el botón.
*
*/

@FXML
void Ingreso(ActionEvent event) throws IOException {

// #1 Obtiene los datos ingresados
String username = Usuario.getText();
String password = Password.getText();

if (databaseManager.authenticateUser(username, password)) {
System.err.println("Usuario correcto");

      // #2.1 Cambia el texto del botón
      Ingresar.setText("BIENVENIDO"); 
                    
      // #2.2 Abre una nueva ventana
      abrirNuevaVentana();

      // #2.3 Cierra la ventana actual
      cerrarVentanaActual();

} else {
System.err.println("Usuario incorrecto");

      // #2.1 Cambia el texto del boton
      Ingresar.setText("INTENTA OTRA VEZ");
}

}

/*
* Abre una nueva ventana.
*/

private void abrirNuevaVentana() throws IOException {
      
// #1 Crea una nueva ventana 
Stage nuevaVentana = new Stage();

// #2 Carga el nuevo FXML para la nueva ventana
FXMLLoader loader = new FXMLLoader(getClass().getResource("Reservas.fxml"));

     // #3 Le pasa el archivo a la raiz
     Parent root = loader.load();

     // #4 Establece la escena 
     Scene scene = new Scene(root);

// #5 Agrega un icono a la ventana
nuevaVentana.getIcons().add(new Image(getClass().getResource("/hogar.png").toExternalForm()));

// #6 Asigna la escena a la ventana
nuevaVentana.setScene(scene);

// #7 Muestra la ventana
nuevaVentana.show();

}

/*
* Cierra una ventana.
*/

private void cerrarVentanaActual() {

// #1 Obtiene la referencia de la escena actual
Scene escenaActual = Ingresar.getScene();
Stage ventanaActual = (Stage) escenaActual.getWindow();

// #2 Cierra la ventana
ventanaActual.close();

}

} // Finaliza el controlador principal