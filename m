public class test2 {
    public static void main(String args[]){
        String s = "add  t1 t2 t3";
        String[] array = s.split(" ");
        String[] final_array = new String[4];
        int x=0;

        for(int i=0; i<array.length; i++){
            if(!array[i].equals("")){
                final_array[x] = array[i];
                System.out.println(final_array[x]);
                System.out.println("------");
                x++;
            }
        }
    }
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Register{
    public int num;
    public boolean check; // 0 if it's integer, 1 if it's address
}

class Memory{
    Object[] memory = new Object[4096];
}

class Simulator{
    Memory m = new Memory();
    public int add(int input1, int input2){
        return input1+input2;
    }
    public int sub(int input1, int input2){
        return input1-input2;
    }
    public int bne(int input1, int input2){
        if(input1 == input2){
            return 0;
        }else{
            return 1;
        }
    }
    public void lw(Register r1, Register rd, int offset){
        int rs = r1.num + offset;
        rd.num = (int)m.memory[rs];
    }
    public void sw(Register rt, Register base, int offset){
        int r = base.num + offset;
        m.memory[r] = rt.num;
    }

    public Register[] setOfRegisters = new Register[32];

    public static void main(String args[]){
        try{
            File file = new File("C:\\Users\\VISHNU VARDHAN\\OneDrive\\Desktop\\CO PROJECT RISC-V\\PHASE-1\\add.txt");
            Scanner Reader = new Scanner(file);
            while(Reader.hasNextLine()){
                String data = Reader.nextLine();
                String[] array = data.split(" ");
                String[] final_array = new String[4];
                int x=0;

                for(int i=0; i<array.length; i++){
                    if(!array[i].equals("")){
                        final_array[x] = array[i];
                        System.out.println(final_array[x]);
                        System.out.println("------");
                        x++;
                    }
                }

                if(final_array[0].equals("add")){
                    if(final.array[])
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("An error has occured");
            e.printStackTrace();
        }

    }
}