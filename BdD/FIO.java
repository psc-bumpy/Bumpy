package BdD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FIO 
{
	FileWriter fw;
	FIO(String filename) throws IOException
	{
		File output = new File (filename+".csv");
		fw = new FileWriter (output);
		fw.write("Temps(s);Position roue(m");
		fw.write ("\r\n");
	}
	
	void add(double t,double x)
	{
		try
		{
			fw.write(Double.toString(t).replace(".",",")+";");
			fw.write(Double.toString(x).replace(".",",")+";");
			fw.write ("\r\n");  
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
	
	void close() throws IOException
	{
		fw.close();  
	}
}



