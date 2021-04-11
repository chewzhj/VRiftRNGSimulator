package configs.attraction;

public record AttractionRate(double taAR, double bulwarkAR) {
    public double normalAR() {
        return 100-taAR-bulwarkAR;
    }
}
