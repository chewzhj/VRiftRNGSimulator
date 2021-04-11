package calculation;

import configs.Generator;
import configs.Setup;

import java.util.Map;
import java.util.TreeMap;

import static configs.Generator.getFloorFromStep;

public class CalculationMain {

    public static void run(Setup setup) {
        if (setup.simulTypeSingle) {
            Simulation simulation = Simulation.createSimulation(setup);
            simulation.printSimulation();
            System.out.println(generateStatistics(simulation));
        } else {
            int numSims = setup.numSimul;
            Map<Integer, Integer> teCounts = new TreeMap<>();
            Map<Integer, Integer> floorCounts = new TreeMap<>();

            while(numSims-- > 0) {
                Simulation simulation = Simulation.createSimulation(setup);
                int finalFloor = getFloorFromStep(simulation.step);
                int teValue = finalFloor / 8;

                if (!teCounts.containsKey(teValue)) {
                    teCounts.put(teValue, 1);
                } else {
                    int count = teCounts.get(teValue);
                    teCounts.replace(teValue, count+1);
                }

                if (!floorCounts.containsKey(finalFloor)) {
                    floorCounts.put(finalFloor, 1);
                } else {
                    int count = floorCounts.get(finalFloor);
                    floorCounts.replace(finalFloor, count+1);
                }
            }

            System.out.println(teCounts);
            System.out.println(floorCounts);
        }
    }

    public static int calculateNewStep(Hunt hunt) {
        int movement = 0;

        if (hunt.caught) {
            movement = hunt.mouse.equals("TA") ? 44 : 11;
        } else {
            movement = hunt.mouse.equals("Bulwark") ? -10 : -5;
        }

        int newStep = hunt.stepBefore + movement;
        Limits limits = calculateLimits(hunt);

        if (newStep > limits.ceiling)
            newStep = limits.ceiling;
        else if (newStep < limits.base)
            newStep = limits.base;

        return newStep;
    }

    private record Limits(int base, int ceiling){}

    public static Limits calculateLimits(Hunt hunt) {
        int floorBase = Generator.floorMap[hunt.floorBefore];
        int lap = (hunt.floorBefore / 8) + 1;
        int lapCeiling = Generator.floorMap[lap*8];

        return new Limits(floorBase, lapCeiling);
    }

    public static String generateStatistics(Simulation simulation) {
        int normalHunts = simulation.numHunts - simulation.bulAttr - simulation.teAttr - simulation.taAttracted;
        int normalCaught = simulation.totalCaught - simulation.bulCaught - simulation.teCaught - simulation.taAttracted;

        double taAR = getPercentage(simulation.taAttracted, simulation.numHunts);
        double bulAR = getPercentage(simulation.bulAttr, simulation.numHunts);

        double bulCR = getPercentage(simulation.bulCaught, simulation.bulAttr);
        double normCR = getPercentage(normalCaught, normalHunts);
        double overallCR = getPercentage(simulation.totalCaught, simulation.numHunts);

        int finalFloor = getFloorFromStep(simulation.step);
        int teValue = finalFloor / 8;

        StringBuilder sb = new StringBuilder();
        sb.append("Statistics\n");

        appendValues(sb, "Total Hunts", simulation.numHunts);
        appendValues(sb, "Floor Reached", finalFloor);
        appendValues(sb, "TEs Caught", teValue);
        appendNewline(sb);

        appendRates(sb, "TA AR%", taAR);
        appendRates(sb, "Bulwark AR%", bulAR);
        appendNewline(sb);

        appendRates(sb, "Bulwark CR%", bulCR);
        appendRates(sb, "Normal CR%", normCR);
        appendRates(sb, "Overall CR%", overallCR);
        appendNewline(sb);

        return sb.toString();
    }

    private static void appendValues(StringBuilder sb, String title, int value) {
        sb.append(spaceTitle(title));
        sb.append(value);
        appendNewline(sb);
    }

    private static void appendRates(StringBuilder sb, String title, double rates){
        sb.append(spaceTitle(title));
        sb.append(String.format("%.2f",  rates));
        sb.append('%');
        appendNewline(sb);
    }

    private static String spaceTitle(String title) {
        return String.format("%-20s: ", title);
    }

    private static void appendNewline(StringBuilder sb) {
        sb.append('\n');
    }

    private static double getPercentage(int num, int dem) {
        return (100.0 * num) / dem;
    }
}
