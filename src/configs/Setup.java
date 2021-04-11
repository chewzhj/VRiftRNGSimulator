package configs;

import configs.trapsetup.*;

public class Setup {
    public final boolean simulTypeSingle;
    public final int numSimul;
    public final TrapSetup trapSetup;
    public final boolean ucTE;
    public final int startStep;
    public final int startNumHunts;

    private Setup(boolean simulTypeSingle, int numSimul, TrapSetup trapSetup, boolean ucTE, int startStep, int startNumHunts) {
        this.simulTypeSingle = simulTypeSingle;
        this.numSimul = numSimul;
        this.trapSetup = trapSetup;
        this.ucTE = ucTE;
        this.startStep = startStep;
        this.startNumHunts = startNumHunts;
    }

    public static Setup getSetup(String code) {
        String[] chunks = code.split(" ");

        if (chunks.length < 3) {
            return null;
        }

        // parse simulation type
        boolean st = false;
        int numSimul = 0;

        if (chunks[0].charAt(0) == 'O') {
            if (chunks[0].length() == 1) {
                st = true;
            } else {
                return null;
            }
        } else if (chunks[0].charAt(0) == 'M') {
            String numSimulStr = chunks[0].substring(1);
            try {
                numSimul = Integer.parseInt(numSimulStr);
            } catch (NumberFormatException nfex) {
                return null;
            }
        }

        // parse base and charm setup
        TrapSetup trapSetup = retrieveTrapSetup(chunks[1]);
        if (trapSetup == null) return null;

        // parse ucTE
        boolean ucTE = false;
        if (chunks[2].length() == 1) {
            if (chunks[2].equalsIgnoreCase("Y")) {
                ucTE = true;
            } else if (!chunks[2].equalsIgnoreCase("N")) {
                return null;
            }
        } else {
            return null;
        }

        int step = 0, hunts = 100;
        if (chunks.length == 5) {
            try {
                step = Integer.parseInt(chunks[3]);
                hunts = Integer.parseInt(chunks[4]);
            } catch (NumberFormatException nfex) {
                return null;
            }

            if (step < 0 || hunts < 0) return null;
        } else if (chunks.length != 3) {
            return null;
        }

        return new Setup(st, numSimul, trapSetup, ucTE, step, hunts);
    }

    private static TrapSetup retrieveTrapSetup(String input) {
        return switch (input) {
            case "PU" -> new PB110ULPC();
            case "SRLP" -> new SSDBRULPC();
            case "SRP" -> new SSDBRUPC();
            case "ST" -> new SSDBTimesplit();
            case "PE" -> new PB110EggCharge();
            case "UC" -> new JustUC();

            default -> null;
        };
    }
}
