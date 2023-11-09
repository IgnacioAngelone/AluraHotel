package aplicacion;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.image.Image;
        import javafx.stage.Stage;

/**
* 
* Clase principal de la aplicación que extiende de Application de JavaFX.
* 
*/

public class Main extends Application { 

public static void main(String[] args) { 
       launch(args);
}

/**
* 
* Este método "start" se llama cuando se inicia JavaFX.
* 
* @param primaryStage es el escenario principal de la aplicación.
* 
* @throws Exception por si ocurre una excepción.
* 
*/

@Override
public void start(Stage primaryStage) throws Exception { 

// #1 Crea un objeto FXML busca en el paquete actual con getclass y obtiene el recurso FXML
FXMLLoader loader = new FXMLLoader(getClass().getResource("Principal.fxml"));

       // 2# Le pasa el archivo a la raiz
       Parent root = loader.load(); 

       // #3 Establece el tamaño de la raiz ("root") 
       Scene scene = new Scene(root, 1280, 800); 
    
// #4 Agrega un icono a la ventana
primaryStage.getIcons().add(new Image(getClass().getResource("/ico.png").toExternalForm()));
// #5 Establece el titulo de la ventana
primaryStage.setTitle("ARKANE INICIO");
// #6 Asigna la escena a la ventana
primaryStage.setScene(scene);
// #7 Muestra la ventana
primaryStage.show();

}

} // Finaliza la clase principal
