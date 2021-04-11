package calculation;

import configs.Generator;
import configs.Setup;
import configs.attraction.AttractionRate;

import java.util.Random;

public class Hunt {
    static Random random = new Random();
    final String mouse;
    final boolean caught;
    final int stepBefore;
    final int floorBefore;
    final int currentHunt;

    Hunt(Setup setup, int stepBefore, int floorBefore, int currentHunt) {
        this.stepBefore = stepBefore;
        this.floorBefore = floorBefore;
        mouse = generateMouse(floorBefore);
        caught = catchMouse(setup);
        this.currentHunt = currentHunt;
    }

    private String generateMouse(int floor) {
        // eclipse on every 8
        if (floor % 8 == 0) return "TE";

        double mouseAttractedNum = random.nextDouble()*100;
        int lap = (floor / 8) + 1;
        if (lap > 4) lap = 4;

        AttractionRate lapAR = Generator.arMap[lap-1];
        if (mouseAttractedNum <= lapAR.bulwarkAR()) {
            return "Bulwark";
        } else if (mouseAttractedNum >= 100-lapAR.taAR()) {
            return "TA";
        } else {
            return "Normal";
        }
    }

    private boolean catchMouse(Setup setup) {
        double mouseCatchRNG = random.nextDouble()*100;

        return switch (mouse) {
            case "TA" -> setup.trapSetup.taCR() >= mouseCatchRNG;
            case "Bulwark" -> setup.trapSetup.bulwarkCR() >= mouseCatchRNG;
            case "TE" -> setup.ucTE || setup.trapSetup.teCR() >= mouseCatchRNG;

            default -> setup.trapSetup.normalCR() >= mouseCatchRNG;
        };
    }

    public String toString() {
        return Generator.formatHunt(this);
    }

    public int getHunts() {
        return currentHunt;
    }

    public String getMouse() {
        return mouse;
    }

    public boolean isCaught() {
        return caught;
    }

    public int getStepBefore() {
        return stepBefore;
    }

    public int getFloorBefore() {
        return floorBefore;
    }
}
