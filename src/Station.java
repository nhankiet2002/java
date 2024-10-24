import java.io.Serializable;

public class Station implements Serializable {
    private int id;
    private String name;
    private String location;
    private String type;  // Thêm thuộc tính type (loại trạm)
    private int number;    // Thêm thuộc tính number (số tàu/số tuyến buýt)

    // Constructor nhận 4 tham số
    public Station(String name, String location, String type, int number) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.number = number;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho các thuộc tính khác
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
