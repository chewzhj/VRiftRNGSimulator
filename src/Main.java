import calculation.CalculationMain;
import configs.Generator;
import configs.Setup;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Scanner sc = new Scanner(System.in);

        Generator.greet();

        while (true) {
            String input = sc.nextLine();
            Setup setup = Setup.getSetup(input);
            if (setup == null) break;

            CalculationMain.run(setup);
        }
    }



}
