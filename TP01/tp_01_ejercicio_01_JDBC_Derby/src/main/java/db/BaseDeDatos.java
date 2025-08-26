package db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BaseDeDatos {
    public static void main(String[] args) throws SQLException {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException
                | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String uri = "jdbc:derby:MyDerbyDB;create=true";
        try {
            Connection conn = DriverManager.getConnection(uri);
            createTable(conn);
            addPerson(conn, 1, "juan", 20);
            addPerson(conn, 2, "paula", 30);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addPerson(Connection conn, int id, String name, int years) throws SQLException {
        String insert = "INSERT INTO persona (id, nombre, edad) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, years);
        ps.execute();
        ps.close();
        conn.commit();
    }

    private static void createTable(Connection conn) throws SQLException {
        String table = "CREATE TABLE persona (" +
                "id INT PRIMARY KEY, " +
                "nombre VARCHAR(500), " +
                "edad INT)";

        // Si querés manejar commit manual:
        // conn.setAutoCommit(false);

        try (var stmt = conn.prepareStatement(table)) {
            stmt.executeUpdate();
        }

        // Solo hacer commit si desactivaste autocommit
        conn.commit();
    }

}
