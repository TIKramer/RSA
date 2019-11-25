import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
	
		
		System.out.println("GCD of 12543, 1682");
		System.out.println("GCD: " + gcd(12543, 1682));
				
	}

  public static long gcd(long e, long newN)
	    { 
		
		  while(newN > 0)
		    {
			  long c = e % newN;
		        e = newN;
		        newN = c;
		    }
		    return e;
		  
	    } 
	  

	
}
