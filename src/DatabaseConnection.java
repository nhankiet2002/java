import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/stationmanagerdb"; // CSDL trên XAMPP
    private static final String USER = "root";  // Mặc định là 'root'
    private static final String PASSWORD = "";  // Mặc định mật khẩu trống

    public static Connection getConnection() throws SQLException {
        try {
            // Kiểm tra và tải driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");  
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver không được tìm thấy.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
