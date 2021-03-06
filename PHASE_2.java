import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

// static class Op{
//     int add(int num1, int num2){
//         return num1+num2;
//     }
//     int sub(int num1, int num2){
//         return num1-num2;
//     }
//     int mul(int num1, int num2){
//         return num1*num2;
//     }
// }

public class PHASE_2 {

    public static void main(String[] args) throws IOException, FileNotFoundException {

        int IF_var = 0;
        int ID_RF_var = 0;
        int EX_var = 0;
        int MEM_var = 0;
        int WB_var = 0;
        int num1 = 0;
        int num2 = 0;
        String[] destination_reg = new String[100];
        int des_reg_index = 0;
        int EX_buffer;
        int MEM_buffer;
        int mem_location = -1;
        int[] result = new int[100];
        int res_var = 0;
        String oper = "0";
        int clock = 0;
        int IF_counter = 0;
        int ID_RF_counter = 0;
        int EX_counter = 0;
        int MEM_counter = 0;
        int WB_counter = 0;
        int No_Of_Ins = 0;
        String[] IF_array = new String[4];
        for(int i=0; i<4; i++){
            IF_array[i] = "0";
        }
        Testing testing = new Testing();
        File file = new File("basic.txt");
        Scanner Reader = new Scanner(file);
        String[] final_array = new String[10];
        for(int i=0; i<10; i++){
            final_array[i] = "1";
        }

        ArrayList<String> final_array1 = new ArrayList<String>();
        boolean onlytext=false;
        String ch ="x";
        while (Reader.hasNextLine()){
            StringBuilder stringBuilder1 = new StringBuilder(Reader.nextLine());
            if(stringBuilder1.isEmpty()){
                continue;
            }
            for (int i=0;i<stringBuilder1.length();i++){
                if(stringBuilder1.charAt(i)=='#'){
                    stringBuilder1.replace(i,stringBuilder1.length(),"");
                }
            }
            if(stringBuilder1.isEmpty()){
                continue;
            }
            String x = stringBuilder1.toString().replace("\t"," ");

            String[] xc = x.split(" ");

            int i=0;
            for(i=0; i<xc.length; i++){
                if(!xc[i].equals("")){
                    ch = xc[i];
                    break;
                }
            }
            if(ch == null ||ch.equals("x")){
                continue;
            }
            if(ch.equals(".text")){
                onlytext=true;
                Total_Instruction.begintext=Memory.pc;
            }
            boolean label = false;
            StringBuilder stringBuilder = new StringBuilder("");
            if(onlytext&&ch.charAt(ch.length() - 1) == ':') {
                label = true;
                StringBuilder str = new StringBuilder(ch);
                ch=str.deleteCharAt(str.length()-1).toString();
                Hashmap.labelHash.put(ch,Memory.pc);
                xc[i]="";
                for (String s : xc) {
                    stringBuilder.append(s);
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

        Total_Instruction.counter = Memory.pc;
        No_Of_Ins = Total_Instruction.counter - Total_Instruction.begintext - 1;
        Memory.pc=0;
        int index = 0;
        int TextFound = 0;
        int DataFound = 0;
        System.out.println("***" + Memory.pc + "***");
        System.out.println("&&&" + Total_Instruction.counter + "&&&");
        System.out.println("&&&" + Total_Instruction.begintext + "&&&");
        System.out.println(No_Of_Ins);
        while(Memory.pc< Total_Instruction.counter){
            //System.out.println(Memory.pc);
            int a = index;
            String data = Hashmap.pcHash.get(Memory.pc);
            String[] check = data.split(" ");
            String final_check = "0";
            for (String value : check) {
                if (!value.equals("")) {
                    final_check = value;
                    break;
                }
            }
            if(final_check.equals("0")){
                Memory.pc++;
                continue;
            }
            else if(final_check.equals(".text")){
                TextFound = 1;
            }
            else if(DataFound == 1 && TextFound == 0){
                // System.out.println(Memory.pc);
                data = data.replace(",", " ");
                data = data.replace(":", " ");
                String[] array = data.split(" ");
                for (String value : array) {
                    if (!value.equals("")) {
                        final_array1.add(value);
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
                while(Memory.pc < Total_Instruction.counter || WB_counter < No_Of_Ins){

                        if(Memory.pc < Total_Instruction.counter){
                            data = Hashmap.pcHash.get(Memory.pc);
                        }
                        index=0;
                        data = data.replace(",", " ");
                        data = data.replace("(", " ");
                        data = data.replace(")", " ");
                        String[] array = data.split(" ");
                        // System.out.println(data);
                    
                    // Instruction in = Hashmap.insHash.get(final_array[0]);
                    // in.Op(final_array[1], final_array[2], final_array[3]);
                    // index = a + 4;
                    if(IF_var == 0){
                        // Instruction Fetch
                        index = 0;
                        if(IF_counter<No_Of_Ins){
                            for(int i=0; i<array.length; i++){
                                if(!array[i].equals("")){
                                    if(array[i].equals("#")){
                                        break;
                                    }
                                    IF_array[index] = array[i];
                                    index++;
                                }
                                if(index == 4){
                                    break;
                                }
                            }
                            IF_counter++;
                            IF_var = 1;
                        }
                        
                    }
                    else if(ID_RF_var == 0){

                        // Instruction Decode / Register Fetch
                        if(ID_RF_counter < No_Of_Ins){
                            oper = IF_array[0];
                            System.out.println("EX_oper:" + oper);
                            System.out.println(IF_array[3]);
                            if(oper.equals("addi") || oper.equals("muli")){
                                num2 = Integer.parseInt(IF_array[3]);
                            }else if(oper.equals("lw") || oper.equals("sw")){
                                if(Hashmap.memHash.containsKey(IF_array[3])){
                                    num2 = Hashmap.memHash.get(IF_array[3]);
                                }else{
                                    num2 = Hashmap.regHash.get(IF_array[3]).regInt;
                                }
                            }
                            else if(oper.equals("add") || oper.equals("sub") || oper.equals("mul")){
                                num2 = Hashmap.regHash.get(IF_array[3]).regInt;
                            }
                            if(oper.equals("add") || oper.equals("sub") || oper.equals("mul") || oper.equals("addi") || oper.equals("muli")){
                                num1 = Hashmap.regHash.get(IF_array[2]).regInt;
                            }else if(oper.equals("lw") || oper.equals("sw")){
                                num1 = Integer.parseInt(IF_array[2]);
                            }
                            destination_reg[des_reg_index] = IF_array[1];
                            System.out.println(num1 + "***" + num2);
                            ID_RF_var = 1;
                            ID_RF_counter++;
                        }
                        // Instruction Fetch
                        index = 0;
                        if(IF_counter < No_Of_Ins){
                            for(int i=0; i<array.length; i++){
                                if(!array[i].equals("")){
                                    if(array[i].equals("#")){
                                        break;
                                    }
                                    IF_array[index] = array[i];
                                    index++;
                                }
                            }
                            IF_counter++;
                        }
                        // System.out.println("---------------");
                        
                    }else if(EX_var == 0){

                        // Execute
                        System.out.println("---------------");
                        if(EX_counter < No_Of_Ins){
                            if(oper.equals("add") || oper.equals("sub") || oper.equals("mul") || oper.equals("addi") || oper.equals("muli")){
                                result[res_var] = Hashmap.ALU_Hash.get(oper).Op(num1, num2);
                                System.out.println("add");
                            }
                            else{
                                result[res_var] = Hashmap.ALU_Hash.get(oper).Op(num1, num2);
                                mem_location = Hashmap.ALU_Hash.get(oper).Op(num1, num2);
                                System.out.println("lw");
                            }
                            EX_var = 1;
                            EX_counter++;
                        }

                        // Instruction Decode and Register Fetch
                        if(ID_RF_counter < No_Of_Ins){
                            oper = IF_array[0];
                            System.out.println("EX_oper:" + oper);
                            if(oper.equals("addi") || oper.equals("muli")){
                                num2 = Integer.parseInt(IF_array[3]);
                            }else if(oper.equals("lw") || oper.equals("sw")){
                                if(Hashmap.memHash.containsKey(IF_array[3])){
                                    num2 = Hashmap.memHash.get(IF_array[3]);
                                }else{
                                    num2 = Hashmap.regHash.get(IF_array[3]).regInt;
                                }
                            }
                            else if(oper.equals("add") || oper.equals("sub") || oper.equals("mul")){
                                num2 = Hashmap.regHash.get(IF_array[3]).regInt;
                            }
                            if(oper.equals("add") || oper.equals("sub") || oper.equals("mul") || oper.equals("addi") || oper.equals("muli")){
                                num1 = Hashmap.regHash.get(IF_array[2]).regInt;
                                
                            }else if(oper.equals("lw") || oper.equals("sw")){
                                num1 = Integer.parseInt(IF_array[2]);
                            }
                            destination_reg[des_reg_index+1] = IF_array[1];
                            // System.out.println(num1 + "***" + num2);
                            ID_RF_counter++;
                        }
                        // Instruction Fetch
                        index = 0;
                        if(IF_counter < No_Of_Ins){
                            for(int i=0; i<array.length; i++){
                                if(!array[i].equals("")){
                                    if(array[i].equals("#")){
                                        break;
                                    }
                                    IF_array[index] = array[i];
                                    index++;
                                }
                            }
                            IF_counter++;
                        }
                    }else if(MEM_var == 0){
                        //System.out.println("MEM");
                        // Memory
                        // System.out.println(MEM_counter);
                        // System.out.println(No_Of_Ins);
                        if(MEM_counter < No_Of_Ins){
                            if(mem_location != -1){
                                int X = mem_location;
                                result[res_var] = Memory.Mem[X] << 24 | (Memory.Mem[X+1] & 0xFF) << 16 | (Memory.Mem[X+2] & 0xFF) << 8 | (Memory.Mem[X+3] & 0xFF);
                            }
                            System.out.println("result[res_var]" + result[res_var] + "..."+ res_var);
                            MEM_counter++;
                            MEM_var = 1;
                            mem_location = -1;
                        }
                        System.out.println("HEY");
                        // Execute
                        if(EX_counter < No_Of_Ins){
                            // System.out.println("oper: " + oper);
                            if(oper.equals("add") || oper.equals("sub")|| oper.equals("mul") || oper.equals("addi") || oper.equals("muli")){
                                
                                result[res_var+1] = Hashmap.ALU_Hash.get(oper).Op(num1, num2);
                                System.out.println("NUM1" + num1 + "NUM2: " + num2);
                                System.out.println("Executing second instrruction.. add result[res_var] = " + result[res_var] + "res_var: " + res_var);
                            }
                            else{//System.out.println(num1 + "----" + num2);
                                result[res_var+1] = Hashmap.ALU_Hash.get(oper).Op(num1, num2);
                                System.out.println("Executing second instrruction.. lw result[res_var] = " + result[res_var] + "res_var: " + res_var);
                                mem_location = Hashmap.ALU_Hash.get(oper).Op(num1, num2);
                            }
                            
                            EX_counter++;
                        }
                        // Instruction Decode / Register Fetch
                        if(ID_RF_counter < No_Of_Ins){
                            oper = IF_array[0];
                            if(oper.equals("addi") || oper.equals("muli")){
                                num2 = Integer.parseInt(IF_array[3]);
                            }else if(oper.equals("lw") || oper.equals("sw")){
                                if(Hashmap.memHash.containsKey(IF_array[2])){
                                    num2 = Hashmap.memHash.get(IF_array[2]);
                                }else{
                                    num2 = Hashmap.regHash.get(IF_array[2]).regInt;
                                }
                            }
                            else if(oper.equals("add") || oper.equals("sub") || oper.equals("mul")){
                                num2 = Hashmap.regHash.get(IF_array[3]).regInt;
                            }
                            if(oper.equals("add") || oper.equals("sub") || oper.equals("mul") || oper.equals("addi") || oper.equals("muli")){
                                num1 = Hashmap.regHash.get(IF_array[2]).regInt;
                            }else if(oper.equals("lw") || oper.equals("sw")){
                                num1 = Integer.parseInt(IF_array[2]);
                            }
                            destination_reg[des_reg_index+2] = IF_array[1];

                            ID_RF_counter++;
                        }
                        // Instruction Fetch
                        index = 0;
                        if(IF_counter < No_Of_Ins){
                            for(int i=0; i<array.length; i++){
                                if(!array[i].equals("")){
                                    IF_array[index] = array[i];
                                    index++;
                                }
                            }
                            IF_counter++;
                        }
                    }else{
                        // System.out.println("WB");
                        // Write Back
                        if(WB_counter<No_Of_Ins){
                            // System.out.println(res_var);
                            // System.out.println("res_var: " + result[res_var]);
                            Hashmap.regHash.get(destination_reg[des_reg_index]).regInt = result[res_var];
                            System.out.println("RESULT:"+ result[res_var]);
                            res_var++;
                            des_reg_index++;
                            WB_counter++;
                        }
                        // Memory
                        if(MEM_counter < No_Of_Ins){
                            System.out.println("mem_location:"+ mem_location);
                            if(mem_location != -1){
                                int X = mem_location;
                                result[res_var] = Memory.Mem[X] << 24 | (Memory.Mem[X+1] & 0xFF) << 16 | (Memory.Mem[X+2] & 0xFF) << 8 | (Memory.Mem[X+3] & 0xFF);
                                // System.out.println(MEM_counter + "***" + result[res_var]);
                            }
                            MEM_counter++;
                            mem_location = -1;
                        }
                        // Execute
                        if(EX_counter < No_Of_Ins){
                            if(oper.equals("add") || oper.equals("sub") || oper.equals("mul") || oper.equals("addi") || oper.equals("muli")){
                                result[res_var+1] = Hashmap.ALU_Hash.get(oper).Op(num1, num2);
                                
                            }
                            else{
                                result[res_var+1] = Hashmap.ALU_Hash.get(oper).Op(num1, num2);
                                mem_location = Hashmap.ALU_Hash.get(oper).Op(num1, num2);
                                
                            }
                            EX_counter++;
                        }
                        // Instruction Decode / Register Fetch
                        if(ID_RF_counter < No_Of_Ins){
                            oper = IF_array[0];
                            System.out.println("EX_oper:" + oper);
                            if(oper.equals("addi") || oper.equals("muli")){
                                num2 = Integer.parseInt(IF_array[3]);
                            }else if(oper.equals("lw") || oper.equals("sw")){
                                if(Hashmap.memHash.containsKey(IF_array[3])){
                                    num2 = Hashmap.memHash.get(IF_array[3]);
                                }else{
                                    num2 = Hashmap.regHash.get(IF_array[3]).regInt;
                                }
                            }
                            else if(oper.equals("add") || oper.equals("sub") || oper.equals("mul")){
                                num2 = Hashmap.regHash.get(IF_array[3]).regInt;
                            }
                            if(oper.equals("add") || oper.equals("sub") || oper.equals("mul") || oper.equals("addi") || oper.equals("muli")){
                                num1 = Hashmap.regHash.get(IF_array[2]).regInt;
                                
                            }else if(oper.equals("lw") || oper.equals("sw")){
                                num1 = Integer.parseInt(IF_array[2]);
                            }
                            destination_reg[des_reg_index+2] = IF_array[1];
                            System.out.println(num1 + "***" + num2);
                            ID_RF_counter++;
                        }
                        // Instruction Fetch
                        index = 0;
                        if(IF_counter < No_Of_Ins){
                            for(int i=0; i<array.length; i++){
                                if(!array[i].equals("")){
                                    if(array[i].equals("#")){
                                        break;
                                    }
                                    IF_array[index] = array[i];
                                    index++;
                                }

                            }
                            IF_counter++;
                        }
                    }
                    System.out.println("clock:" + clock);
                    clock++;
                    System.out.println("EX_counter: " +EX_counter);
                    Memory.pc++;
                    System.out.println(No_Of_Ins);
                }
            }
            if(Memory.pc < Total_Instruction.counter){
                Memory.pc++;
            }
        }
        Testing.printRegisters();
        System.out.println("No. Of Clock cycles: " + clock);
        Memory.printMemory();
    }
}

// class Pipeline extends PHASE_1{

// }

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
    static ALU_Instruction add = new ALU_Instruction(0) {
        @Override
        int Op(int a, int b) {
            return a+b;
        }
    };

    static ALU_Instruction sub = new ALU_Instruction(0) {
        @Override
        int Op(int a, int b) {
            return a-b;
        }
    };
    // static Instruction addi = new Instruction(0){
    //     @Override
    //     void Op(String a, String b, String c){
    //         int B = Hashmap.regHash.get(b).regInt;
    //         int C = Integer.parseInt(c);
    //         Hashmap.regHash.get(a).regInt = B + C;
    //     }
    // };
    static ALU_Instruction mul = new ALU_Instruction(0){
        @Override
        int Op(int a, int b){
            return a*b;
        }
    };
    /*-----------------------Data transfer code 1---------------------------*/
    // static Instruction lw = new Instruction(1) {
    //     @Override
    //     int Op(int a, int b) {
    //         return a+b;
    //     }
    // };
    // static Instruction sw = new Instruction(1) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int B,X,C;
    //         try {
    //             B = Integer.parseInt(b);
    //             C = Hashmap.regHash.get(c).regInt;
    //             X = C + B;
    //         }
    //         catch (NumberFormatException e) {
    //             B=Hashmap.memHash.get(b);
    //             C=0;
    //             if(Hashmap.regHash.containsKey(c)){
    //                 C = Hashmap.regHash.get(c).regInt;
    //             }
    //             X = B + C;
    //         }
    //         int A = Hashmap.regHash.get(a).regInt;
    //         Memory.Mem[X]=(byte)(A>>24);
    //         Memory.Mem[X+1]=(byte)(A>>16);
    //         Memory.Mem[X+2]=(byte)(A>>8);
    //         Memory.Mem[X+3]=(byte)(A);
    //     }
    // };
    /*-----------------------Data transfer code 2---------------------------*/
    // static Instruction mv = new Instruction(2) {
    //     @Override
    //     int Op(int a,int b) {
    //         return b;
    //     }
    // };
    // static Instruction li = new Instruction(2) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int B = Integer.parseInt(b);
    //         Hashmap.regHash.get(a).regInt=B;
    //     }
    // };
    // static Instruction la = new Instruction(2) {
    //     @Override
    //     void Op(String a, String b, String c){
    //         int address = Hashmap.memHash.get(b);
    //         Hashmap.regHash.get(a).regInt = address;
    //     }
    // };
    // /*-----------------------Unconditional jump code 3---------------------------*/
    // static Instruction j = new Instruction(3) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int pcn = Hashmap.labelHash.get(a);
    //         Memory.pc=pcn-1;
    //     }
    // };
    // static Instruction jr = new Instruction(3) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int A = Hashmap.regHash.get(a).regInt;
    //         Memory.pc=A-1;
    //     }
    // };
    // static Instruction jal = new Instruction(3) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int A = Hashmap.labelHash.get(a);
    //         r31.regInt=Memory.pc+1;
    //         Memory.pc=A-1;
    //     }
    // };
    // /*-----------------------Conditional Branch code 4---------------------------*/
    // static Instruction beq = new Instruction(4) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int A = Hashmap.regHash.get(a).regInt;
    //         int B;
    //         try {
    //             B=Integer.parseInt(b);
    //         }
    //         catch (NumberFormatException e){
    //             B = Hashmap.regHash.get(b).regInt;
    //         }
    //         int C = Hashmap.labelHash.get(c);
    //         if(A==B){
    //             Memory.pc=C-1;
    //         }
    //     }
    // };
    // static Instruction bne = new Instruction(4) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int A = Hashmap.regHash.get(a).regInt;
    //         int B;
    //         try {
    //             B=Integer.parseInt(b);
    //         }
    //         catch (NumberFormatException e){
    //             B = Hashmap.regHash.get(b).regInt;
    //         }
    //         int C = Hashmap.labelHash.get(c);
    //         if(A!=B){
    //             Memory.pc=C-1;
    //         }
    //     }
    // };
    // static Instruction bgt = new Instruction(4) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int A = Hashmap.regHash.get(a).regInt;
    //         int B;
    //         try {
    //             B=Integer.parseInt(b);
    //         }
    //         catch (NumberFormatException e){
    //             B = Hashmap.regHash.get(b).regInt;
    //         }
    //         int C = Hashmap.labelHash.get(c);
    //         if(A>B){
    //             Memory.pc=C-1;
    //         }
    //     }
    // };
    // static Instruction bge = new Instruction(4) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int A = Hashmap.regHash.get(a).regInt;
    //         int B;
    //         try {
    //             B=Integer.parseInt(b);
    //         }
    //         catch (NumberFormatException e){
    //             B = Hashmap.regHash.get(b).regInt;
    //         }
    //         int C = Hashmap.labelHash.get(c);
    //         if(A>=B){
    //             Memory.pc=C-1;
    //         }
    //     }
    // };
    // static Instruction blt = new Instruction(4) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int A = Hashmap.regHash.get(a).regInt;
    //         int B;
    //         try {
    //             B=Integer.parseInt(b);
    //         }
    //         catch (NumberFormatException e){
    //             B = Hashmap.regHash.get(b).regInt;
    //         }
    //         int C = Hashmap.labelHash.get(c);
    //         if(A<B){
    //             Memory.pc=C-1;
    //         }
    //     }
    // };
    // static Instruction ble = new Instruction(4) {
    //     @Override
    //     void Op(String a, String b, String c) {
    //         int A = Hashmap.regHash.get(a).regInt;
    //         int B;
    //         try {
    //             B=Integer.parseInt(b);
    //         }
    //         catch (NumberFormatException e){
    //             B = Hashmap.regHash.get(b).regInt;
    //         }
    //         int C = Hashmap.labelHash.get(c);
    //         if(A<=B){
    //             Memory.pc=C-1;
    //         }
    //     }
    // };
    /*-----------------------System Calls code 5---------------------------*/
    // static Instruction ecall = new Instruction(5) {
    //     @Override
    //     String Op(String a, String b, String c) {
    //         int sys = Hashmap.regHash.get("v0").regInt;
    //         if(sys==10){
    //             Memory.pc=Instruction.counter;
    //         }
    //     }
    // };

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

        Hashmap.ALU_Hash.put("add",add);
        Hashmap.ALU_Hash.put("sub",sub);
        Hashmap.ALU_Hash.put("mul",mul);
        Hashmap.ALU_Hash.put("lw", add);
        Hashmap.ALU_Hash.put("sw", add);
        Hashmap.ALU_Hash.put("addi", add);
        Hashmap.ALU_Hash.put("muli", mul);

        Hashmap.CheckIns.put("add", "ALU");
        Hashmap.CheckIns.put("sub", "ALU");
        Hashmap.CheckIns.put("mul", "ALU");
        // Hashmap.insHash.put("lw",lw);
        // Hashmap.insHash.put("sw",sw);
        // Hashmap.insHash.put("li",li);i
        // Hashmap.insHash.put("mv",mv);
        // Hashmap.insHash.put("j",j);
        // Hashmap.insHash.put("jr",jr);
        // Hashmap.insHash.put("jal",jal);
        // Hashmap.insHash.put("la", la);
        // Hashmap.insHash.put("beq",beq);
        // Hashmap.insHash.put("bne",bne);
        // Hashmap.insHash.put("bge",bge);
        // Hashmap.insHash.put("bgt",bgt);
        // Hashmap.insHash.put("blt", blt);
        // Hashmap.insHash.put("ble",ble);
        // Hashmap.insHash.put("ecall",ecall);
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
abstract class ALU_Instruction{
    int inc;
    ALU_Instruction(int t){
        inc=t;
    }
    abstract int Op(int a, int b);
    static int counter=0;
}

abstract class Total_Instruction{
    int inc;
    Total_Instruction(int t){
        inc=t;
    }
    abstract int Op(int a, int b);
    static int counter=0;
    static int begintext=0;
}

class Hashmap {
    static HashMap<String,Register> regHash = new HashMap<>();
    static HashMap<String,ALU_Instruction> ALU_Hash = new HashMap<>();
    static HashMap<String,Integer> memHash = new HashMap<>();
    static HashMap<Integer,String> pcHash = new HashMap<>();
    static HashMap<String,Integer> labelHash = new HashMap<>();
    static HashMap<String,String> All_Ins = new HashMap<>();
    static HashMap<String,String> CheckIns = new HashMap<>();
}