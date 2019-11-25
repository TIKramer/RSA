import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class FileReader 
{
	private byte[] bArray;
	
	public byte[] getbArray() {
		return bArray;
	}

	public FileReader(String file) {
		// TODO Auto-generated method stub
		readFile(file);
		//c.).toBinaryString(character...)
		
		  
	}
	
	private  void readFile(String inFileName) 
    {
   
        Path path = Paths.get(inFileName);

        try
        {         
            bArray = Files.readAllBytes(path);

            
        }
        catch(Exception e)
        {
        	System.out.println("ERROR");
        }
    }
	
     public void write(String data, String fileName) {
        try {
            Files.write(Paths.get(fileName), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
