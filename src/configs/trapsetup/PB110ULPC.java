package configs.trapsetup;

public class PB110ULPC implements TrapSetup{

    @Override
    public double normalCR() {
        return 86.70;
    }

    @Override
    public double bulwarkCR() {
        return 68.04;
    }

    @Override
    public double teCR() {
        return 62.19;
    }
}
