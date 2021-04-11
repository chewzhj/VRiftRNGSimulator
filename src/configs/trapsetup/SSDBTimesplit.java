package configs.trapsetup;

public class SSDBTimesplit implements TrapSetup{

    @Override
    public double normalCR() {
        return 89.74;
    }

    @Override
    public double bulwarkCR() {
        return 72.58;
    }

    @Override
    public double teCR() {
        return 66.78;
    }
}
