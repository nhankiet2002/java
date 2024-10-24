import java.util.Scanner;
import java.io.*;
import java.util.*;

class Main {

    private static final String TEXT_FILE = "stations.txt";
    private static final String BINARY_FILE = "stations.dat";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Station> stationList = new ArrayList<>();
        int choice;

        stationList = loadTxt(); 
        stationList = loadBinary();

        do {
            System.out.println("\n1. Add TrainStation");
            System.out.println("2. Add BusStation");
            System.out.println("3. Show list");
            System.out.println("4. Delete ");
            System.out.println("5. Find");
            System.out.println("6. Save to text file and Save to binary file");
            System.out.println("0. Exit");
            System.out.print("Select: ");
            choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    TrainStation trainStation = new TrainStation();
                    try {
                        trainStation.inputData();
                        stationList.add(trainStation);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    BusStation busStation = new BusStation();
                    try {
                        busStation.inputData();
                        stationList.add(busStation);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\nDanh sách các trạm :");
                    for (Station station : stationList) {
                        station.displayData();
                        System.out.println();
                    }
                    break;

                case 4:
                    System.out.print("Nhập tên trạm cần xóa: ");
                    String nameToDelete = scanner.nextLine();
                    stationList.removeIf(station -> station.getName().equalsIgnoreCase(nameToDelete));
                    break;

                case 5:
                    System.out.print("Nhập tên trạm cần tìm: ");
                    String nameToFind = scanner.nextLine();
                    for (Station station : stationList) {
                        if (station.getName().equalsIgnoreCase(nameToFind)) {
                            station.displayData();
                        }
                    }
                    break;

                case 6:
                    saveTxt(stationList);
                    saveBinary(stationList); 
                    break;

                case 0:
                    System.out.println("Thoát.");
                    break;

                default:
                    System.out.println("Không hợp lệ.");
            }
        } while (choice != 0);
    }

    // Ghi danh sách vào tệp văn bản
    private static void saveTxt(List<Station> stationList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEXT_FILE))) {
            for (Station station : stationList) {
                writer.write(station.getClass().getSimpleName() + "," + station.getName() + "," + station.getLocation());
                if (station instanceof TrainStation) {
                    writer.write("," + ((TrainStation) station).getNumberTrain());
                } else if (station instanceof BusStation) {
                    writer.write("," + ((BusStation) station).getNumberOfBusLines());
                }
                writer.newLine(); 
            }
            writer.flush(); 
            System.out.println("Lưu danh sách vào tệp văn bản thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi vào tệp: " + e.getMessage());
        }
    }

    // Đọc danh sách từ tệp văn bản
    private static List<Station> loadTxt() {
        List<Station> stationList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEXT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals("TrainStation")) {
                    stationList.add(new TrainStation(data[1], data[2], Integer.parseInt(data[3])));
                } else if (data[0].equals("BusStation")) {
                    stationList.add(new BusStation(data[1], data[2], Integer.parseInt(data[3])));
                }
            }
            System.out.println("Tải danh sách từ tệp văn bản thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc từ tệp: " + e.getMessage());
        }
        return stationList;
    }

    // Ghi danh sách vào tệp nhị phân
    private static void saveBinary(List<Station> stationList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BINARY_FILE))) {
            oos.writeObject(stationList);
            oos.flush(); // Đảm bảo dữ liệu được ghi vào file
            System.out.println("Lưu danh sách vào tệp nhị phân thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi vào tệp: " + e.getMessage());
        }
    }

    // Đọc danh sách từ tệp nhị phân
    private static List<Station> loadBinary() {
        List<Station> stationList = new ArrayList<>();
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(BINARY_FILE))) {
            stationList = (List<Station>) oi.readObject();
            System.out.println("Tải danh sách từ tệp nhị phân thành công.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khi đọc từ tệp: " + e.getMessage());
        }
        return stationList;
    }
}
