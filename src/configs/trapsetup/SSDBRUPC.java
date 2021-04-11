package configs.trapsetup;

public class SSDBRUPC implements TrapSetup{

    @Override
    public double normalCR() {
        return 87.79;
    }

    @Override
    public double bulwarkCR() {
        return 70.62;
    }

    @Override
    public double teCR() {
        return 65.06;
    }
}
