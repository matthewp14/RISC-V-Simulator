package simulator;

public class Runner {

	static String path = "test_files/instructor/instruction_tests/";
	static String name = "test_jalr.bin";
	public static void main(String[] args) {
		String[] arguments = new String[]{path + name};
		IsaSim.main(arguments);
	}
}
