package aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Esto carga el FXML desde la ubicación completa
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Principal.fxml"));
    
        // Esto define la raíz
        Parent root = loader.load();
    
        // Agregar el estilo a la escena después de crear la variable root
        Scene scene = new Scene(root, 1280, 800);
    
        primaryStage.getIcons().add(new Image("file:C:/Users/iange/OneDrive/Documentos/ProyectoHotel/app/src/main/resources/ico.png"));
    
        // Con esto se muestra
        primaryStage.setTitle("HOTEL ARKANE");
    
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
}
