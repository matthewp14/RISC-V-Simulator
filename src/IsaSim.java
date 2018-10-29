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
	static int reg[] = new int[4];

	// Here the first program hard coded as an array
	static int progr[] = {
			// As minimal RISC-V assembler example
			0x00200093, // addi x1 x0 2
			0x00300113, // addi x2 x0 3
			0x002081b3, // add x3 x1 x2
	};

	public static void main(String[] args) {

		System.out.println("Hello RISC-V World!");

		pc = 0;

		for (;;) {

			int instr = progr[pc];
			int opcode = instr & 0x7f;
			int rd = (instr >> 7) & 0x01f;
			int rs1 = (instr >> 15) & 0x01f;
			int imm = (instr >> 20);

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
			
			inputStream.read(fileBytes);
			
			
			int[] instArr = new int[ (int) (fileSize/4)];
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
		}
	}
	

}