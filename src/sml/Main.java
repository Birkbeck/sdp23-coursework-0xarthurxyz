package sml;

import java.io.IOException;

public class Main {
	/**
	 * Initialises the system and executes the program.
	 *
	 * @param args name of the file containing the program text.
	 */
	public static void main(String... args) {
		if (args.length != 1) {
			System.err.println("Incorrect number of arguments - Machine <file> - required");
			System.exit(-1);
		}

		try {
			Translator t = new Translator(args[0]);
			Machine m = new Machine(new Registers());
			t.readAndTranslate(m.getLabels(), m.getProgram());

			System.out.println("Here is the program; it has " + m.getProgram().size() + " instructions.");
			System.out.println(m);

			System.out.println("Beginning program execution.");
			m.execute();
			System.out.println("Ending program execution.");

			System.out.println("Values of registers at program termination:" + m.getRegisters() + ".");

			// My tests
			System.out.println("Labels are:" + m.getLabels().toString() + ".");

			// Enum print works
			System.out.println(Machine.getOpcodeClassName(supportedOpcodes.add));

			// Main.getEnumConstants();

			// Class<> myClass = Main.class;
			
			// System.out.println(Arrays.toString(OpCodes.values()));
			// System.out.println((OpCodes.values());

			// for(OpCodes opcode : myClass.getEnumConstants()) {
			// 	System.out.println(opcode.className());
			// }

		}
		catch (IOException e) {
			System.out.println("Error reading the program from " + args[0]);
		}
	}
}


