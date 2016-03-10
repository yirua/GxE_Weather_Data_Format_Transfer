package Weather_Data_Transfer;

public class Pair{
	 String prv;
	 String next;
	 String[] pairs= new String[2];
	 //override hashcode and equals
	 Pair(String prv, String next){
		 this.prv=prv;
		 this.next=next;
		 pairs[0]=this.prv;
		 pairs[1]=this.next;
	 }
	 
	 public String[] get_pair(){
		 return pairs;
	 }
	 
	 public String get_prv(){
		 return prv;
	 }
	 public String get_next(){
		 return next;
	 }
	}
