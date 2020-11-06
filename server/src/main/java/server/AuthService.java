package server;

import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement getNickByLoginAndPass;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            prepareStatements();
            getNickByLoginAndPass.setString(1, login);
            getNickByLoginAndPass.setString(2, pass);
            ResultSet rs = getNickByLoginAndPass.executeQuery();
            if (rs.next()) {
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void prepareStatements() throws SQLException {
        getNickByLoginAndPass = connection.prepareStatement("SELECT nickname FROM client_info WHERE login = ? AND password = ?;");
    }
}
