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

public class Arranque {

    @FXML
    private ImageView Back;
    @FXML
    private Hyperlink Hyper;
    @FXML
    private Button Ingresar;
    @FXML
    private ImageView Logo;
    @FXML
    private Pane Panel;
    @FXML
    private PasswordField Password;
    @FXML
    private Button Registro;
    @FXML
    private ImageView Typo;
    @FXML
    private TextField Usuario;

    private DatabaseManager databaseManager;

    public Arranque() {
        // Inicializa la instancia de DatabaseManager en el constructor
        databaseManager = new DatabaseManager();
    }

    
public void initialize() {

// Cargar las imágenes
    Image imagenBack = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/Background.jpg");
    Image imagenTypo = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/Typo.jpg");
    Image imagenLogo = new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/Logo-removebg-preview.png");

// Las muestra
    Back.setImage(imagenBack);
    Typo.setImage(imagenTypo);
    Logo.setImage(imagenLogo);

}

@FXML
void Registrarse(ActionEvent event) {
    try {
        // Cargar el nuevo archivo FXML para la segunda ventana
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Registro.fxml"));
        Parent root = loader.load();

        // Crear un nuevo escenario para la segunda ventana
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.getIcons().add(new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/2ico.png"));


        // Configurar el escenario (tamaño, título, etc.)
        stage.setTitle("REGISTRARSE");
        stage.setScene(scene);

        // Mostrar la segunda ventana
        stage.show();
    } catch(IOException e){
        e.printStackTrace();
    }
}


@FXML
void Ingreso(ActionEvent event) throws IOException {

        // Obtén el usuario y la contraseña ingresados por el usuario
        String username = Usuario.getText();
        String password = Password.getText();

        if (databaseManager.authenticateUser(username, password)) {
            System.err.println("Bien ahi");
            Ingresar.setText("OK"); // Cambia el texto del botón a "OK"
            // El usuario se autenticó con éxito
            // Aquí puedes realizar la lógica adicional, como abrir una nueva ventana
                    // Abre una nueva ventana
            abrirNuevaVentana();
        // Cierra la ventana actual
            cerrarVentanaActual();

        } else {
            System.err.println("Mal ahi");
            Ingresar.setText("ERROR");
            // Las credenciales son incorrectas, puedes mostrar un mensaje de error
        }
}

private void abrirNuevaVentana() throws IOException {
    // Crea una nueva ventana
    Stage nuevaVentana = new Stage();
    // Carga el nuevo FXML para la nueva ventana
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Reservas.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);

    nuevaVentana.getIcons().add(new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/hogar.png"));


    // Configura la nueva ventana y muestra
    nuevaVentana.setScene(scene);
    nuevaVentana.show();
}

private void cerrarVentanaActual() {
    // Obtiene la referencia de la escena actual y cierra la ventana
    Scene escenaActual = Ingresar.getScene();
    Stage ventanaActual = (Stage) escenaActual.getWindow();
    ventanaActual.close();
}




}




