package configs.trapsetup;

public class PB110EggCharge implements TrapSetup{
    @Override
    public double normalCR() {
        return 82.76;
    }

    @Override
    public double bulwarkCR() {
        return 60.73;
    }

    @Override
    public double teCR() {
        return 54.54;
    }
}
