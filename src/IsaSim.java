/**
 * RISC-V Instruction Set Simulator
 * 
 * A tiny first step to get the simulator started. Can execute just a single
 * RISC-V instruction.
 * 
 * @author Martin Schoeberl (martin@jopdesign.com)
 *
 */
import java.io.*;
public class IsaSim {

	static int pc;
	static int reg[] = new int[32];

	// Here the first program hard coded as an array
	static int mem[] = new int[1000000];

	public static void main(String[] args) {
		int prog[] = readByteFile(args[0]);
		System.out.println("Hello RISC-V World!");

		pc = 0;

		for (;;) {

			int instr = prog[pc];
			int opcode = instr & 0x7f;
//			int rd = (instr >> 7) & 0x01f;
//			int rs1 = (instr >> 15) & 0x01f;
//			int imm = (instr >> 20);
			
			if (opcode == 0b0000011 || opcode ==  0b0001111 || opcode ==  0b0010011 || opcode ==  0b0011011 || opcode ==  0b1110011 || opcode ==  0b110111) {
				// I-TYPE
			}
			else if (opcode == 0b0000011 || opcode ==  0b0001111 || opcode ==  0b0010011 || opcode ==  0b0011011 || opcode ==  0b1110011 || opcode ==  0b110111) {
				
			}
			else {
				System.out.println("Opcode " + opcode + " not yet implemented");
				break;
			}


			switch (opcode) {

			case 0x13:
				reg[rd] = reg[rs1] + imm;
				break;
			default:
				System.out.println("Opcode " + opcode + " not yet implemented");
				break;
			}

			++pc; // We count in 4 byte words
			if (pc >= progr.length) {
				break;
			}
			for (int i = 0; i < reg.length; ++i) {
				System.out.print(reg[i] + " ");
			}
			System.out.println();
		}

		System.out.println("Program exit");

	}
	
	
	
	
	
	public static int[] readByteFile(String inFile) {
		try (
			InputStream inputStream = new FileInputStream(inFile);
		) {
			long fileSize = new File(inFile).length();
			byte[] fileBytes = new byte[ (int) fileSize];
			
			inputStream.read(fileBytes); // Read the byte file into the byte array
			
			
			int[] instArr = new int[ (int) (fileSize/4)]; // integer array of instructions
			
			//loop through byte array and construct 32bit instructions
			for (int i = 0 ; i < fileSize; i+=4) {
				int instr = fileBytes[i];
				instr = instr << 8;
				instr = instr + fileBytes[i+1];
				instr = instr << 8;
				instr = instr + fileBytes[i+2];
				instr = instr << 8;
				instr = instr + fileBytes[i+3];
				instArr[i/4] = instr;				
			}
			return instArr;
		}
		catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	

}