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
