package src.simulator;
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
import java.util.*;
public class IsaSim {

	static int pc;
	static int reg[] = new int[32];

	// 4MB storage
	static int mem[] = new int[1000000];

	public static void main(String[] args) {
		reg[0] = 0;
		int prog[] = readByteFile(args[0]);
		System.out.println("Hello RISC-V World!");

		pc = 0;
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
		loop: while(true) {		
			// Little to Big Endian
			instr = Integer.reverseBytes(prog[pc]);
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
				System.out.println("Add AUIPC Functionality");
			}
			
			// J-Type
			case 0b1101111: {
				rd = (instr >> 7) & 0b11111;
				imm = (((instr >> 21) & 0b11111111111)) + (((instr >> 20) & 0b1) << 11) + (((instr >> 12) & 0b11111111) << 12) + (((instr >> 31) & 0b1) << 20); 
				// Add functionality
				System.out.println("Add J-Type Functionality");
				break;
			}
			
			// JALR (Normal I-Type)
			case 0b1100111: {
				rd = (instr >> 7) & 0b11111;
				funct3 = (instr >> 12) & 0b111;
				rs1 = (instr >> 15) & 0b11111;
				imm = (instr >> 20);
				// Add functionality
				System.out.println("Add JALR Functionality");
				break;
			}
			
			// B-Type
			case 0b1100011: {
				funct3 = (instr >> 12) & 0b111;
				rs1 = (instr >> 15) & 0b11111;
				rs2 = (instr >> 20) & 0b11111;
				imm = (((instr >> 8) & 0b1111) << 1) + (((instr >> 25) & 0b111111) << 5) + (((instr >> 7) & 0b1) << 11) + (((instr >> 31) & -1) << 12); 
				// Add functionality
				switch (funct3) {
				case 0b000: { // BEQ
					if (reg[rs1]== reg[rs2]) {
						branch = true; 
						break;
					}
				}
				case 0b001: { //BNE
					if (reg[rs1]!=reg[rs2]) {
						branch = true;
						break;
					}	
				}
				case 0b100: { // BLT
					if (reg[rs1] < reg[rs2]) {
						branch = true;
						break;
					}
				}
				case 0b101: { // BGE
					if (reg[rs1] >= reg[rs2]) {
						branch = true;
						break;
					}
				}
				case 0b110: { // BLTU
					if (Integer.compareUnsigned(reg[rs1], reg[rs2]) < 0 ) { // use Integer object to allow for unsigned comparison 
						branch = true;
						break;
					}
				}
				case 0b111: { //BGEU
					if (Integer.compareUnsigned(reg[rs1], reg[rs2])>=0) {
						branch = true;
						break;
					}
				}
				default: {
					System.out.println("B-Type with funct3" + funct3 + " not yet implemented");
					break;
				}
					
				}
				
				System.out.println("Add B-Type Functionality: Unsigned");
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
				// Add functionality
				switch (funct3) {
				case 0b000: { //LB
					// need logic to select the correct byte
					if ((imm+reg[rs1]) % 4 == 0) { // first byte at index 
						b = (byte)(mem[imm + reg[rs1]]); // by casting to byte it should sign extend automatically
						reg[rd] = (int)b;
					}
					else if((imm+reg[rs1]) % 4 == 1) { // second byte
						b = (byte)(mem[imm + reg[rs1]] >> 8);
						reg[rd] = (int)b;
					}
					else if((imm+reg[rs1]) % 4 == 2) {
						b = (byte)(mem[imm + reg[rs1]] >> 16);
						reg[rd] = (int)b;
					}
					else { // last byte
						b = (byte)(mem[imm + reg[rs1]] >> 24);
						reg[rd] = (int)b;
					}
				}
				case 0b001: { //LH
					//TODO: clarify whether or not the 16 bits must be located in one slot of array or if they can span 2
					if ((imm+reg[rs1]) % 4 == 0) { // lower 16 bits
						s = (short)(mem[imm + reg[rs1]]);
						reg[rd] = (int)s;
					}
					else { //upper 16 bits
						s = (short)(mem[imm + reg[rs1]] >> 16);
						reg[rd] = (int)s;
					}
				}
				case 0b010: { //LW
					//TODO: Clarify whether or not we need to check to see that these align correctly or if this is implied
					reg[rd] = mem[imm+reg[rs1]];
					break;
				}
				case 0b100: { //LBU
					//note, no need to type-cast here as this will result in signed extension
					if ((imm+reg[rs1]) % 4 == 0) { //first byte
						reg[rd] = (mem[imm + reg[rs1]]) & 0xff;
					}
					else if ((imm + reg[rs1]) % 4 == 1) { // second byte
						reg[rd] = (mem[imm + reg[rs1]] >> 8 ) & 0xff;
					}
					else if ((imm + reg[rs1]) % 4 == 2) { //third byte
						reg[rd] = (mem[imm + reg[rs1]] >> 16) & 0xff;
					}
					else {
						reg[rd] = (mem[imm + reg[rs1]] >> 24) & 0xff;
					}
				}
				case 0b101: { //LHU
					//no type casting to avoid signed representation
					if ((imm + reg[rs1]) % 4 == 0) { // lower 16 bits
						reg[rd] = (mem[imm + reg[rs1]]) & 0xffff;
					}
					else { // upper 16 bits
						reg[rd] = (mem[imm + reg[rs1]] >> 16) & 0xffff;
					}
				}
				}
				System.out.println("Add Loads Functionality");
				break;
			}
			
			// S-Type
			case 0b0100011: {
				funct3 = (instr >> 12) & 0b111;
				rs1 = (instr >> 15) & 0b11111;
				rs2 = (instr >> 20) & 0b11111;
				int b;
				imm = ((instr >> 7) & 0b11111) + (((instr >> 25) & 0b1111111) << 5);
				// Add functionality
				switch(funct3) {
				case 0b000: { //SB
					b = reg[rs2] & 0xff;
					if ((imm + reg[rs1]) % 4 == 0) { //first byte
						mem[imm + reg[rs1]] = ((mem[imm+reg[rs1]] & 0xffffff00) | b);
					}
					else if ((imm + reg[rs1]) % 4 == 1)  { // second byte
						mem[imm + reg[rs1]] = ((mem[imm+reg[rs1]] & 0xffff00ff) | (b << 8));
					}
					else if ((imm + reg[rs1]) % 4 == 2) { // third byte
						mem[imm + reg[rs1]] = ((mem[imm+reg[rs1]] & 0xff00ffff) | (b << 16));
					}
					else { // last byte
						mem[imm + reg[rs1]] = ((mem[imm+reg[rs1]] & 0x00ffffff) | (b << 24));
					}
					
				}
				case 0b001 : { //SH
					b = reg[rs2] & 0xffff;
					if ((imm + reg[rs1]) % 4 == 0) { // store in lower bits
						mem[imm + reg[rs1]] = ((mem[imm+reg[rs1]] & 0xffff0000) | b);
					}
					else { // upper 16 bits
						mem[imm + reg[rs1]] = ((mem[imm+reg[rs1]] & 0x0000ffff) | (b << 16));
					}
					
				}
				case 0b010: { //SW
					mem[imm + reg[rs1]] = reg[rs2]; 
				}
				}
				System.out.println("Add S-Type Functionality");
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
						} else { //SRAI
							reg[rd] = reg[rs1] >> shamt;
						}
						break;
					}
					}
		
				}
				
				// Normal I-Type
				else {
					imm = (instr >> 20);
					switch (funct3) {
					case 0b000: {
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
						System.out.println("Normal I-Type with funct3 " + funct3 + " not yet implemented");
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
				switch (funct3) {
				case 0b000: {
					// Add
					if (funct7 == 0) {
						reg[rd] = reg[rs1] + reg[rs2];
					}
					// Subtract
					else {
						reg[rd] = reg[rs1] - reg[rs2];
					}
					break;
				} 
				case 0b001: { //SLL
					shamt = (reg[rs2] & 0b11111);
					reg[rd] = reg[rs1] << shamt;
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
					} else { //SRA
						reg[rd] = reg[rs1] >> shamt;
					} break;
				}
				case 0b110: { //OR
					reg[rd] = reg[rs1] | reg[rs2];
					break;
				}
				case 0b111: { //AND
					reg[rd] = reg[rs1] & reg[rs2];
					break;
				}
				default: {
					System.out.println("R-Type with funct3 " + funct3 + " not yet implemented");
					break;
				}
				}
				break;
			}		
			
			// E-Call
			case 0b1110011: {
				switch (reg[10]) {
				// Prints int in a11
				case 1: {
					System.out.println("Hi" + reg[11]);
					break;
				}
				
				// Add ID = 4
				// Add ID = 9

				// Exits
				case 10: {
					break loop;
				}
				// Prints ascii char in a11
				case 11: {
					System.out.println(((char)reg[11]));
					break;
				}
				// Exits wit error in a1
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
			
			
			if (!branch)++pc; // We count in 4 byte words
			else { // if branching increment by specified amount
				pc += imm/4;
				branch = !branch;
			}
			if (pc >= prog.length) {
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
			
			int[] instArr = new int[ (int) (fileSize/4)]; // integer array of instructions
			
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