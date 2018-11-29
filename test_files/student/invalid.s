jalr x0, x0, 0 # Invalid funct3
beq x0, x0, End # Invalid funct3
lb x0, 0(x0) # Invalid funct3
sb x0, 0(x0) # Invalid funct3
srli x0, x0, 1 # Invalid funct7
#Invalid opcode
xor x0, x0, x0 # Invalid funct7
add x0, x0, x0 # Invalid funct7
srl x0, x0, x0 # Invalid funct7
ecall # Invalid a0
#Invalid opcode
End: