package configs.trapsetup;

public interface TrapSetup {
    double normalCR();
    default double taCR() { return 100; }
    double bulwarkCR();
    double teCR();
}
