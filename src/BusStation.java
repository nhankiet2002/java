public class BusStation extends Station {
    private int numberOfBusLines;

    public BusStation(String name, String location, int numberOfBusLines) {
        super(name, location);
        this.numberOfBusLines = numberOfBusLines;
    }

    public int getNumberOfBusLines() {
        return numberOfBusLines;
    }

    public void setNumberOfBusLines(int numberOfBusLines) {
        this.numberOfBusLines = numberOfBusLines;
    }
}
