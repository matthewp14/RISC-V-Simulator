package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import simulator.IsaSim;

class Task3 {

	static String testPath = "test_files/instructor/task3/";
	static Path outputFile = Paths.get("./output.res");
	
	@Test
	void newloop() throws IOException {
		String testName = "newloop";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test_show_sp"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void oldloop() throws IOException {
		String testName = "oldloop";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}

}
