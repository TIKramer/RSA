import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RSA
{
	private long prime1;
	private long prime2;
	private long  n;
	private long  newN;
	private long e;
	private long d;


	//Get the modular inverse using the extending Euclidean
	public long getInverse(long a, long mod)
	{
		Euclidean  gcd = extendedEuclidean(a, mod);
		//Floor mod to fix issues with negative numbers
		return Math.floorMod(gcd.x,mod);
	}
	
	
	
	/* ExtendedEuclidean that returns gcd and previous values */
	public Euclidean extendedEuclidean(long a, long b)
	{
		//Two objects one stores values of previous call one for new
		 Euclidean euclidean1 = new Euclidean();
		 Euclidean euclidean2 = new Euclidean();
		 //Pointer to the Object that is return
		 Euclidean returnE= new Euclidean();
		 //Base case
		 if (b == 0) {
		  euclidean1.d = a;
		  euclidean1.x = 1;
		  euclidean1.y = 0;
		  returnE= euclidean1;
		 } else {
		  euclidean1 = extendedEuclidean(b, a % b);
		  //update values
		  euclidean2.d = euclidean1.d;
		  euclidean2.x = euclidean1.y;
		  euclidean2.y = euclidean1.x - ((int)(a / b)) * euclidean1.y;
		  returnE = euclidean2;
		 }
		 return returnE;
		}
	
	public RSA()
	{
		
		initalize();
	}

//Generates all the values used for encryption and decryption
	public void initalize()
	{
		 prime1 = getPrimes();
		 prime2 = getPrimes();
		 n = prime1 * prime2;
		 newN = (prime1-1)*(prime2-1);
		setE();
		System.out.println(e);
		long eInverse = getInverse(e, newN);
		d = eInverse%newN;
		
	}
	
	public void setE()
	{
		//+ one to not start at 0
		long tempE = (long) ((Math.random() * (newN-1)) + 1);
		while(gcd(tempE, newN) != 1)
		{
			tempE--;
			if(tempE==0)
			{
				tempE= newN-1;
			}
		}
		e = tempE;
	}
	
	
	public long getPrimes()
	{
		Random r = new Random();
		int bottomLimit = 1000;
		int upperLimit = 10000;
		int result = r.nextInt(upperLimit-bottomLimit) + bottomLimit; 
		while(!primeNumbers(result))
		{
			 result = r.nextInt(upperLimit-bottomLimit) + bottomLimit; 

		}
		return result;
	}
	public LinkedList<String> stringToAsci(String message)
	{
		LinkedList<String> asciValues = new LinkedList<String>();
		String ascii ="";
		//int [] ascii = new int[message.length()];
	    for (int i = 0; i < message.length(); i++) {
	    	String asci =  String.format("%03d", (int)message.charAt(i));
	    	asciValues.add(asci);
	    }
	    return asciValues;	
	    
	}
	
	
	
	public long powmod(long a, long e,long n)
	{
	String b = Long.toBinaryString(e);
	int k = b.length();

	long f =1;
	for(int i = 0; i < k; i++)
	{
		f = ((f*f)%n);
		if(b.charAt(i) == '1')
		{
			f = ((f*a) %n);
		}
	
	}
	  return f;
	}
	
	  private static long gcd(long e, long newN)
	    { 
		
		  while(newN > 0)
		    {
			  long c = e % newN;
		        e = newN;
		        newN = c;
		    }
		    return e;
		  
	    } 
	  
	
	public boolean primeNumbers(int testNum)
	{
		int a = 0;
		boolean prime = true;
		  int e = (testNum-1)/2;
		  int result;
	        int tries = 100;
	        
	        /** 1 and 0 not prime **/
	        if (testNum == 0 ||testNum == 1)
	            prime = false;
	        /** even numbers other than 2 are not prime **/
	        else if (testNum % 2 == 0)
	        {
	            prime = false;
	        }
	        /** check incase it is 2 - which is prime **/
	        else if (testNum == 2)
	        {
	            prime = true;
	        	tries = 0;
	        }
	        else
		        {
		        	while (tries!=0 && prime!=false)
			        {
		        		//Get new random number - recaculate and test
			            a = (int)(Math.random() * (testNum-1)) + 1;             
		               	tries--;
			  	 result = (int) powmod(a,e, testNum);
		
			  		 if(result != 1 && result != (testNum - 1))
			  		 {

				  		  prime = false;
			  		 }
			  		
		           
			        }
	        }
	        return prime;

	    }


  
/* Reads a file, encrpyts the file and saves the encrpyted file. */	
	public void encryptFile(String string)
	{

		FileReader reader = new FileReader(string);
		byte[] encoded = reader.getbArray();
		
		String s = new String(encoded, Charset.defaultCharset());
		String result = "";
		for(int i = 0; i < s.length(); i++)
		{
	    	String asci =  String.format("%03d", (int)s.charAt(i));
			long encrypt = powmod(Long.valueOf(asci),e,n);

			 result += encrypt+ " ";

		}
		reader.write(result, "encrypted.txt");
		
	}
/*MEthod reads a file that has been encrypted
 * Decrypts this file and produces a decrypted text file	
 */
	public void decryptFile(String string)
	{

		FileReader reader = new FileReader(string);

		byte[] encoded = reader.getbArray();
		 String s = new String(encoded, Charset.defaultCharset());
		//Encrypted files are split by spaces
		 String[] splited = s.split("\\s+");
		 
		 String decryptText = "";
	        for (int i = 0; i < splited.length; i++) {
	           	            
	        	long decrypt = powmod(Long.valueOf(splited[i]),d,n);
	        	decryptText += (char)decrypt;

	        }
	       
			reader.write(decryptText, "decryptedFile.txt");
		
	}
	
	

	

}
