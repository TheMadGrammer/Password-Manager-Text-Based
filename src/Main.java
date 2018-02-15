import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	static String[][] database = new String[100][4];    //This is the array that stores the info until quit
	public static String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*?-_+=~`<>,./ ";    //set string for the alphabet for encryption
	public static char[] alphabet = string.toCharArray();
	public static Scanner scanner = new Scanner(System.in);	//Scanner for reading user input
	static File f = new File("savefile.txt");   //make the file object
	public static String path = f.getAbsolutePath();    //get file path (for use on different machines)
	public static char[] password;  //create password "string"

	public static void addAccount() { // the method for adding accounts
		
		String[] toAdd = new String[3];
		System.out.println("Enter your application name: ");
		toAdd[0] = scanner.nextLine();
		System.out.println("Enter your username: ");
		toAdd[1] = scanner.nextLine();
		System.out.println("Enter your password: ");
		toAdd[2] = scanner.nextLine();
		
		for (int i = 0; i < database.length; i++) {
			if (database[i][0] == null) {
				database[i][0] = toAdd[0];
				database[i][1] = toAdd[1];
				database[i][2] = toAdd[2];
				break;
			}
		}
		
	}
	public static void search() { // the method for searching
		String search;
		boolean found = false;
		System.out.println("Type in the name of the application you wish to search for:");
		search = scanner.nextLine();
		for (int i = 0; i < database.length; i++) {
			if (database[i][0].equals(search)) {
				System.out.println("Your account was found!");
				System.out.println("Your username is: " + database[i][1]);
				System.out.println("Your password is: " + database[i][2]);
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("The account you searched for was not found.");
		}
	}
	
	public static void delete() { //the method for deleting entries
		String search;
		boolean found = false;
		System.out.println("Type in the name of the application you wish to delete:");
		search = scanner.nextLine();
		for (int i = 0; i < database.length; i++) {
			if (database[i][0] != null && database[i][0].equals(search)) {
				System.out.println("Your account was found!");
				found = true;
				System.out.println("The username is: " + database[i][1]);
				System.out.println("The password is: " + database[i][2]);
				System.out.println("Are you sure that you want to delete this account? (y/n)");
				if (scanner.nextLine().equals("y")) {
					database[i][0] = null;
					database[i][1] = null;
					database[i][2] = null;
				} else {
					System.out.println("Delete Stopped!");
					break;
				}
				System.out.println("Delete Successful!");
				break;
			}
		}
		if (!found) {
		    System.out.println("The account you searched for was not found.");
        }
	}
	

	public static void update() { //the method for updating entries
		String search;
		System.out.println("Type in the name of the application you wish to update:");
		search = scanner.nextLine();
		for (int i = 0; i < database.length; i++) {
			if (database[i][0].equals(search)) {
				System.out.println("Your account was found!");
				System.out.println("Your username is: " + database[i][1]);
				System.out.println("Your password is: " + database[i][2]);
				System.out.println("Enter your new username: ");
				database[i][1] = scanner.nextLine();
				System.out.println("Enter your new password: ");
				database[i][2] = scanner.nextLine();
				break;
			}
		}
	}
	
	public static void write() throws IOException {
        FileWriter file = new FileWriter(path);
        BufferedWriter writer = new BufferedWriter(file);
        writer.write("");
        for (int i = 0; i < database.length; i++) {
            if (database[i][0] != null) {
                //caesarEncrypt();
                writer.append(caesarEncrypt(password, database[i][0].toCharArray()) + "\n");
                writer.append(caesarEncrypt(password, database[i][1].toCharArray()) + "\n");
                writer.append(caesarEncrypt(password, database[i][2].toCharArray()) + "\n");
            }
        }
        writer.close();
        file.close();
	}
	
	public static int getIndex(char c) {
		boolean found = false;
		int i = 0;
		while (true) {
			if (c == alphabet[i]) {
				found = true;
				break;
			}
			i++;
		}
		if (found == true) {
			return i;
		} else {
			return 0;
		}
	}
	
		public static int  wrapInt(int x) {
		while (x < 0) {
			x += alphabet.length;
		}
		while (x > alphabet.length - 1) {
			x -= alphabet.length;
		}
		return x;
	}
		
	public static char encryptChar(char c, int shift) {
		int location = getIndex(c);
		location += shift;
		location = wrapInt(location);
		return alphabet[location];
	}
	
	static String caesarEncrypt(char[] password, char[] phrase) {
		int key = 0;
		String encrypted = "";
		for (int i = 0; i < password.length; i++) {
			key += (int)password[i];
		}
		key = key % 26;

		for (int i = 0; i < phrase.length; i++) {
			encrypted += encryptChar(phrase[i], key);	}


		return encrypted;
	}
	
	public static String caesarDecrypt(char[] password, char[] phrase) {
		int key = 0;
		String decrypted = "";
		for (int i = 0; i < password.length; i++) {
			key += (int)password[i];
		}
		key = key % 26;
		key = key * -1;

		for (int i = 0; i < phrase.length; i++) {
			decrypted += encryptChar(phrase[i], key);
		}

		return decrypted;
	}

	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		String input = null;
		System.out.println("Please type in your password: ");
		password = (scanner.nextLine()).toCharArray();
		FileData.readFile();

		while (true) {
			System.out.println("Welcome to the Passworld Manager!");
			System.out.println("Type 'add' to add an account.");
			System.out.println("Type 'del' to delete an account.");
			System.out.println("Type 'update' to update an account.");
			System.out.println("Type 'search' to search for an account.");
			System.out.println("Type 'quit' to quit.");
			input = scanner.nextLine();
			if (input.equals("add")) {
				addAccount();
			} if (input.equals("print")) {
				for (int i = 0; i < database.length; i++) {
					if (database[i][0] != null) {
						System.out.println(database[i][0]);
						System.out.println(database[i][1]);
						System.out.println(database[i][2]);
					}
				}
			} if (input.equals("del")) {
				delete();
			} if (input.equals("update")) {
				update();
			} if (input.equals("search")) {
				search();
			} if (input.equals("quit")) {
				break;
			}
			
		} 

		write();

	}
}