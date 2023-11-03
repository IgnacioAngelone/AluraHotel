package aplicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager() {
        // Inicializa la conexión en el constructor
        try {
            String url = "jdbc:mysql://localhost:3306/arkane_database";
            String user = "root";
            String password = "root1234";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String username, String password) {
        // Realiza la autenticación aquí
        String query = "SELECT * FROM arkane_usuarios WHERE usuarios = ? AND pass = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registrarUsuario(String dni, String nombre, String usuario, String contraseña) {
        String insertQuery = "INSERT INTO arkane_usuarios (nombre, dni, pass, usuarios) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, dni);
            preparedStatement.setString(3, contraseña);
            preparedStatement.setString(4, usuario);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Si rowsAffected es mayor que 0, la inserción se realizó con éxito.
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En caso de error, devuelve false
        }
    }



}
