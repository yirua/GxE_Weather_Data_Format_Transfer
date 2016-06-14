package Weather_Data_Transfer;

/**
 * this class will split string into an array of string
 * @author yiweisun
 *
 */
public class Tokenizer {
    public String[] result;
    public Tokenizer(String a)
    {
        result = a.split(",");
        if (result[0].contains(";")){
        	
        	result= a.split(";");
        }
    }
   
    public int time_to_int(int position)
    {
        String temp[] = result[position].split(":");
        int h = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);
        return h*60+m;
    }
    public int station(int position)
    {
        String temp[] = result[position].split(" ");
        return Integer.parseInt(temp[1]);
    }
}
