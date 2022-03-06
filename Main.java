import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Testing testing = new Testing();
        File file = new File("BubbleSort.txt");
        Scanner Reader = new Scanner(file);
        String[] final_array = new String[10];
        for(int i=0; i<10; i++){
            final_array[i] = "1";
        }

        ArrayList<String> final_array1 = new ArrayList<String>();
        boolean onlytext=false;
        String ch ="x";
        while (Reader.hasNextLine()){
            String x = Reader.nextLine();
            x = x.replace("#", " # ");
            String[] xc = x.split(" ");
            
            int i=0;
            for(i=0; i<xc.length; i++){
                if(!xc[i].equals("")){
                    if(xc[i].equals("#") && !(ch.equals("x"))){
                        break;
                    }else if(xc[i].equals("#")){
                        ch = "comment";
                        break;
                    }
                    ch = xc[i];
                    break;
                }
            }
            if(ch.equals("comment")){
                Memory.pc++;
                continue;
            }else{
                if(ch.equals(null)||ch.equals("x")){
                    continue;
                }
                if(ch.equals(".text")){
                    onlytext=true;
                }
                boolean label = false;
                StringBuilder stringBuilder = new StringBuilder("");
                if(onlytext&&ch.charAt(ch.length() - 1) == ':') {
                    label = true;
                    StringBuilder str = new StringBuilder(ch);
                    ch=str.deleteCharAt(str.length()-1).toString();
                    Hashmap.labelHash.put(ch,Memory.pc);
                    xc[i]="";
                    for (int f=0;f<xc.length;++f){
                        stringBuilder.append(xc[f]);
                        stringBuilder.append(" ");
                    }
                }
                if(label) {
                    Hashmap.pcHash.put(Memory.pc,stringBuilder.toString());
                }
                else{
                    Hashmap.pcHash.put(Memory.pc,x);
                }
                Memory.pc++;
            }
        }

        Instruction.counter = Memory.pc;
        Memory.pc=0;
        int index = 0;
        int TextFound = 0;
        int DataFound = 0;
        while(Memory.pc< Instruction.counter){
            int a = index;
            String data = Hashmap.pcHash.get(Memory.pc);
            data = data.replace("#", " # ");
            String[] check = data.split(" ");
            String final_check = "0";//
            for(int i=0; i<check.length; i++){
                if(!check[i].equals("")){
                    final_check = check[i];
                    break;
                }
            }
            if(final_check.equals(null) || final_check.equals("0") || final_check.equals("#")){
                Memory.pc++;
                continue;
            }
            else if(final_check.equals(".text")){
                TextFound = 1;
            }
            else if(DataFound == 1 && TextFound == 0){
                data = data.replace(",", " ");
                data = data.replace(":", " ");
                data = data.replace("\t"," ");
                String[] array = data.split(" ");
                for(int i=0; i<array.length; i++){
                    if(!array[i].equals("")){
                        final_array1.add(array[i]);
                    }
                }
                String s = final_array1.get(0);
                Hashmap.memHash.put(s, Memory.i);
                if(final_array1.get(1).equals(".word")){
                    for(int i=2; i<final_array1.size();i++){
                        int x = Integer.parseInt(final_array1.get(i));
                        Memory.Mem[Memory.i]=(byte)(x>>24);
                        Memory.Mem[Memory.i + 1]=(byte)(x>>16);
                        Memory.Mem[Memory.i + 2]=(byte)(x>>8);
                        Memory.Mem[Memory.i + 3]=(byte)(x);
                        Memory.i+=4;
                    }
                }
                else if(final_array1.get(1).equals(".byte")){
                    for(int i=2; i<final_array1.size();i++){
                        int x = Integer.parseInt(final_array1.get(i));
                        Memory.Mem[Memory.i]=(byte)(x);
                        Memory.i++;
                    }
                }
                final_array1.clear();
            }
            else if(final_check.equals(".data")){
                DataFound = 1;
            }
            else if(TextFound == 1){
                index=0;
                data = data.replace(",", " ");
                data = data.replace("(", " ");
                data = data.replace(")", " ");
                data = data.replace("\t"," ");
                String[] array = data.split(" ");
                for(int i=0; i<array.length; i++){
                    if(!array[i].equals("")){
                        if(array[i].equals("#")){
                            break;
                        }
                        final_array[index] = array[i];
                        index++;
                    }
                }
                System.out.println(final_array[0]);
                Instruction in = Hashmap.insHash.get(final_array[0]);
                in.Op(final_array[1], final_array[2], final_array[3]);
                index = a + 4;
            }
            Memory.pc++;
        }

        Testing.printRegisters();
        Memory.printMemory();
    }
}

class Register{
    int regCode;
    int regInt;
    Register(int regCode,int regInt){
        this.regCode=regCode;
        this.regInt=regInt;
    }
}

class Testing {
    static Register r0 = new Register(0,0);
    static Register r1 = new Register(1,0);
    static Register r2 = new Register(2,0);
    static Register r3 = new Register(3,0);
    static Register r4 = new Register(4,0);
    static Register r5 = new Register(5,0);
    static Register r6 = new Register(6,0);
    static Register r7 = new Register(7,0);
    static Register r8 = new Register(8,0);
    static Register r9 = new Register(9,0);
    static Register r10 = new Register(10,0);
    static Register r11 = new Register(11,0);
    static Register r12 = new Register(12,0);
    static Register r13 = new Register(13,0);
    static Register r14 = new Register(14,0);
    static Register r15 = new Register(15,0);
    static Register r16 = new Register(16,0);
    static Register r17 = new Register(17,0);
    static Register r18 = new Register(18,0);
    static Register r19 = new Register(19,0);
    static Register r20 = new Register(20,0);
    static Register r21 = new Register(21,0);
    static Register r22 = new Register(22,0);
    static Register r23 = new Register(23,0);
    static Register r24 = new Register(24,0);
    static Register r25 = new Register(25,0);
    static Register r26 = new Register(26,0);
    static Register r27 = new Register(27,0);
    static Register r28 = new Register(28,0);
    static Register r29 = new Register(29,0);
    static Register r30 = new Register(30,0);
    static Register r31 = new Register(31,0);
    /*-----------------------Arithmetic code 0---------------------------*/
    static Instruction add = new Instruction(0) {
        @Override
        void Op(String a, String b, String c) {
            int B = Hashmap.regHash.get(b).regInt;
            int C = Hashmap.regHash.get(c).regInt;
            int A = B + C;
            Hashmap.regHash.get(a).regInt=A;
        }
    };

    static Instruction sub = new Instruction(0) {
        @Override
        void Op(String a, String b, String c) {
            int B = Hashmap.regHash.get(b).regInt;
            int C = Hashmap.regHash.get(c).regInt;
            int A = B - C;
            Hashmap.regHash.get(a).regInt=A;
        }
    };
    static Instruction addi = new Instruction(0){
        @Override
        void Op(String a, String b, String c){
            int B = Hashmap.regHash.get(b).regInt;
            int C = Integer.parseInt(c);
            Hashmap.regHash.get(a).regInt = B + C;
        }
    };
    static Instruction mul = new Instruction(0){
        @Override
        void Op(String a, String b, String c){
            int B = Hashmap.regHash.get(b).regInt;
            int C = Hashmap.regHash.get(c).regInt;
            int A = B * C;
            Hashmap.regHash.get(a).regInt=A;
        }
    };
    /*-----------------------Data transfer code 1---------------------------*/
    static Instruction lw = new Instruction(1) {
        @Override
        void Op(String a, String b, String c) {
            int B,X,C;
            try {
                B = Integer.parseInt(b);
                C = Hashmap.regHash.get(c).regInt;
                X = C + B;
            }
            catch (NumberFormatException e) {
                B=Hashmap.memHash.get(b);
                C=0;
                if(Hashmap.regHash.containsKey(c)){
                    C = Hashmap.regHash.get(c).regInt;
                }
                X = B + C;
            }
            int A = Memory.Mem[X] << 24 | (Memory.Mem[X+1] & 0xFF) << 16 | (Memory.Mem[X+2] & 0xFF) << 8 | (Memory.Mem[X+3] & 0xFF);
            Hashmap.regHash.get(a).regInt= A;
        }
    };
    static Instruction sw = new Instruction(1) {
        @Override
        void Op(String a, String b, String c) {
            int B,X,C;
            try {
                B = Integer.parseInt(b);
                C = Hashmap.regHash.get(c).regInt;
                X = C + B;
            }
            catch (NumberFormatException e) {
                B=Hashmap.memHash.get(b);
                C=0;
                if(Hashmap.regHash.containsKey(c)){
                    C = Hashmap.regHash.get(c).regInt;
                }
                X = B + C;
            }
            int A = Hashmap.regHash.get(a).regInt;
            Memory.Mem[X]=(byte)(A>>24);
            Memory.Mem[X+1]=(byte)(A>>16);
            Memory.Mem[X+2]=(byte)(A>>8);
            Memory.Mem[X+3]=(byte)(A);
        }
    };
    /*-----------------------Data transfer code 2---------------------------*/
    static Instruction mv = new Instruction(2) {
        @Override
        void Op(String a, String b, String c) {
            int B = Hashmap.regHash.get(b).regInt;
            Hashmap.regHash.get(a).regInt=B;
        }
    };
    static Instruction li = new Instruction(2) {
        @Override
        void Op(String a, String b, String c) {
            int B = Integer.parseInt(b);
            Hashmap.regHash.get(a).regInt=B;
        }
    };
    static Instruction la = new Instruction(2) {
        @Override
        void Op(String a, String b, String c){
            int address = Hashmap.memHash.get(b);
            Hashmap.regHash.get(a).regInt = address;
        }
    };
    /*-----------------------Unconditional jump code 3---------------------------*/
    static Instruction j = new Instruction(3) {
        @Override
        void Op(String a, String b, String c) {
            int pcn = Hashmap.labelHash.get(a);
            Memory.pc=pcn-1;
        }
    };
    static Instruction jr = new Instruction(3) {
        @Override
        void Op(String a, String b, String c) {
            int A = Hashmap.regHash.get(a).regInt;
            Memory.pc=A-1;
        }
    };
    static Instruction jal = new Instruction(3) {
        @Override
        void Op(String a, String b, String c) {
            int A = Hashmap.labelHash.get(a);
            r31.regInt=Memory.pc+1;
            Memory.pc=A-1;
        }
    };
    /*-----------------------Conditional Branch code 4---------------------------*/
    static Instruction beq = new Instruction(4) {
        @Override
        void Op(String a, String b, String c) {
            int A = Hashmap.regHash.get(a).regInt;
            int B;
            try {
                B=Integer.parseInt(b);
            }
            catch (NumberFormatException e){
                B = Hashmap.regHash.get(b).regInt;
            }
            int C = Hashmap.labelHash.get(c);
            if(A==B){
                Memory.pc=C-1;
            }
        }
    };
    static Instruction bne = new Instruction(4) {
        @Override
        void Op(String a, String b, String c) {
            int A = Hashmap.regHash.get(a).regInt;
            int B;
            try {
                B=Integer.parseInt(b);
            }
            catch (NumberFormatException e){
                B = Hashmap.regHash.get(b).regInt;
            }
            int C = Hashmap.labelHash.get(c);
            if(A!=B){
                Memory.pc=C-1;
            }
        }
    };
    static Instruction bgt = new Instruction(4) {
        @Override
        void Op(String a, String b, String c) {
            int A = Hashmap.regHash.get(a).regInt;
            int B;
            try {
                B=Integer.parseInt(b);
            }
            catch (NumberFormatException e){
                B = Hashmap.regHash.get(b).regInt;
            }
            int C = Hashmap.labelHash.get(c);
            if(A>B){
                Memory.pc=C-1;
            }
        }
    };
    static Instruction bge = new Instruction(4) {
        @Override
        void Op(String a, String b, String c) {
            int A = Hashmap.regHash.get(a).regInt;
            int B;
            try {
                B=Integer.parseInt(b);
            }
            catch (NumberFormatException e){
                B = Hashmap.regHash.get(b).regInt;
            }
            int C = Hashmap.labelHash.get(c);
            if(A>=B){
                Memory.pc=C-1;
            }
        }
    };
    static Instruction blt = new Instruction(4) {
        @Override
        void Op(String a, String b, String c) {
            int A = Hashmap.regHash.get(a).regInt;
            int B;
            try {
                B=Integer.parseInt(b);
            }
            catch (NumberFormatException e){
                B = Hashmap.regHash.get(b).regInt;
            }
            int C = Hashmap.labelHash.get(c);
            if(A<B){
                Memory.pc=C-1;
            }
        }
    };
    static Instruction ble = new Instruction(4) {
        @Override
        void Op(String a, String b, String c) {
            int A = Hashmap.regHash.get(a).regInt;
            int B;
            try {
                B=Integer.parseInt(b);
            }
            catch (NumberFormatException e){
                B = Hashmap.regHash.get(b).regInt;
            }
            int C = Hashmap.labelHash.get(c);
            if(A<=B){
                Memory.pc=C-1;
            }
        }
    };
    /*-----------------------System Calls code 5---------------------------*/
    static Instruction syscall = new Instruction(5) {
        @Override
        void Op(String a, String b, String c) {
            int sys = Hashmap.regHash.get("v0").regInt;
            if(sys==10){
                Memory.pc=Instruction.counter;
            }
        }
    };

    Testing(){
        Hashmap.regHash.put("r0",r0);
        Hashmap.regHash.put("at",r1);
        Hashmap.regHash.put("v0",r2);
        Hashmap.regHash.put("at",r3);
        Hashmap.regHash.put("a0",r4);
        Hashmap.regHash.put("a1",r5);
        Hashmap.regHash.put("a2",r6);
        Hashmap.regHash.put("at",r7);
        Hashmap.regHash.put("t0",r8);
        Hashmap.regHash.put("t1",r9);
        Hashmap.regHash.put("t2",r10);
        Hashmap.regHash.put("t3",r11);
        Hashmap.regHash.put("t4",r12);
        Hashmap.regHash.put("t5",r13);
        Hashmap.regHash.put("t6",r14);
        Hashmap.regHash.put("t7",r15);
        Hashmap.regHash.put("s0",r16);
        Hashmap.regHash.put("s1",r17);
        Hashmap.regHash.put("s2",r18);
        Hashmap.regHash.put("s3",r19);
        Hashmap.regHash.put("s4",r20);
        Hashmap.regHash.put("s5",r21);
        Hashmap.regHash.put("s6",r22);
        Hashmap.regHash.put("s7",r23);
        Hashmap.regHash.put("t8",r24);
        Hashmap.regHash.put("t9",r25);
        Hashmap.regHash.put("k0",r26);
        Hashmap.regHash.put("k1",r27);
        Hashmap.regHash.put("gp",r28);
        Hashmap.regHash.put("sp",r29);
        Hashmap.regHash.put("s8",r30);
        Hashmap.regHash.put("ra",r31);

        Hashmap.insHash.put("add",add);
        Hashmap.insHash.put("sub",sub);
        Hashmap.insHash.put("addi",addi);
        Hashmap.insHash.put("lw",lw);
        Hashmap.insHash.put("sw",sw);
        Hashmap.insHash.put("li",li);
        Hashmap.insHash.put("mv",mv);
        Hashmap.insHash.put("j",j);
        Hashmap.insHash.put("jr",jr);
        Hashmap.insHash.put("jal",jal);
        Hashmap.insHash.put("la", la);
        Hashmap.insHash.put("beq",beq);
        Hashmap.insHash.put("bne",bne);
        Hashmap.insHash.put("bge",bge);
        Hashmap.insHash.put("bgt",bgt);
        Hashmap.insHash.put("blt", blt);
        Hashmap.insHash.put("ble",ble);
        Hashmap.insHash.put("syscall",syscall);
    }

    public static void printRegisters(){
        System.out.println("   Register Name      Value in Register");
        System.out.println("      R0  (r0)              " + r0.regInt);
        System.out.println("      R1  (at)              " + r1.regInt);
        System.out.println("      R2  (v0)              " + r2.regInt);
        System.out.println("      R3  (v1)              " + r3.regInt);
        System.out.println("      R4  (a0)              " + r4.regInt);
        System.out.println("      R5  (a1)              " + r5.regInt);
        System.out.println("      R6  (a2)              " + r6.regInt);
        System.out.println("      R7  (a3)              " + r7.regInt);
        System.out.println("      R8  (t0)              " + r8.regInt);
        System.out.println("      R9  (t1)              " + r9.regInt);
        System.out.println("      R10 (t2)              " + r10.regInt);
        System.out.println("      R11 (t3)              " + r11.regInt);
        System.out.println("      R12 (t4)              " + r12.regInt);
        System.out.println("      R13 (t5)              " + r13.regInt);
        System.out.println("      R14 (t6)              " + r14.regInt);
        System.out.println("      R15 (t7)              " + r15.regInt);
        System.out.println("      R16 (s0)              " + r16.regInt);
        System.out.println("      R17 (s1)              " + r17.regInt);
        System.out.println("      R18 (s2)              " + r18.regInt);
        System.out.println("      R19 (s3)              " + r19.regInt);
        System.out.println("      R20 (s4)              " + r20.regInt);
        System.out.println("      R21 (s5)              " + r21.regInt);
        System.out.println("      R22 (s6)              " + r22.regInt);
        System.out.println("      R23 (s7)              " + r23.regInt);
        System.out.println("      R24 (t8)              " + r24.regInt);
        System.out.println("      R25 (t9)              " + r25.regInt);
        System.out.println("      R26 (k0)              " + r26.regInt);
        System.out.println("      R27 (k1)              " + r27.regInt);
        System.out.println("      R28 (gp)              " + r28.regInt);
        System.out.println("      R29 (sp)              " + r29.regInt);
        System.out.println("      R30 (s8)              " + r30.regInt);
        System.out.println("      R31 (ra)              " + r31.regInt);
    }

}

class Memory{
    static byte[] Mem = new byte[4096];
    static int i = 0;
    static int pc = 0;
    static void printMemory(){
        System.out.println("--------------------Memory--------------------");
        for(int j=0; j<i; j=j+4){
            int A = Memory.Mem[j] << 24 | (Memory.Mem[j+1] & 0xFF) << 16 | (Memory.Mem[j+2] & 0xFF) << 8 | (Memory.Mem[j+3] & 0xFF);
            String s = Integer.toHexString(j);
            System.out.print("0x");
            for(int k=0; k<(8-s.length()); k++){
                if(s.equals("0")){
                    System.out.print("0000000");
                    break;
                }else{
                    System.out.print("0");
                }
            }
            System.out.print(s + "        -------->        ");
            System.out.println(A);
        }
    }
}
abstract class Instruction{
    int inc;
    Instruction(int t){
        inc=t;
    }
    abstract void Op(String a, String b, String c);
    static int counter=0;
}

class Hashmap {
    static HashMap<String,Register> regHash = new HashMap<>();
    static HashMap<String,Instruction> insHash = new HashMap<>();
    static HashMap<String,Integer> memHash = new HashMap<>();
    static HashMap<Integer,String> pcHash = new HashMap<>();
    static HashMap<String,Integer> labelHash = new HashMap<>();
}
