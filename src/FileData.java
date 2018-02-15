import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileData {
	
	public static void readFile() {
		File f = new File("savefile.txt");
		String fileName = "/Password Manager Java 2.0/savefile.txt";
		fileName = f.getAbsolutePath();
		String line = null;
		int j = 0;
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(file);
			while((line = bufferedReader.readLine()) != null) {
                Main.database[j][0] = Main.caesarDecrypt(Main.password, line.toCharArray());
                line = bufferedReader.readLine();
                Main.database[j][1] = Main.caesarDecrypt(Main.password, line.toCharArray());
                line = bufferedReader.readLine();
                Main.database[j][2] = Main.caesarDecrypt(Main.password, line.toCharArray());
                j++;
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
	}
}