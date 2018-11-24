package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import simulator.IsaSim;

class ModifiedRandoms {
	
	static String testPath = "test_files/instructor/modified_randoms/";
	static Path outputFile = Paths.get("./output.res");
	

	@Test
	void random1() throws IOException {
		String testName = "test_random1";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test_show_gp"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}

}
