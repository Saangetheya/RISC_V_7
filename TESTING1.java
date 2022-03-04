import java.io.*;
import java.util.*;

class Register{
    public int num = 0;
    public String name;
    Register(String name){
        this.name = name;
    }
}

class Hash{
    public static HashMap<String, Register> map = new HashMap<>();
}

class Memory{
   static Object[] m = new Object[4096];
}

public class TESTING1{
    static Register R0 = new Register("r0");
    static Register R1 = new Register("at");
    static Register R2 = new Register("v0");
    static Register R3 = new Register("v1");
    static Register R4 = new Register("a0");
    static Register R5 = new Register("a1");
    static Register R6 = new Register("a2");
    static Register R7 = new Register("a3");
    static Register R8 = new Register("t0");
    static Register R9 = new Register("t1");
    static Register R10 = new Register("t2");
    static Register R11 = new Register("t3");
    static Register R12 = new Register("t4");
    static Register R13 = new Register("t5");
    static Register R14 = new Register("t6");
    static Register R15 = new Register("t7");
    static Register R16 = new Register("s0");
    static Register R17 = new Register("s1");
    static Register R18 = new Register("s2");
    static Register R19 = new Register("s3");
    static Register R20 = new Register("s4");
    static Register R21 = new Register("s5");
    static Register R22 = new Register("s6");
    static Register R23 = new Register("s7");
    static Register R24 = new Register("t8");
    static Register R25 = new Register("t9");
    static Register R26 = new Register("k0");
    static Register R27 = new Register("k1");
    static Register R28 = new Register("gp");
    static Register R29 = new Register("sp");
    static Register R30 = new Register("s8");
    static Register R31 = new Register("ra");

    public static void AssignRegisters(){
        Hash.map.put("r0", R0);
        Hash.map.put("at", R1);
        Hash.map.put("v0", R2);
        Hash.map.put("v1", R3);
        Hash.map.put("a0", R4);
        Hash.map.put("a1", R5);
        Hash.map.put("a2", R6);
        Hash.map.put("a3", R7);
        Hash.map.put("t0", R8);
        Hash.map.put("t1", R9);
        Hash.map.put("t2", R10);
        Hash.map.put("t3", R11);
        Hash.map.put("t4", R12);
        Hash.map.put("t5", R13);
        Hash.map.put("t6", R14);
        Hash.map.put("t7", R15);
        Hash.map.put("s0", R16);
        Hash.map.put("s1", R17);
        Hash.map.put("s2", R18);
        Hash.map.put("s3", R19);
        Hash.map.put("s4", R20);
        Hash.map.put("s5", R21);
        Hash.map.put("s6", R22);
        Hash.map.put("s7", R23);
        Hash.map.put("t8", R24);
        Hash.map.put("t9", R25);
        Hash.map.put("k0", R26);
        Hash.map.put("k1", R27);
        Hash.map.put("gp", R28);
        Hash.map.put("sp", R29);
        Hash.map.put("s8", R30);
        Hash.map.put("ra", R31);
    }

    public static int add(int input1, int input2){
        return input1+input2;
    }
    public static int sub(int input1, int input2){
        return input1-input2;
    }
    public static int bne(int input1, int input2){
        if(input1 == input2){
            return 0;
        }else{
            return 1;
        }
    }
    public static void lw(Register r1, Register rd, int offset){
        int rs = r1.num + offset;
        rd.num = (int)Memory.m[rs];
    }
    public static void sw(Register rt, Register base, int offset){
        int r = base.num + offset;
        Memory.m[r] = rt.num; 
    }
    public static void li(Register r, int value){
        r.num = value;
    }

    public static void printRegisters(){
        System.out.println("   Register Name      Value in Register");
        System.out.println("      R0  (r0)              " + R0.num);
        System.out.println("      R1  (at)              " + R1.num);
        System.out.println("      R2  (v0)              " + R2.num);
        System.out.println("      R3  (v1)              " + R3.num);
        System.out.println("      R4  (a0)              " + R4.num);
        System.out.println("      R5  (a1)              " + R5.num);
        System.out.println("      R6  (a2)              " + R6.num);
        System.out.println("      R7  (a3)              " + R7.num);
        System.out.println("      R8  (t0)              " + R8.num);
        System.out.println("      R9  (t1)              " + R9.num);
        System.out.println("      R10 (t2)              " + R10.num);
        System.out.println("      R11 (t3)              " + R11.num);
        System.out.println("      R12 (t4)              " + R12.num);
        System.out.println("      R13 (t5)              " + R13.num);
        System.out.println("      R14 (t6)              " + R14.num);
        System.out.println("      R15 (t7)              " + R15.num);
        System.out.println("      R16 (s0)              " + R16.num);
        System.out.println("      R17 (s1)              " + R17.num);
        System.out.println("      R18 (s2)              " + R18.num);
        System.out.println("      R19 (s3)              " + R19.num);
        System.out.println("      R20 (s4)              " + R20.num);
        System.out.println("      R21 (s5)              " + R21.num);
        System.out.println("      R22 (s6)              " + R22.num);
        System.out.println("      R23 (s7)              " + R23.num);
        System.out.println("      R24 (t8)              " + R24.num);
        System.out.println("      R25 (t9)              " + R25.num);
        System.out.println("      R26 (k0)              " + R26.num);
        System.out.println("      R27 (k1)              " + R27.num);
        System.out.println("      R28 (gp)              " + R28.num);
        System.out.println("      R29 (sp)              " + R29.num);
        System.out.println("      R30 (s8)              " + R30.num);
        System.out.println("      R31 (ra)              " + R31.num);
    }
    
    public static void main(String args[]){

        AssignRegisters();

        try{
            File file = new File("C:\\Users\\VISHNU VARDHAN\\OneDrive\\Desktop\\CO PROJECT RISC-V\\PHASE-1\\add.txt");
            Scanner Reader = new Scanner(file);
            String[] final_array = new String[10000];
            int index = 0;
            while(Reader.hasNextLine()){
                int a = index;
                String data = Reader.nextLine();
                data = data.replace(",", " ");
                data = data.replace("(", " ");
                data = data.replace(")", " ");
                String[] array = data.split(" ");

                for(int i=0; i<array.length; i++){
                    if(!array[i].equals("")){
                        final_array[index] = array[i];
                        index++;
                    }
                }
                index = a + 4;
            }
            for(int i=0; i < index; i= i+4){
                if(final_array[i].equals("add")){
                    int num1 = Hash.map.get(final_array[i+2]).num;
                    int num2 = Hash.map.get(final_array[i+3]).num;
                    int result = add(num1, num2);
                    Hash.map.get(final_array[i+1]).num = result;
                }else if(final_array[i].equals("li")){
                    int num = Integer.parseInt(final_array[i+2]);
                    li(Hash.map.get(final_array[i+1]), num);
                }else if(final_array[i].equals("addi")){
                    int num1 = Hash.map.get(final_array[i+2]).num;
                    int num2 = Integer.parseInt(final_array[i+3]);
                    int result = add(num1, num2);
                    Hash.map.get(final_array[i+1]).num = result;
                }else if(final_array[i].equals("sub")){
                    int num1 = Hash.map.get(final_array[i+2]).num;
                    int num2 = Hash.map.get(final_array[i+3]).num;
                    int result = sub(num1, num2);
                    Hash.map.get(final_array[i+1]).num = result;
                }else if(final_array[i].equals("lw")){
                    int offset = Integer.parseInt(final_array[i+2]);
                    int r = Hash.map.get(final_array[i+3]).num;
                    int address = offset + r;
                    int result = (int)Memory.m[address];
                    Hash.map.get(final_array[i+1]).num = result;
                }else if(final_array[i].equals("sw")){
                    int offset = Integer.parseInt(final_array[i+2]);
                    int r = Hash.map.get(final_array[i+3]).num;
                    int address = offset + r;
                    Memory.m[address] = Hash.map.get(final_array[i+1]).num;
                }
            }    
            printRegisters();
        }
        catch(FileNotFoundException e){
            System.out.println("An error has occured");
            e.printStackTrace();
        }   
    }
}
