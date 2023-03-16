package sml;

import java.io.IOException;

//
// My TODO: Remove unofficial imports below
//
import static sml.Registers.Register.*;

public class Main {
	/**
	 * Initialises the system and executes the program.
	 *
	 * @param args name of the file containing the program text.
	 */
	public static void main(String... args) {
		//
		// My TODO: Uncomment offifical instructions below:
		//

		// if (args.length != 1) {
		// 	System.err.println("Incorrect number of arguments - Machine <file> - required");
		// 	System.exit(-1);
		// }

		try {
			//
			// My TODO: Remove my personal instructions below
			//
			Translator t = new Translator("test3.sml");
			Machine m = new Machine(new Registers());
			t.readAndTranslate(m.getLabels(), m.getProgram());


			System.out.println("Here is the program; it has " + m.getProgram().size() + " instructions.");

			System.out.println("Beginning program execution.");
			m.execute();
			System.out.println("Ending program execution.");
			System.out.println("Values of register is: " + m.getRegisters().get(EAX));


			//
			// My TODO: Uncomment offifical instructions below:
			//

			// Translator t = new Translator(args[0]);
			// Machine m = new Machine(new Registers());
			// t.readAndTranslate(m.getLabels(), m.getProgram());

			// My TODO: Uncomment official instructions
			// System.out.println("Here is the program; it has " + m.getProgram().size() + " instructions.");
			// System.out.println(m);

			// System.out.println("Beginning program execution.");
			// m.execute();
			// System.out.println("Ending program execution.");

			// System.out.println("Values of registers at program termination:" + m.getRegisters() + ".");
		}
		catch (IOException e) {
			System.out.println("Error reading the program from " + args[0]);
		}
	}
}
