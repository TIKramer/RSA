import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RSA rsa = new RSA();

		Scanner sc = new Scanner(System.in);
		boolean run = true;
		while(run)
		{
			System.out.println("Please select what would u like to do "
					+ "\n 1: to encrpyt a text file"
					+ "\n 2: to decrpyt a text file"
					+ "\n 3 ex1t");
			int value = sc.nextInt();
			sc.nextLine();
			switch(value)
			{
			case 1: 
				System.out.println("Enter name of file to encrypt");
				rsa.encryptFile(sc.next());
				System.out.println("File is encrypted!");
				break;
			case 2: 
				System.out.println("Enter name of file to decrypt");
				rsa.decryptFile(sc.next());
				System.out.println("File is decrypted!");
				break;
			case 3: 			
				run = false;
				break;
			}
		}
				
	}

	
}
