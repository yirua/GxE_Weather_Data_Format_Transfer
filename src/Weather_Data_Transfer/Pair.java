package Weather_Data_Transfer;

import java.util.Objects;
/*
 * This class is build to make sure the string pairs are unique so that later we can detect which string combinations(descriptions + abbr.) are not in our list then add them into our table.
 */
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
	 
	 public int hashCode(){
	        return Objects.hashCode(prv)+Objects.hashCode(next);
	    }

	 public boolean equals(Object obj) {
	        Pair o1 = (Pair) obj;
	        return Objects.equals(o1.prv,this.prv) && Objects.equals(o1.next,this.next);
	    }
	
	}
