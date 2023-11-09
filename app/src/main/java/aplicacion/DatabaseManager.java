package aplicacion;

     import java.sql.Connection;
     import java.sql.DriverManager;
     import java.sql.PreparedStatement;
     import java.sql.ResultSet;
     import java.sql.SQLException;

/*
 
Clase de manejo de bases de datos

*/

public class DatabaseManager {
     
// Declara un objeto de tipo connection
private Connection connection;

/*
* Inicializa el objeto para gestionar la base de datos.
*/

public DatabaseManager() {

try {

// Declaracion de variables con la informacion de mysql
String url = "jdbc:mysql://localhost:3306/arkane_database";
String user = "root";
String password = "Root1234";

// Inicializa la conexión en el constructor
connection = DriverManager.getConnection(url, user, password);

} catch (SQLException e) {
e.printStackTrace();
}

}

/**
* 
* Verificacion de usuario en la base de datos.
*
* @param username   Nombre.
* @param password   Contraseña.
* @param query      Consulta SQL.
*
* @return esto se retorna dependiendo de si se encuentra algun resultado.
* 
*/

public boolean authenticateUser(String username, String password) {
// Declara una variable con una query
String query = "SELECT * FROM arkane_usuarios WHERE usuario = ? AND pass = ?";

try {

// #1 Prepara una declaración SQL con la query.
PreparedStatement statement = connection.prepareStatement(query);

// #2 Se declaran las variables de usuario y contraseña.
statement.setString(1, username);
statement.setString(2, password);

// #3 Se almacena el resultado de la query.
ResultSet resultSet = statement.executeQuery();

// #4 Devuelve true si hay al menos un resultado.
return resultSet.next();

} catch (SQLException e) {
e.printStackTrace();

// #5 Maneja excepciones de mysql y pasa falso en caso de no encontrar nada.
return false;

}

}

/**
* 
* Registro de usuario en la base de datos.
*
* @param dni 
* @param nombre
* @param usuario
* @param contraseña
*
* @return True si el registro se realizó con éxito; de lo contrario, false.
*
*/

public boolean registrarUsuario(String dni, String nombre, String usuario, String contraseña) {
// #1 Se declara la query
String insertQuery = "INSERT INTO arkane_usuarios (nombre, dni, pass, usuario) VALUES (?, ?, ?, ?)";
        
try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

// #2 Establece los valores de los parámetros en la consulta preparada.
preparedStatement.setString(1, nombre);
preparedStatement.setString(2, dni);
preparedStatement.setString(3, contraseña);
preparedStatement.setString(4, usuario);

// #3 Ejecuta la consulta de registro y recibe las nuevas
int rowsAffected = preparedStatement.executeUpdate();

// #4 Devuelve true si son mas de 0 las filas afectadas.
return rowsAffected > 0; 

} catch (SQLException e) {
e.printStackTrace();

// #5 Si da error por el registro retorna falso.
return false;

}

}

/*
* Cierra la conexion
*/

public void closeConnection() {
try {
// #1 Cierra la conexion
connection.close();

} catch (SQLException e) {
e.printStackTrace();
}

}

}