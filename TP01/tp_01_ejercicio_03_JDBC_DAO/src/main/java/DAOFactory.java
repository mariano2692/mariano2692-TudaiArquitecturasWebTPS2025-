public abstract class DAOFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;


    public static DAOFactory getDAOFactory(int whichFactory) {
        return switch (whichFactory) {
            case MYSQL_JDBC -> new MySqlDAOFactory();
            case DERBY_JDBC -> new DerbyDAOFactory();
            default -> null;
        };
    }

    public abstract PersonaDAO getPersonaDAO();

}
