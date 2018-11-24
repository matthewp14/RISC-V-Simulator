addi a0, x0, 1
addi a1, x0, 29
ecall

addi a0, x0, 4
lui t0, 535
addi t0, t0, -1720
sw t0, 1(gp)
addi a1, gp, 1
ecall

addi a0, x0, 11
addi a1, x0, 33
ecall

addi a0, x0, 17
ecall