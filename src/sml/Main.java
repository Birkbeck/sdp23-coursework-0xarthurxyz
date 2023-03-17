package sml;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;

import static sml.Registers.Register.*;

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
			try {
				// Creates AddInstruction Class
				Class<?> add = Class.forName(Machine.getOpcodeClassName(supportedOpcodes.add));
				// Prints class name
				System.out.println(add.getSimpleName());

				// sets registers.
				m.getRegisters().set(EAX, 5);
				m.getRegisters().set(EBX, 6);

				// int argumentLen = 2;
				// String[] argumentsList = { "f3", "EAX", "EBX" };

				Class<?> addIns = Class.forName(Machine.getOpcodeClassName(supportedOpcodes.add));
				// AddInstruction(String label, RegisterName result, RegisterName source)
				Constructor<?> constructor = addIns.getConstructor(new Class[]{ String.class, sml.RegisterName.class, sml.RegisterName.class });
        Instruction ins = (Instruction)constructor.newInstance("f3", EAX, EBX);
				System.out.println(ins.toString());

				// for (Constructor<?> candidateConstructor : Class.forName(
				// 		Machine.getOpcodeClassName(supportedOpcodes.add)).getConstructors()) {
				// 	if (candidateConstructor.getParameterCount() == argumentLen) {
				// 		try {
				// 			Object[] parameterObjs = new Object[argumentLen];
				// 			// get the candidate constructor parameters
				// 			Class<?>[] paramCons = candidateConstructor.getParameterTypes();
				// 			System.out.println("here 1");
				// 			for (int i = 0; i < argumentLen; i++) {
				// 				System.out.println("here 2");
				// 				// attempt to type the parameters using any available string constructors
				// 				// NoSuchMethodException will be thrown where retyping isn't possible
				// 				Class<?> c = toWrapper(paramCons[i]);
				// 				parameterObjs[i] = c.getConstructor(String.class).newInstance(argumentsList[i]);
				// 			}
				// 			// return instance ob object using the successful constructor
				// 			// and parameters of the right class types.
				// 			Object obj = candidateConstructor.newInstance(parameterObjs);
				// 		} catch (NoSuchMethodException ignored) {
				// 			System.out.println("NoSuchMethodException");
				// 		} catch (Exception e) {
				// 			e.printStackTrace();
				// 		}
				// 	}
				// }

				// Arguments
				// int argumentLen = 3;
				// Object[] parameterObjs = new Object[argumentLen];
				// String[] argumentsList = { "null", "EAX", "EBX" };
				// // get the candidate constructor parameters
				// Class<?>[] paramCons = add.getConstructor().getParameterTypes();

				// for (int i = 0; i < argumentLen; i++) {
				// 	// attempt to type the parameters using any available string constructors
				// 	// NoSuchMethodException will be thrown where retyping isn't possible
				// 	Class<?> c = toWrapper(paramCons[i]);
				// 	parameterObjs[i] = c.getConstructor(String.class).newInstance(argumentsList[i]);

				// 	// return instance ob object using the successful constructor
				// 	// and parameters of the right class types.
				// 	Object obj = add.getConstructor().newInstance(parameterObjs);
				// 	System.out.println(obj.toString());
				// }
				// Instantiate AddInstruction
				// Object obj = add.getConstructor().newInstance(argumentsList);
				// System.out.println(obj.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Main.getEnumConstants();

			// Class<> myClass = Main.class;

			// System.out.println(Arrays.toString(OpCodes.values()));
			// System.out.println((OpCodes.values());

			// for(OpCodes opcode : myClass.getEnumConstants()) {
			// System.out.println(opcode.className());
			// }

		} catch (IOException e) {
			System.out.println("Error reading the program from " + args[0]);
		}
	}

	private static Class<?> toWrapper(Class<?> testClass) {
		return PRIMITIVE_TYPE_WRAPPERS.getOrDefault(testClass, testClass);
	}

	private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_WRAPPERS = Map.of(
			int.class, Integer.class,
			long.class, Long.class,
			boolean.class, Boolean.class,
			byte.class, Byte.class,
			char.class, Character.class,
			float.class, Float.class,
			double.class, Double.class,
			short.class, Short.class,
			void.class, Void.class);
}
