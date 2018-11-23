package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import simulator.IsaSim;

class InstructionTests  {

	static String testPath = "test_files/instructor/instruction_tests/";
	static Path outputFile = Paths.get("./output.res");
	
	@Test
	void add() throws IOException {
		String testName = "test_add";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void addi() throws IOException {
		String testName = "test_addi";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void and() throws IOException {
		String testName = "test_and";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void andi() throws IOException {
		String testName = "test_andi";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void auipc() throws IOException {
		String testName = "test_auipc";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void beq() throws IOException {
		String testName = "test_beq";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void bge() throws IOException {
		String testName = "test_bge";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void bgeu() throws IOException {
		String testName = "test_bgeu";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void blt() throws IOException {
		String testName = "test_blt";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void bltu() throws IOException {
		String testName = "test_bltu";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void bne() throws IOException {
		String testName = "test_bne";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
//	@Test
//	void ecall() throws IOException {
//		String testName = "test_ecall";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
	
	@Test
	void jal() throws IOException {
		String testName = "test_jal";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void jalr() throws IOException {
		String testName = "test_jalr";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void lb() throws IOException {
		String testName = "test_lb";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}

	@Test
	void lbu() throws IOException {
		String testName = "test_lbu";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void lh() throws IOException {
		String testName = "test_lh";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void lhu() throws IOException {
		String testName = "test_lhu";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void li() throws IOException {
		String testName = "test_li";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void lui() throws IOException {
		String testName = "test_lui";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void lw() throws IOException {
		String testName = "test_lw";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void or() throws IOException {
		String testName = "test_or";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void ori() throws IOException {
		String testName = "test_ori";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
//	@Test
//	void random1() throws IOException {
//		String testName = "test_random1";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
//
//	@Test
//	void random2() throws IOException {
//		String testName = "test_random2";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
//	
//	@Test
//	void random3() throws IOException {
//		String testName = "test_random3";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
//	
//	@Test
//	void random4() throws IOException {
//		String testName = "test_random4";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
//	
//	@Test
//	void random5() throws IOException {
//		String testName = "test_random5";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
//	
//	@Test
//	void random6() throws IOException {
//		String testName = "test_random6";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
//	
//	@Test
//	void random7() throws IOException {
//		String testName = "test_random7";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
//	
//	@Test
//	void random8() throws IOException {
//		String testName = "test_random8";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
//	
//	@Test
//	void random9() throws IOException {
//		String testName = "test_random9";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
//	
//	@Test
//	void random10() throws IOException {
//		String testName = "test_random10";
//		String path = testPath + testName + ".bin";
//		String[] arguments = new String[]{path, "test"};
//		IsaSim.main(arguments);
//		Path trueResults = Paths.get(testPath + testName + ".res");
//		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
//	}
	
	@Test
	void sb() throws IOException {
		String testName = "test_sb";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void sh() throws IOException {
		String testName = "test_sh";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void sll() throws IOException {
		String testName = "test_sll";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void slli() throws IOException {
		String testName = "test_slli";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void slt() throws IOException {
		String testName = "test_slt";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void slti() throws IOException {
		String testName = "test_slti";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void sltiu() throws IOException {
		String testName = "test_sltiu";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void sltu() throws IOException {
		String testName = "test_sltu";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void sra() throws IOException {
		String testName = "test_sra";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void srai() throws IOException {
		String testName = "test_srai";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void srl() throws IOException {
		String testName = "test_srl";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void srli() throws IOException {
		String testName = "test_srli";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void sub() throws IOException {
		String testName = "test_sub";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void sw() throws IOException {
		String testName = "test_sw";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void xor() throws IOException {
		String testName = "test_xor";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
	
	@Test
	void xori() throws IOException {
		String testName = "test_xori";
		String path = testPath + testName + ".bin";
		String[] arguments = new String[]{path, "test"};
		IsaSim.main(arguments);
		Path trueResults = Paths.get(testPath + testName + ".res");
		assertTrue(Arrays.equals(Files.readAllBytes(trueResults), Files.readAllBytes(outputFile)));
	}
}
