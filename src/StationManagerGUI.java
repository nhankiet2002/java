import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationManagerGUI extends JFrame {
    // Các trường cho việc nhập dữ liệu và hiển thị danh sách
    private JTextField txtName, txtLocation, txtNumber;
    private JComboBox<String> cmbType;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Station> stationList = new ArrayList<>();

    // Constructor tạo giao diện
    public StationManagerGUI() {
        setTitle("Station Manager");  // Tiêu đề cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Đóng chương trình khi tắt cửa sổ
        setSize(800, 500);  // Kích thước cửa sổ
        setLocationRelativeTo(null);  // Đặt vị trí cửa sổ ở giữa màn hình
        
        // Tạo các thành phần giao diện nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        JLabel lblType = new JLabel("Loại trạm:");
        cmbType = new JComboBox<>(new String[]{"TrainStation", "BusStation"});
        JLabel lblName = new JLabel("Tên:");
        txtName = new JTextField();
        JLabel lblLocation = new JLabel("Địa điểm:");
        txtLocation = new JTextField();
        JLabel lblNumber = new JLabel("Số tàu/Số tuyến buýt:");
        txtNumber = new JTextField();

        // Thêm các thành phần nhập liệu vào panel
        inputPanel.add(lblType);
        inputPanel.add(cmbType);
        inputPanel.add(lblName);
        inputPanel.add(txtName);
        inputPanel.add(lblLocation);
        inputPanel.add(txtLocation);
        inputPanel.add(lblNumber);
        inputPanel.add(txtNumber);

        // Tạo bảng và mô hình bảng để hiển thị dữ liệu
        String[] columns = {"ID", "Loại trạm", "Tên", "Địa điểm", "Số tàu/Số tuyến"};
        tableModel = new DefaultTableModel(columns, 0) {
            // Ghi đè phương thức isCellEditable để không cho phép người dùng chỉnh sửa trực tiếp trên bảng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Không cho phép chỉnh sửa
            }
        };
        table = new JTable(tableModel);

        // Tạo các nút bấm
        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnLoad = new JButton("Tải từ CSDL");

        // Thêm các nút vào panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnLoad);

        // Thêm các sự kiện cho nút bấm
        btnAdd.addActionListener(e -> addStation());
        btnDelete.addActionListener(e -> deleteStation());
        btnUpdate.addActionListener(e -> updateStation());
        btnLoad.addActionListener(e -> loadStationsFromDatabase());

        // Bố trí các thành phần trong giao diện chính
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);  // Panel nhập liệu ở trên
        add(new JScrollPane(table), BorderLayout.CENTER);  // Bảng ở giữa
        add(buttonPanel, BorderLayout.SOUTH);  // Nút bấm ở dưới
    }
//     public ArrayList<Station> getListStation{
//         ArrayList<Student> list = new ArrayList<>();
//         String sql = "SELECT * FROM stations";
//         try{
//             PreparedStatement ps = conn.prepareStatement(sql);
//             while(rs.next()){
//                 Station s = new Station();
//                 s.setId(rs.getString("ID"));
//             }catch(Exception e){

//             }
//             return list;
//         }
// }
    // Phương thức thêm trạm mới vào danh sách và CSDL
    private void addStation() {
        try {
            // Lấy dữ liệu từ các trường nhập
            String type = cmbType.getSelectedItem().toString();
            String name = txtName.getText();
            String location = txtLocation.getText();
            int number = Integer.parseInt(txtNumber.getText());

            // Thêm vào CSDL
            DatabaseHelper.addStation(type, name, location, number);

            // Thêm vào danh sách và bảng
            stationList.add(new Station(type, name, location, number));
            loadStationsFromDatabase();  // Cập nhật lại bảng sau khi thêm
            clearInputFields();  // Xóa dữ liệu sau khi thêm
        } catch (NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm trạm: " + ex.getMessage());
        }
    }

    // Phương thức xóa trạm được chọn khỏi danh sách và CSDL
    private void deleteStation() {
        int selectedRow = table.getSelectedRow();  // Lấy hàng được chọn
        if (selectedRow >= 0) {
            try {
                // Lấy ID từ bảng và xóa khỏi CSDL
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                DatabaseHelper.deleteStation(id);

                // Cập nhật lại bảng sau khi xóa
                loadStationsFromDatabase();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa trạm: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạm cần xóa!");
        }
    }

    // Phương thức cập nhật dữ liệu trạm trong danh sách và CSDL
    private void updateStation() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                // Lấy dữ liệu mới từ các trường nhập
                String type = cmbType.getSelectedItem().toString();
                String name = txtName.getText();
                String location = txtLocation.getText();
                int number = Integer.parseInt(txtNumber.getText());

                // Lấy ID từ bảng
                int id = (int) tableModel.getValueAt(selectedRow, 0);

                // Cập nhật trong CSDL
                DatabaseHelper.updateStation(id, type, name, location, number);

                // Cập nhật lại bảng sau khi chỉnh sửa
                loadStationsFromDatabase();
                clearInputFields();
            } catch (NumberFormatException | SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật trạm: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạm cần chỉnh sửa!");
        }
    }

    // Phương thức tải tất cả các trạm từ CSDL và hiển thị lên bảng
    private void loadStationsFromDatabase() {
        try {
            ResultSet rs = DatabaseHelper.loadStations();
            stationList.clear();  // Xóa danh sách cũ
            tableModel.setRowCount(0);  // Xóa dữ liệu cũ trong bảng

            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                String name = rs.getString("name");
                String location = rs.getString("location");
                int number = rs.getInt("number");

                // Tạo đối tượng Station và thêm vào danh sách
                Station station;
                if (type.equals("TrainStation")) {
                    station = new TrainStation(name, location, number);
                } else {
                    station = new BusStation(name, location, number);
                }
                station.setId(id);  // Gán ID từ CSDL
                stationList.add(station);

                // Thêm hàng vào bảng
                tableModel.addRow(new Object[]{id, type, name, location, number});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ CSDL: " + e.getMessage());
        }
    }

    // Phương thức xóa dữ liệu trong các trường nhập liệu sau khi thêm/xóa
    private void clearInputFields() {
        txtName.setText("");
        txtLocation.setText("");
        txtNumber.setText("");
    }

    // Phương thức main để chạy ứng dụng
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StationManagerGUI gui = new StationManagerGUI();
            gui.setVisible(true);  // Hiển thị cửa sổ giao diện
        });
    }
}
