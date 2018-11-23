package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import simulator.IsaSim;

class Task2 {

	static String testPath = "test_files/instructor/task2/";
	static Path outputFile = Paths.get("./output.res");
	
	@Test
	void branchcnt() throws IOException {
		String testName = "branchcnt";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void branchmany() throws IOException {
		String testName = "branchmany";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
}
