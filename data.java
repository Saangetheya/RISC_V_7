import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class data {
    public static void main(String args[]) throws FileNotFoundException{
        File file = new File("C:\\Users\\VISHNU VARDHAN\\OneDrive\\Desktop\\CO PROJECT RISC-V\\PHASE-1\\d.txt");
        Scanner Reader = new Scanner(file);
        ArrayList<String> final_array1 = new ArrayList<String>();
        int DataFound = 0;
        while(Reader.hasNextLine()){
            String data = Reader.nextLine();
            if(DataFound == 1){
                data = data.replace(",", " ");
                data = data.replace(":", " ");
                String[] array = data.split(" ");
                
                for(int i=0; i<array.length; i++){
                    if(!array[i].equals("")){
                        final_array1.add(array[i]);
                        // System.out.println(final_array);
                    }
                }


            }
            if(data.equals(".data")){
                DataFound = 1;
            }
        }
    }
}
