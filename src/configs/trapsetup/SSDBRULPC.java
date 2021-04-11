package configs.trapsetup;

public class SSDBRULPC implements TrapSetup{
    @Override
    public double normalCR() {
        return 89.31;
    }

    @Override
    public double bulwarkCR() {
        return 71.15;
    }

    @Override
    public double teCR() {
        return 65.10;
    }
}
