public class TrainStation extends Station {
    private int numberOfTrains;

    // Constructor nhận 3 tham số và gọi constructor của lớp cha
    public TrainStation(String name, String location, int numberOfTrains) {
        super(name, location);  // Gọi constructor của lớp cha (Station)
        this.numberOfTrains = numberOfTrains;
    }

    // Getter và Setter cho numberOfTrains
    public int getNumberOfTrains() {
        return numberOfTrains;
    }

    public void setNumberOfTrains(int numberOfTrains) {
        this.numberOfTrains = numberOfTrains;
    }
}
