package src.testingHarness;

import src.simulator.IsaSim;

public class Tester {

	//Probably looks like commented version on mac/linux 
	//static String testPath = "../../tests/instructor/";
	static String testPath = "tests/instructor/task1/";
	static String testName = "addpos.bin";
	public static void main(String[] args) {
		String[] arguments = new String[]{testPath + testName};
		IsaSim.main(arguments);
	}

}
