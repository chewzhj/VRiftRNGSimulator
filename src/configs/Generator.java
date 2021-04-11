package configs;

import calculation.Hunt;
import configs.attraction.AttractionRate;

public class Generator {
    public static final AttractionRate[] arMap = generateARMap();
    public static final int[] floorMap = floorSetup();

    private static AttractionRate[] generateARMap() {
        AttractionRate[] arMap = new AttractionRate[4];

        double[][] data = {
                {18.92, 17.93},
                {10.95, 18.46},
                {9.85, 19.30},
                {8.68, 20.13}
        };

        for (int i = 0; i < 4; i++) {
            arMap[i] = new AttractionRate(data[i][0], data[i][1]);
        }

        return arMap;
    }

    private static int[] floorSetup() {
        int[] floorBases = new int[150];

        int step = 0;
        for (int i = 1; i < 150; i++) {
            floorBases[i] = step;

            // calculate steps on the floor
            int stepsOnFloor;
            int lap = i / 8 + 1; // 1-7 is 1st lap, 9-15 is 2nd, ...
            if (i % 8 == 0) // eclipse floor
                stepsOnFloor = 1;
            else // regular, (lap+1)*10
                stepsOnFloor = (lap+1)*10;

            // add steps on the floor
            step += stepsOnFloor;
        }

        return floorBases;
    }

    public static int getFloorFromStep(int step) {
        for (int i = 2; i < 150; i++) {
            if (step < floorMap[i]) return i-1;
        }
        return 149;
    }

    public static void greet() {
        System.out.println(greeter());
    }

    public static void printContinue() {
        System.out.println(continueMessage());
    }

    public static String greeter() {
        return """
                VRift Simulator
                Enter the code:
                <S<#T>> <B> <U> <CS> <RH>
                Ex: M100 SRLP Y 8000 43
                
                Program will stop if it code is not recognized.
                
                Reference
                S - Simulation Type: O (One time) / M (Many times)
                #T - If Many times, input number of simulations
                B - Base and charm config: See <file> for codes
                U - UC on TE? Y/N
                CS - Current Step: Input the current step you are on, if you are starting, leave it blank
                RH - Remaining Hunts: Input remaining hunts, if left blank, it will be 100
                """;
    }

    public static String continueMessage() {
        return """
               Press 'Y' to continue or any other key to exit
               """;
    }

    public static String formatHunt(Hunt hunt) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("|%4d|", hunt.getHunts()));
        sb.append(String.format("%-10s|", hunt.getMouse()));
        sb.append(String.format("%s|", hunt.isCaught()?"Y":"N"));
        sb.append(String.format("%4d|", hunt.getFloorBefore()));
        sb.append(String.format("%5d|", hunt.getStepBefore()));

        return sb.toString();
    }

}
