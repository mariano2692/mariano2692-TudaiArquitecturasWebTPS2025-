package db;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Select {
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
            String select = "SELECT * FROM persona";
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getInt(3));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
