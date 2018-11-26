package simulator;
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
import java.nio.ByteBuffer;
public class IsaSim {

	static int progSize = 0;
	
	public static void main(String[] args) {
		
//		int mem[] = new int[536870911];
		int reg[] = new int[32];
		
		int pc = 0;
		progSize = 0;
		String testFlag = (args.length > 1) ? args[1] : null;

		reg[0] = 0; //hard code to 0
		
//		int prog[] = readByteFile(args[0]); //array of instructions
		int mem[] = readByteFile(args[0]); //array of instructions
		System.out.println("Hello RISC-V World!");


		reg[2] = 0x7ffffff0; // initialize sp towards end of mem array but with space for error
		reg[3] = 0x10000000;
		//initial values
		int instr = Integer.MIN_VALUE;
		int opcode = Integer.MIN_VALUE;
		int rd = Integer.MIN_VALUE;
		int rs1 = Integer.MIN_VALUE;
		int rs2 = Integer.MIN_VALUE;
		int imm = Integer.MIN_VALUE;
		int funct3 = Integer.MIN_VALUE;
		int funct7 = Integer.MIN_VALUE;
		int shamt = Integer.MIN_VALUE;
		boolean branch = false;
		//loop until broken
		loop: while(true) {		
			// Little to Big Endian
			
//			instr = Integer.reverseBytes(prog[pc]);
			instr = Integer.reverseBytes(mem[pc]);
			opcode = instr & 0b1111111;
			switch (opcode) {

			// U-Type
			case 0b0110111: {
				rd = (instr >> 7) & 0b11111;
				imm = ((instr >> 12) << 12);
				reg[rd] = imm;
				break;
			}
			// U-Type AUIPC
			case 0b0010111: {
				rd = (instr >> 7) & 0b11111;
				imm = ((instr >> 12) << 12);
				reg[rd] = pc*4 + imm; //store the byte address *not* the array address
				break;
			}
			
			// J-Type
			case 0b1101111: {
				rd = (instr >> 7) & 0b11111;
				imm = (((instr >> 21) & 0b1111111111) << 1)  + (((instr >> 20) & 0b1) << 11) + (((instr >> 12) & 0b11111111) << 12) + ((instr >> 31) << 20); 
				branch = true;
				reg[rd] = pc*4 + 4; //store byte address *not* array address
				break;
			}
			
			// JALR (Normal I-Type)
			case 0b1100111: {
				rd = (instr >> 7) & 0b11111;
				funct3 = (instr >> 12) & 0b111;
				if (funct3 != 0b000) {
					System.out.println("JALR should have a funct3 = 0b000. Unknown instruction.");
					break;
				}
				rs1 = (instr >> 15) & 0b11111;
				imm = (instr >> 20) >> 1 << 1;
				reg[rd] = pc*4 + 4; // store byte address 
				branch = true;
				pc = 0; // reset for simplicity in branching logic
				imm = imm + reg[rs1]; // byte value of new address
				break;
			}
			
			// B-Type
			case 0b1100011: {
				funct3 = (instr >> 12) & 0b111;
				rs1 = (instr >> 15) & 0b11111;
				rs2 = (instr >> 20) & 0b11111;
				imm = (((instr >> 8) & 0b1111) << 1) + (((instr >> 25) & 0b111111) << 5) + (((instr >> 7) & 0b1) << 11) + ((instr >> 31)  << 12); 
				// Add functionality
				switch (funct3) {
				case 0b000: { // BEQ
					if (reg[rs1] == reg[rs2]) {
						branch = true; 	
					}
					break;
				}
				case 0b001: { //BNE
					if (reg[rs1]!=reg[rs2]) {
						branch = true;
					}
					break;
				}
				case 0b100: { // BLT
					if (reg[rs1] < reg[rs2]) {
						branch = true;	
					}
					break;
				}
				case 0b101: { // BGE
					if (reg[rs1] >= reg[rs2]) {
						branch = true;
					}
					break;
				}
				case 0b110: { // BLTU
					if (Integer.compareUnsigned(reg[rs1], reg[rs2]) < 0 ) { // use Integer object to allow for unsigned comparison 
						branch = true;
					}
					break;
				}
				case 0b111: { //BGEU
					if (Integer.compareUnsigned(reg[rs1], reg[rs2])>=0) {
						branch = true;
					}
					break;
				}
				default: {
					System.out.println("Trying to perform an unimplemented B-Type");
					break;
				}
				}
				break;
			}
			
			// Loads (Normal I-Type)
			case 0b0000011: {
				rd = (instr >> 7) & 0b11111;
				funct3 = (instr >> 12) & 0b111;
				rs1 = (instr >> 15) & 0b11111;
				imm = (instr >> 20);
				byte b;
				short s;
				int eff_addr = (imm + reg[rs1])/4;
				// Add functionality
				switch (funct3) {
				case 0b000: { //LB
					// byte selection logic
					if ((imm+reg[rs1]) % 4 == 0) { // first byte at index 
						b = (byte)(mem[eff_addr] >> 24); // use byte to ensure correct sign extension
						reg[rd] = (int)b;
					}
					else if((imm+reg[rs1]) % 4 == 1) { // second byte
						b = (byte)(mem[eff_addr] >> 16);
						reg[rd] = (int)b;
					}
					else if((imm+reg[rs1]) % 4 == 2) { //third
						b = (byte)(mem[eff_addr] >> 8);
						reg[rd] = (int)b;
					}
					else { // last byte
						b = (byte)(mem[eff_addr]);
						reg[rd] = (int)b;
					}
					break;
				}
				case 0b001: { //LH
					// use short data type to ensure correct sign extension					
					if ((imm + reg[rs1]) % 4 == 0) { // lower 16 bits
						s = (short)(mem[eff_addr] >> 16);
					}
					else if ((imm + reg[rs1]) % 4 == 1)  { // Middle 16 bits
						s = (short)((mem[eff_addr] & 0x00ffff00) >> 8);
					}
					else if ((imm + reg[rs1]) % 4 == 2) { // upper 16 bits
						s = (short)(mem[eff_addr]);
					}
					else { // Across two words
						s = (short)((((mem[eff_addr] & 0xff) << 8)) | ((mem[eff_addr+1] & 0xff000000) >>> 24));
					}
					reg[rd] = (int)(Short.reverseBytes(s));
					break;
				}
				case 0b010: { //LW
					if ((imm + reg[rs1]) % 4 == 0) { // 1 word
						reg[rd] = mem[eff_addr];
					}
					else if ((imm + reg[rs1]) % 4 == 1)  { // 3 first, 1 second
						reg[rd] = ((mem[eff_addr] << 8) | (mem[eff_addr+1] >>> 24));
					}
					else if ((imm + reg[rs1]) % 4 == 2) { // 2 and 2
						reg[rd] = ((mem[eff_addr] << 16) | (mem[eff_addr+1] >>> 16));
					}
					else { // 1 and 3
						reg[rd] = ((mem[eff_addr] << 24) | (mem[eff_addr+1] >>> 8));
					}
					reg[rd] = Integer.reverseBytes(reg[rd]);
					break;
				}
				case 0b100: { //LBU
					//note, no need to type-cast here as this will result in signed extension
					if ((imm+reg[rs1]) % 4 == 0) { //first byte
						reg[rd] = (mem[eff_addr] >> 24) & 0xff;
					}
					else if ((imm + reg[rs1]) % 4 == 1) { // second byte
						reg[rd] = (mem[eff_addr] >> 16 ) & 0xff;
					}
					else if ((imm + reg[rs1]) % 4 == 2) { //third byte
						reg[rd] = (mem[eff_addr] >> 8) & 0xff;
					}
					else {
						reg[rd] = (mem[eff_addr]) & 0xff;
					}
					break;
				}
				case 0b101: { //LHU
					//no type casting to avoid signed representation
					if ((imm + reg[rs1]) % 4 == 0) { // lower 16 bits
						reg[rd] = (mem[eff_addr] >> 16) & 0xffff;
					}
					else if ((imm + reg[rs1]) % 4 == 1)  { // Middle 16 bits
						reg[rd] = (mem[eff_addr] >> 8) & 0xffff;
					}
					else if ((imm + reg[rs1]) % 4 == 2) { // upper 16 bits
						reg[rd] = (mem[eff_addr]) & 0xffff;
					}
					else { // Across two words
						reg[rd] = (((mem[eff_addr] & 0xff) << 8) | ((mem[eff_addr+1] & 0xff000000) >>> 24));
					}
					reg[rd] = Integer.reverseBytes(reg[rd]);
					reg[rd] >>>= 16;

				}
				default: {
					System.out.println("Trying to perform an unimplemented Load");
					break;
				}
				}
				break;
			}
			
			// S-Type
			case 0b0100011: {
				funct3 = (instr >> 12) & 0b111;
				rs1 = (instr >> 15) & 0b11111;
				rs2 = (instr >> 20) & 0b11111;
				int b;
				imm = ((instr >> 7) & 0b11111) + ((instr >> 25) << 5);
				int eff_addr = (imm + reg[rs1])/4;
				// Add functionality
				switch(funct3) {
				case 0b000: { //SB
					b = reg[rs2] & 0xff;
					if ((imm + reg[rs1]) % 4 == 0) { //first byte
						mem[eff_addr] = ((mem[eff_addr] & 0x00ffffff) | (b << 24));
					}
					else if ((imm + reg[rs1]) % 4 == 1)  { // second byte
						mem[eff_addr] = ((mem[eff_addr] & 0xff00ffff) | (b << 16));
					}
					else if ((imm + reg[rs1]) % 4 == 2) { // third byte
						mem[eff_addr] = ((mem[eff_addr] & 0xffff00ff) | (b << 8));
					}
					else { // last byte
						mem[eff_addr] = ((mem[eff_addr] & 0xffffff00) | b);
					}
					break;
					
				}
				case 0b001 : { //SH
					b = reg[rs2] & 0xffff;	
					b = Integer.reverseBytes(b);
					b >>>= 16;
					if ((imm + reg[rs1]) % 4 == 0) { // store in lower bits
						mem[eff_addr] = ((mem[eff_addr] & 0x0000ffff) | (b << 16));
					}
					else if ((imm + reg[rs1]) % 4 == 1)  { // Middle 16 bits
						mem[eff_addr] = ((mem[eff_addr] & 0xff0000ff) | (b << 8));
					}
					else if ((imm + reg[rs1]) % 4 == 2) { // upper 16 bits
						mem[eff_addr] = ((mem[eff_addr] & 0xffff0000) | b);
					}
					else { // Across two words
						mem[eff_addr] = ((mem[eff_addr] & 0xffffff00) | ((b & 0xff00) >> 8));
						mem[eff_addr+1] = ((mem[eff_addr+1] & 0x00ffffff) | ((b & 0x00ff) << 24));
					}
					break;
					
				}
				case 0b010: { //SW
					b = reg[rs2];
					b = Integer.reverseBytes(b);
					if ((imm + reg[rs1]) % 4 == 0) { // 1 word
						mem[eff_addr] = b; 
					}
					else if ((imm + reg[rs1]) % 4 == 1)  { // 3 first, 1 second
						mem[eff_addr] = ((mem[eff_addr] & 0xff000000) | (b >>> 8));
						mem[eff_addr+1] = ((mem[eff_addr+1] & 0x00ffffff) | (b << 24));
					}
					else if ((imm + reg[rs1]) % 4 == 2) { // 2 and 2
						mem[eff_addr] = ((mem[eff_addr] & 0xffff0000) | (b >>> 16));
						mem[eff_addr+1] = ((mem[eff_addr+1] & 0x0000ffff) | (b << 16));
					}
					else { // 1 and 3
						mem[eff_addr] = ((mem[eff_addr] & 0xffffff00) | (b >>> 24));
						mem[eff_addr+1] = ((mem[eff_addr+1] & 0x000000ff) | (b << 8));
					}
					break;
				}
				default: {
					System.out.println("Trying to perform an unimplemented Store");
					break;
				}
				}
				break;
			}
			
			// I-Type
			case 0b0010011: {
				rd = (instr >> 7) & 0b11111;
				funct3 = (instr >> 12) & 0b111;
				rs1 = (instr >> 15) & 0b11111;
				
				// Shift I-Type
				if (funct3 == 0b001 || funct3 == 0b101) {
					shamt = (instr >> 20) & 0b11111;
					funct7 = (instr >> 25);
					switch (funct3) {
					case 0b001: { //SLLI
						reg[rd] = reg[rs1] << shamt;
						break;
					}
					case 0b101: {
						if (funct7 == 0) { //SRLI
							reg[rd] = reg[rs1] >>> shamt;
						} else if (funct7 == 0b0100000) { //SRAI
							reg[rd] = reg[rs1] >> shamt;
						}
						else {
							System.out.println("Unsupported I-type (invalid funct7)");
						}
						break;
					}
					}
		
				}
				
				// Normal I-Type
				else {
					imm = (instr >> 20);
					switch (funct3) {
					case 0b000: { //ADDI
						reg[rd] = reg[rs1] + imm;
						break;
					}
					case 0b010: { // SLTI
						if (reg[rs1]< imm) reg[rd] = 1;
						 else reg[rd] = 0;
						break;
					}
					case 0b011: { // SLTIU
						if (Integer.compareUnsigned(reg[rs1], imm) < 0) reg[rd] = 1;
						else reg[rd] = 0;
						break;
					}
					case 0b100: { //XORI
						reg[rd] = reg[rs1] ^ imm;
						break;
					}
					case 0b110: { //ORI
						reg[rd] = reg[rs1] | imm;
						break;
					}
					case 0b111: { //ANDI
						reg[rd] = reg[rs1] & imm;
						break;
					}
					default: {
						System.out.println("Unsupported I-Type (funct3)");
						break;
					}
					}
				}
				break;
			}
			
			// R-Type
			case 0b0110011: {
				rd = (instr >> 7) & 0b11111;
				funct3 = (instr >> 12) & 0b111;
				rs1 = (instr >> 15) & 0b11111;
				rs2 = (instr >> 20) & 0b11111;
				funct7 = (instr >> 25);
				if (funct7 != 0 && (funct3 != 0b000 && funct3 != 0b101)) {
					System.out.println("Unsupported R-type (invalid funct7)");
					break;
				}
				switch (funct3) {
				case 0b000: {
					// Add
					if (funct7 == 0) {
						reg[rd] = reg[rs1] + reg[rs2];
					}
					// Subtract
					else if (funct7 == 0b0100000){
						reg[rd] = reg[rs1] - reg[rs2];
					}
					else {
						System.out.println("Unsupported R-type (invalid funct7)");
					}
					break;
				} 
				case 0b001: { //SLL
					shamt = (reg[rs2] & 0b11111);
					reg[rd] = reg[rs1] << shamt;
					break;
				}
				case 0b010: { //SLT
					if (reg[rs1] < reg[rs2]) reg[rd] = 1;
					else reg[rd] = 0;
					break;
				}
				case 0b011: { //SLTU
					if (Integer.compareUnsigned(reg[rs1], reg[rs2]) < 0) reg[rd] = 1;
					else reg[rd] = 0;
					break;
				}
				case 0b100: { //XOR
					reg[rd] = reg[rs1] ^ reg[rs2];
					break;
				}
				case 0b101: { 
					shamt = (reg[rs2] & 0b11111);
					if (funct7 == 0) { //SRL
						reg[rd] = reg[rs1] >>> shamt;
					} else if (funct7 == 0b0100000) { //SRA
						reg[rd] = reg[rs1] >> shamt;
					}
					else {
						System.out.println("Unsupported R-type (invalid funct7)");
					}
					break;
				}
				case 0b110: { //OR
					reg[rd] = reg[rs1] | reg[rs2];
					break;
				}
				case 0b111: { //AND
					reg[rd] = reg[rs1] & reg[rs2];
					break;
				}
				}
				break;
			}		
			
			// E-Call
			case 0b1110011: {
				if (instr != 0b00000000000000000000000001110011) {
					System.out.println("Ecall has wrong upper bits");
					break;
				}
				switch (reg[10]) {
				// Prints int in a1
				case 1: {
					System.out.println(reg[11]);
					break;
				}
				
				// Prints string at mem address a1
				case 4: {
					int byteLoc = reg[11];
					int eff_addr = byteLoc/4;
					int val = mem [eff_addr];
					String s = "";
					char ch = 0;
					int o = (byteLoc % 4) * 8;
					do {
						if (o == 32) {
							eff_addr++;
							val = mem [eff_addr];
							o = 0;
						}
						ch = (char)((val >>> o) & 0xff) ;
						s += ch;
						o += 8;
					} while (ch != 0);
					System.out.println(s);
					break;
				}

				// Exits
				case 10: {
					break loop;
				}
				// Prints ascii char in a1
				case 11: {
					System.out.println(((char)reg[11]));
					break;
				}
				// Exits with error in a1
				case 17: {
					System.out.println("Return Code: " + reg[11]);
					break loop;
				}
				default: {
					System.out.println("ecall with ID " + reg[10] + " not yet implemented");
					break;
				}
				}
				break;
			}		
			
			default: {
				System.out.println("Opcode " + opcode + " not yet implemented");
				break;
			}
			}
			
			reg[0] = 0; // reset any values passed to x0
			if (!branch)++pc; // We count in 4 byte words
			else { // if branching increment by specified amount
				pc += imm/4;
				branch = !branch;
			}
//			if (pc >= prog.length) {
			if (pc >= progSize) {
				break loop;
			}
			for (int i = 0; i < reg.length; ++i) {
				System.out.print(reg[i] + " ");
			}
			System.out.println();
		}
		for (int i = 0; i < reg.length; ++i) {
			System.out.print(reg[i] + " ");
		}
		System.out.println();
		System.out.println("Program exit");
		writeOutput(reg, testFlag);
	}
	public static void writeOutput(int[] reg, String testFlag) {
		int lilEnd;
		if (testFlag != null) {
			if (testFlag.equals("test")) {
				reg[2]=0;
				reg[3]=0;
			}
			else if (testFlag.equals("test_show_sp")) {
				reg[3]=0;
			}
			else if (testFlag.equals("test_show_gp")) {
				reg[2]=0;
			}
		}
		byte[] ba = new byte[128];
		for (int i = 0; i < reg.length; i++) {
			lilEnd = reg[i];
			ba[4*i] = (byte) (lilEnd & 0xff);
			lilEnd >>= 8;
			ba[4*i+1] = (byte) (lilEnd & 0xff);
			lilEnd >>= 8;
			ba[4*i+2] = (byte) (lilEnd & 0xff);
			lilEnd >>= 8;
			ba[4*i+3] = (byte) lilEnd;
		}
		try {
			FileOutputStream stream = new FileOutputStream("output.res");
		    stream.write(ba);
		    stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * accepts and reads byte file storing 4 sequential bytes as integers into array 
	 * returns int array
	 */
	public static int[] readByteFile(String inFile) {
		try (
			InputStream inputStream = new FileInputStream(inFile);
		) {
			long fileSize = new File(inFile).length();
			byte[] fileBytes = new byte[ (int) fileSize];
			ByteBuffer byteBuffer = ByteBuffer.wrap(fileBytes);
			inputStream.read(fileBytes); // Read the byte file into the byte array
			
//			int[] instArr = new int[ (int) (fileSize/4)]; // integer array of instructions
	
			int[] instArr = new int[536870911];
			progSize =  (int) (fileSize/4);
			//loop through byte array and construct 32bit instructions
			for (int i = 0 ; i < fileSize; i+=4) {
				instArr[i/4] = byteBuffer.getInt();			
			}
			return instArr;

		}
		catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	

}