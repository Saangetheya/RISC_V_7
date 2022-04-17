# RISC_V_7
RISC_V_PROJECT
The PHASE-1.java is  simulation of RISC-V architecture which can implement bubble sort algorithm. The simulator supports instructions like add, sub, mul, addi, muli, mov, lw, sw, jmp, beq, bne etc. The bubble sort algorithm is attached as .txt file. PHASE-2 implements pipelining of only basic instructions like add, sub, mul, lw, addi, muli.
 Data forwarding isn't done because the time is consumed for implementing the pipelining. 
The pipelining is implemented in this way: 
First 4 clock cycles are implemented separately and from then, all the remaining cycles(after 4) are implemented in the pipelining in a similar way. The program will stop each phase based on a counter. Each pipeline stage/phase is implemented n times, where n is the no. of instructions. And along with this, GUI is also implemented.
