package simulator;

public class Runner {

	static String path = "test_files/demo/";
	static String name = "t1.bin";
	public static void main(String[] args) {
		String[] arguments = new String[]{path + name};
		IsaSim.main(arguments);
	}
}
