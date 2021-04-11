package calculation;

import configs.Generator;
import configs.Setup;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    int step;
    int numHunts;
    int totalCaught = 0;
    int taAttracted = 0;
    int bulAttr = 0;
    int bulCaught = 0;
    int teAttr = 0;
    int teCaught = 0;
    int stamina;
    List<Hunt> hunts;

    private Simulation(Setup setup) {
        step = setup.startStep;
        numHunts = 0;
        stamina = setup.startNumHunts;
        hunts = new ArrayList<>();
    }

    public static Simulation createSimulation(Setup setup) {
        Simulation simulation = new Simulation(setup);

        simulation.run(setup);

        return simulation;
    }

    private void run(Setup setup) {
        while (stamina-- > 0) {
            numHunts++;
            hunt(setup, numHunts);
        }
    }

    private void hunt(Setup setup, int currentHunt) {
        int floor = Generator.getFloorFromStep(step);
        Hunt hunt = new Hunt(setup, step, floor, currentHunt);

        step = CalculationMain.calculateNewStep(hunt);

        switch (hunt.mouse) {
            case "TA" -> taAttracted++;
            case "TE" -> teAttr++;
            case "Bulwark" -> bulAttr++;
        }

        if (hunt.caught) {
            totalCaught++;
            if (hunt.mouse.equals("TE")) {
                stamina += 50;
                teCaught++;
            } else if (hunt.mouse.equals("Bulwark")) bulCaught++;
        }

        hunts.add(hunt);
    }

    private static String generateSimulationTitle() {

        return "|Hunt|" +
                "  Mouse   |" +
                "?|" +
                " Fl |" +
                " Step|";
    }

    public void printSimulation() {
        System.out.println(generateSimulationTitle());

        hunts.forEach(System.out::println);
    }

}
