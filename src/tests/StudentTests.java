package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import simulator.IsaSim;

class StudentTests {

	static String testPath = "test_files/student/";
	static Path outputFile = Paths.get("./output.res");
	
	@Test
	void loadStore() throws IOException {
		String testName = "load_store";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}

}
