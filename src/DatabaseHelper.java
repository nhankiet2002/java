import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/StationManagerDB";
    private static final String USER = "root";  // Thay thế bằng tên người dùng của bạn
    private static final String PASSWORD = "";  // Thay thế bằng mật khẩu của bạn

    // Phương thức kết nối với MySQL
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Phương thức để thêm trạm vào CSDL
    public static void addStation(String type, String name, String location, int number) throws SQLException {
        String query = "INSERT INTO stations (type, name, location, number) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, type);
            pstmt.setString(2, name);
            pstmt.setString(3, location);
            pstmt.setInt(4, number);
            pstmt.executeUpdate();
        }
    }

    // Phương thức để tải tất cả trạm từ CSDL
    public static ResultSet loadStations() throws SQLException {
        String query = "SELECT * FROM stations";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    // Phương thức để cập nhật trạm trong CSDL
    public static void updateStation(int id, String type, String name, String location, int number) throws SQLException {
        String query = "UPDATE stations SET type = ?, name = ?, location = ?, number = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, type);
            pstmt.setString(2, name);
            pstmt.setString(3, location);
            pstmt.setInt(4, number);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
    }

    // Phương thức để xóa trạm khỏi CSDL
    public static void deleteStation(int id) throws SQLException {
        String query = "DELETE FROM stations WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Kết nối thành công!");
            } else {
                System.out.println("Kết nối thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
