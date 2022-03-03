import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("code.txt");
        Scanner sc = new Scanner(file);
        String code="";
        Init init = new Init();
        while (sc.hasNextLine()){
            code=sc.nextLine();
            String A[] = code.split(" ",2);
            Instruction i = Hashmap.insHash.get(A[0]);
            String B[] = A[1].split(",");
            i.Op(B[0],B[1],B[2]);
        }
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
class Init{
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
    static Instruction add = new Instruction(0) {
        @Override
        void Op(String a, String b, String c) {
            int B = Hashmap.regHash.get(b).regInt;
            int C = Hashmap.regHash.get(c).regInt;
            int A = B + C;
            Hashmap.regHash.get(a).regInt=A;
        }
    };
    static Instruction sub = new Instruction(1) {
        @Override
        void Op(String a, String b, String c) {
            int B = Hashmap.regHash.get(b).regInt;
            int C = Hashmap.regHash.get(c).regInt;
            int A = B - C;
            Register x = Hashmap.regHash.get(a);
            x.regInt=A;
        }
    };
    static Instruction lw = new Instruction(2) {
        @Override
        void Op(String a, String b, String c) {
            int B = Hashmap.regHash.get(b).regInt;
            int C = Hashmap.regHash.get(c).regInt;
            int A = B - C;
            Register x = Hashmap.regHash.get(a);
            x.regInt=A;
        }
    };
    Init(){
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
    }

}

class Memory{
    static Object[] Mem = new Object[4096];
    Memory(){

    }
}
abstract class Instruction{
    int inc;
    Instruction(int t){
        inc=t;
    }
    abstract void Op(String a,String b,String c);
}
class Hashmap {
    static HashMap<String,Register> regHash = new HashMap<>();
    static HashMap<String,Instruction>insHash = new HashMap<>();
}
