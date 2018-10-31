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

	// Here the first program hard coded as an array
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
			
			// J-Type
			case 0b1101111: {
				rd = (instr >> 7) & 0b11111;
				imm = (((instr >> 21) & 0b11111111111) << 1) + (((instr >> 20) & 0b1) << 11) + (((instr >> 12) & 0b11111111) << 12) + (((instr >> 31) & 0b1) << 20); 
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
				imm = (((instr >> 8) & 0b1111) << 1) + (((instr >> 25) & 0b111111) << 5) + (((instr >> 7) & 0b1) << 11) + (((instr >> 31) & 0b1) << 12); 
				// Add functionality
				System.out.println("Add B-Type Functionality");
				break;
			}
			
			// Loads (Normal I-Type)
			case 0b0000011: {
				rd = (instr >> 7) & 0b11111;
				funct3 = (instr >> 12) & 0b111;
				rs1 = (instr >> 15) & 0b11111;
				imm = (instr >> 20);
				// Add functionality
				System.out.println("Add Loads Functionality");
				break;
			}
			
			// S-Type
			case 0b0100011: {
				funct3 = (instr >> 12) & 0b111;
				rs1 = (instr >> 15) & 0b11111;
				rs2 = (instr >> 20) & 0b11111;
				imm = ((instr >> 7) & 0b11111) + (((instr >> 25) & 0b1111111) << 5);
				// Add functionality
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
					if (funct3 == 0b001) { //SLLI
						reg[rd] = reg[rs1] << shamt;
					}
					else if (funct3 == 0b101 && funct7 == 0){ //SRLI
						reg[rd] = reg[rs1] >>> shamt;
					}
					else { //SRAI
						reg[rd] = reg[rs1] >> shamt;
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

			++pc; // We count in 4 byte words
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