package configs.trapsetup;

public class JustUC implements TrapSetup{

    @Override
    public double normalCR() {
        return 100;
    }

    @Override
    public double bulwarkCR() {
        return 100;
    }

    @Override
    public double teCR() {
        return 100;
    }
}
