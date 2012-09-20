/*  
  File: NameSurfer.java  

  Description:  A program to search a text file of census data for various patterns
  Testing: Tests of the various methods were done by typing in java -ea NameSurfer, to enable asserts
  Modify: To modify the start decade, simply change the startDecade variable in the nameRecord class. 
    All the other code handles any changes in number of decades, more names, less names, etc automatically.

  Student Name: Henri Sweers 

  Date Created: 11/14/11

  Date Last Modified: 11/18/11

*/

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NameSurfer {

	// constants for menu choices
	public static final int SEARCH = 1;
	public static final int ONE_NAME = 2;
	public static final int APPEAR_ONCE = 3;
	public static final int APPEAR_ALWAYS = 4;
	public static final int MORE_POPULAR = 5;
	public static final int LESS_POPULAR = 6;
	public static final int TOP_TEN = 7;
	public static final int QUIT = 8;
	
	// main method. Driver for the whole program
	public static void main(String[] args) {
		//
		Test tests = new Test();
		assert tests.runTests();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			System.out.println("Unable to set look at feel to local settings. " +
			"Continuing with default Java look and feel.");
		}
	    try {
		    System.out.println("Opening GUI to choose file with names data.");
	        Scanner fileScanner = new Scanner( getFile() );
			Names n = new Names(fileScanner);
			int choice;
			Scanner keyboard = new Scanner(System.in);
			fileScanner.close();
			do {
				showMenu();
				choice = getChoice(keyboard);
				if( choice == SEARCH)
					search(n, keyboard);
				else if( choice == ONE_NAME )
					oneName(n, keyboard);
				else if( choice == APPEAR_ONCE )
					appearOnce(n);
				else if( choice == APPEAR_ALWAYS )
					appearAlways(n);
				else if ( choice == MORE_POPULAR )
					morePopular(n);
				else if ( choice == LESS_POPULAR )
					lessPopular(n);
				else if ( choice == TOP_TEN )
					myMethod(n, keyboard);
				else
					System.out.println("\n\nGoodbye.");

			} while( choice != QUIT);
		}
		catch(FileNotFoundException e) {
			System.out.println("Problem reading the data file. Exiting the program." + e);
		}
	}

	// method that shows names that have appeared in ever decade
	// pre: n != null
	// post: print out names that have appeared in ever decade
	private static void appearAlways(Names n) {
		ArrayList<String> appearAlwaysBuild = n.appearAlways();
		int size = appearAlwaysBuild.size();
		
		if (size == 0)
		{
			System.out.println("There are no names in this list that appear in every decade.");
		}

		if (size == 1)
		{
			appearAlwaysBuild.add(0, "1 name appears in every decade.");
			appearAlwaysBuild.add(appearAlwaysBuild.size(), "\n");
			for (int i = 0; i < appearAlwaysBuild.size(); i++)
			{
				System.out.println(appearAlwaysBuild.get(i));
			}
		}

		else
		{
			appearAlwaysBuild.add(0, " names appear in every decade.");
			appearAlwaysBuild.add(0, Integer.toString(size));
			appearAlwaysBuild.add(appearAlwaysBuild.size(), "\n");
			for (int i = 0; i < appearAlwaysBuild.size(); i++)
			{
				System.out.print(appearAlwaysBuild.get(i));
			}
		}
	}

	// method that shows names that have appeared in only one decade
	// pre: n != null
	// post: print out names that have appeared in only one decade
	private static void appearOnce(Names n) {
		ArrayList<String> appearOnceBuild = n.appearsOnce();
		int size = appearOnceBuild.size();
		
		if (size == 0)
		{
			System.out.println("There are no names in this list that appear in exactly one decade.");
		}

		if (size == 1)
		{
			appearOnceBuild.add(0, "1 name appears in exactly one decade.");
			appearOnceBuild.add(appearOnceBuild.size(), "\n");
			for (int i = 0; i < appearOnceBuild.size(); i++)
			{
				System.out.println(appearOnceBuild.get(i));
			}
		}

		else
		{
			appearOnceBuild.add(0, " names appear in exactly one decade.");
			appearOnceBuild.add(0, Integer.toString(size));
			appearOnceBuild.add(appearOnceBuild.size(), "\n");
			for (int i = 0; i < appearOnceBuild.size(); i++)
			{
				System.out.print(appearOnceBuild.get(i));
			}
		}
	}

	// method that shows data for one name, or states that name has never been ranked
	// pre: n != null, keyboard != null and is connected to System.in
	// post: print out the data for n or a message that n has never been in the top 1000 for any decade
	private static void oneName(Names n, Scanner keyboard) {
		System.out.print("Enter a name: ");
		String input = keyboard.nextLine();
		if (n.has(input))
		{
			int index = n.searchOneNameIndex(input);
			System.out.println(n.searchOneName(index));
		}

		else
		System.out.println(input + " has never been in the top 1000 for any decade.");
		
		
	}

	// method that shows all names that contain a substring from the user
	// and the decade they were most popular in
	// pre: n != null, keyboard != null and is connected to System.in
	// post: show the correct data		
	private static void search(Names n, Scanner keyboard) {
		System.out.print("Enter a partial name: ");
		String input = keyboard.nextLine();
		ArrayList<String> searchBuild = n.search(input);
		int size = searchBuild.size();

		if (searchBuild.size() != 0)
		{
			searchBuild.add(0, "There are " + size + " matches for " + input + ". \n \nThe matches with their highest ranking decade are:");
			searchBuild.add(searchBuild.size(), "\n");
			for (int i = 0; i < searchBuild.size(); i++)
			{
				System.out.print(searchBuild.get(i));
			}
		}

		else
		{
			System.out.println("There are no names that contain " + input);
		}

	}

	// Method that shows all the names that became increasingly more popular every decade
	// pre: n != null
	// post: show the correct data
	private static void morePopular(Names n) {
		ArrayList<String> morePopBuild = n.morePop();
		int size = morePopBuild.size();
		
		if (size == 0)
		{
			System.out.println("There are no names in this list that are more popular every decade.");
		}

		if (size == 1)
		{
			morePopBuild.add(0, "1 name is more popular every decade.");
			morePopBuild.add(morePopBuild.size(), "\n");
			for (int i = 0; i < morePopBuild.size(); i++)
			{
				System.out.println(morePopBuild.get(i));
			}
		}

		else
		{
			morePopBuild.add(0, " names are more popular every decade.");
			morePopBuild.add(0, Integer.toString(size));
			morePopBuild.add(morePopBuild.size(), "\n");
			for (int i = 0; i < morePopBuild.size(); i++)
			{
				System.out.print(morePopBuild.get(i));
			}
		}
	}

	// Method for lessPopular
	// pre: n != null
	// post: show the correct data
	private static void lessPopular(Names n) {
		ArrayList<String> lessPopBuild = n.lessPop();
		int size = lessPopBuild.size();
		
		if (size == 0)
		{
			System.out.println("There are no names in this list that are less popular every decade.");
		}

		if (size == 1)
		{
			lessPopBuild.add(0, "1 name is less popular every decade.");
			lessPopBuild.add(lessPopBuild.size(), "\n");
			for (int i = 0; i < lessPopBuild.size(); i++)
			{
				System.out.println(lessPopBuild.get(i));
			}
		}

		else
		{
			lessPopBuild.add(0, " names are less popular every decade.");
			lessPopBuild.add(0, Integer.toString(size));
			lessPopBuild.add(lessPopBuild.size(), "\n");
			for (int i = 0; i < lessPopBuild.size(); i++)
			{
				System.out.print(lessPopBuild.get(i));
			}
		}
	}

	// My method. Returns the top 10 most and least popular names of a given decade
	// pre: n != null, keyboard != null and is connected to System.in
	// post: show the correct data	
	private static void myMethod(Names n, Scanner keyboard)
	{
		System.out.print("Enter a decade to see its most popular and least popular names: ");
		String input = keyboard.nextLine();
		ArrayList<String> topLists = n.topLeast(input);
	
		for (int i = 10; i < 13; i++)
		{
			topLists.add(i, "....");
		}

		for (int i = 0; i < topLists.size(); i++)
		{
			System.out.println(topLists.get(i));
		}
	}

	// get choice from the user
	// keyboard != null and is connected to System.in
	// return an int that is >= SEARCH and <= QUIT
	private static int getChoice(Scanner keyboard) {
		int choice = getInt(keyboard, "Enter choice: ");
		keyboard.nextLine();
		while( choice < SEARCH || choice > QUIT){
			System.out.println("\n" + choice + " is not a valid choice");
			choice = getInt(keyboard, "Enter choice: ");
			keyboard.nextLine();
		}
		return choice;
	}
	
	// ensure an int is entered from the keyboard
	// pre: s != null and is connected to System.in
    private static int getInt(Scanner s, String prompt) {
        System.out.print(prompt);
        while( !s.hasNextInt() ){
            s.next();
            System.out.println("That was not an int.");
            System.out.print(prompt);
        }
        return s.nextInt();
    }

    // show the user the menu
	private static void showMenu() {
		System.out.println("\nOptions:");
		System.out.println("Enter " + SEARCH + " to search for names.");
		System.out.println("Enter " + ONE_NAME + " to display data for one name.");
		System.out.println("Enter " + APPEAR_ONCE+ " to display all names that appear in only one decade.");
		System.out.println("Enter " + APPEAR_ALWAYS + " to display all names that appear in all decades.");
		System.out.println("Enter " + MORE_POPULAR + " to display all the names that are more popular in every decade.");
		System.out.println("Enter " + LESS_POPULAR + " to display all the names that are less popular in every decade.");
		System.out.println("Enter " + TOP_TEN + " to display the top 10 most and least popular names for a decade.");
		System.out.println("Enter " + QUIT + " to quit.\n");
	}

	/** Method to choose a file using a traditional window.
     * @return the file chosen by the user. Returns null if no file picked.
     */ 
    public static File getFile() {
        // create a GUI window to pick the text to evaluate
        JFileChooser chooser = new JFileChooser(".");
        chooser.setDialogTitle("Select File With Baby Names Data.");
        int retval = chooser.showOpenDialog(null);
        File f =null;
        chooser.grabFocus();
        if (retval == JFileChooser.APPROVE_OPTION)
           f = chooser.getSelectedFile();
        return f;
    }

}

class Names
{
	// Attributes
	private String line;
	private ArrayList<nameRecord> namesList = new ArrayList<nameRecord>();
	private ArrayList<String> namesOnlyList = new ArrayList<String>();
	private int maxRank = 1000;
  
	// Constructor
	public Names(Scanner fileScanner) 
	{
		while(fileScanner.hasNextLine()) 
		{
			line = fileScanner.nextLine();
			nameRecord record = new nameRecord(line);
			
			// Adds record to the namesList and adds name to namesOnly
			namesList.add(record);
			namesOnlyList.add((record.getName()).toLowerCase());
		}		
	}


	// Methods

	// Checks if an input exists in the namesOnlyList
	public boolean has(String input)
	{
		return namesOnlyList.contains(input.toLowerCase());
	}

	// Method to search single name. Searches for input string in substring, returns its index 
	public int searchOneNameIndex(String input)
	{ 
		// Returns the index of a name in the namesList
		for (int i = 0; i < namesOnlyList.size(); i++)
		{
			if ((namesOnlyList.get(i)).equalsIgnoreCase(input))
			{
				return i;
			}
			
		}

		return -1;
	}

	// Given an index, returns the info of the name at that index
	public String searchOneName(int index)
	{
		nameRecord name = namesList.get(index);
		return name.info();
	}

	// Search method
	public ArrayList<String> search(String inputReg)
	{
		String input = inputReg.toLowerCase();
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		ArrayList<String> searchBuild = new ArrayList<String>();

		// Searches, checks while ignoring case
		for (int i = 0; i < namesList.size(); i++)
		{
			if (((namesList.get(i)).getName()).toLowerCase().indexOf(input) != -1)
			{
				searchBuild.add("\n" + (namesList.get(i)).getName() + " " + ((namesList.get(i)).getHighestDecade()));
			}
		}

		return searchBuild;
	}

	// Method to return the names that appear in only one decade
	public ArrayList<String> appearsOnce()
	{
		ArrayList<String> appearOnceBuild = new ArrayList<String>();

		for (int i = 0; i < namesList.size(); i++)
		{
			nameRecord record = namesList.get(i);
			if (record.appearsOnce() == true)
			{
				appearOnceBuild.add("\n" + record.getName());
			}
		}

		return appearOnceBuild;
	}

	// Method to return the names that appear in every decade
	public ArrayList<String> appearAlways()
	{
		ArrayList<String> appearAlwaysBuild = new ArrayList<String>();

		for (int i = 0; i < namesList.size(); i++)
		{
			nameRecord record = namesList.get(i);
			if (record.appearAlways() == true)
			{
				appearAlwaysBuild.add("\n" + record.getName());
			}
		}

		return appearAlwaysBuild;
	}

	// Method to return a the names that are more popular in every decade
	public ArrayList<String> morePop()
	{
		ArrayList<String> morePopBuild = new ArrayList<String>();

		for (int i = 0; i < namesList.size(); i++)
		{
			nameRecord record = namesList.get(i);
			if (record.morePopular() == true)
			{
				morePopBuild.add("\n" + record.getName());
			}
		}

		return morePopBuild;
	}

	// Method to return a the names that are less popular in every decade
	public ArrayList<String> lessPop()
	{
		ArrayList<String> lessPopBuild = new ArrayList<String>();

		for (int i = 0; i < namesList.size(); i++)
		{
			nameRecord record = namesList.get(i);
			if (record.lessPopular() == true)
			{
				lessPopBuild.add("\n" + record.getName());
			}
		}

		return lessPopBuild;

	}

	// Personal method. Given a string input for decade, returns a String representation
	//  of the top most and least popoular names
	public ArrayList<String> topLeast(String input)
	{
		ArrayList<String> nameRanks = new ArrayList<String>();
		ArrayList<String> nameRanksTwo = new ArrayList<String>();
		int decade = Integer.parseInt(input);
		int lowCount = 0;
		int highCount = 0;
		int highCounter = 0;
		
		while (lowCount < 10 && highCounter < 10)
		{
			for (int i = 0; i < namesList.size(); i++)
			{
				for (int j = 0; j < 10; j++)
				{
					if ((namesList.get(i)).getRank(decade) == j + 1)
					{
						nameRanks.add(Integer.toString(j + 1) + ". " + (namesList.get(i)).getName());
						lowCount++;
					}
				}

				for (int j = 0; j < 10; j++)
				{
					if ((namesList.get(i)).getRank(decade) == maxRank - j)
					{
						nameRanksTwo.add((namesList.get(i)).getName());
						highCounter++;
						highCount++;
					}
				}
			}

			System.out.println("Finished making arrays" + nameRanks.size() + "   " + nameRanksTwo.size());

			for (int i = 10; i <= 0; i++)
			{
				nameRanks.add(Integer.toString(maxRank - 10) + nameRanksTwo.get(i));
			}
			System.out.println("Finished merging");

		}
		return nameRanks;
	}
}



class nameRecord
{
	// Attributes
	private String[] parsedData;
	private int startDecade = 1900;
	private String name;
	private ArrayList<Integer> intList= new ArrayList<Integer>();

	// Constructor
	public nameRecord(String data)
	{
		parsedData = data.split("\\s+");

		for (int i = 1; i < parsedData.length; i++)
		{
			intList.add(Integer.parseInt(parsedData[i]));
		}

		for (int i = 0; i < parsedData.length; i++)
		{
			// Turns all 0's into 1001's for convenience
			if (parsedData[i].equals("0"))
			{
				parsedData[i] = "1001";
			}
		}

		name = parsedData[0];
	}


	// Accessors
	public String[] getRecord()
	{
		return parsedData;
	}

	public int getRecordLength()
	{
		return parsedData.length;
	}

	public int getRank(int i)
	{
		int decade = getReverseDecade(i);
		return Integer.parseInt(parsedData[decade]);
	}
	
	public ArrayList<Integer> getIntList()
	{
		return intList;
	}

	// Returns a string representation of a given nameRecord object
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.parsedData.length; i++)
		{
			s.append(this.parsedData[i] + " ");
		}
		return s.toString();
	}

	// Returns string representation of name horizontally and also vertically with
	// highest ranking decade.
	public String info()
	{
		StringBuilder s = new StringBuilder();
		s.append(this.parsedData[0] + ": ");
		for (int i = 1; i < this.parsedData.length; i++)
		{
			if (this.parsedData[i].equals("1001"))
			{
				s.append("0 ");
			}
			else
			{
				s.append(this.parsedData[i] + " ");
			}
		}

		for (int i = 1; i < this.parsedData.length; i++)
		{
			if (this.parsedData[i].equals("1001"))
			{
				s.append("\n" + getDecade(i) + ": 0");
			}
			else
			{
				s.append("\n" + getDecade(i) + ": " + this.parsedData[i]);
			}
		}
		
		return s.toString();
	}

	// Returns name
	public String getName()
	{
		return this.name;
	}

	// Returns the highest ranking decade
	public int getHighestDecade()
	{
		int max = 1;
		int maxCompare = Integer.parseInt(parsedData[1]);
		for (int i = 2; i < this.parsedData.length; i++)
		{
			if (Integer.parseInt(parsedData[i]) <= maxCompare)
			{
				maxCompare = Integer.parseInt(parsedData[i]);
				max = i;
			}
		}
		return getDecade(max);
	}

	// Decade Getter
	public int getDecade(int input)
	{
		return (startDecade + (input * 10) - 10);
	}

	// Reverse Decade Getter, takes index in and returns the decade at that index
	public int getReverseDecade(int input)
	{
		return ((input - startDecade + 10) / 10);
	}

	// Mutators

	// Start decade setter
	public void setDecadeStart(int input)
	{
		this.startDecade = input;
	}


	// Methods

	// Method to check if it appears just once
	public boolean appearsOnce()
	{
		int counter = 0;
		for (int i = 1; i < parsedData.length; i++)
		{
			if (!parsedData[i].equals("1001"))
			{
				counter++;
			}
		}
		
		if (counter == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Method to return true if the given nameRecord is ranked in every decade
	public boolean appearAlways()
	{
		boolean appear = true;;
		for (int i = 1; i < parsedData.length; i++)
		{
			if (parsedData[i].equals("1001"))
			{
				appear = false;
				break;
			}
		}
		return appear;
	}

	// Method to return true if the given nameRecord rankings become increasingly popular
	public boolean morePopular()
	{
		boolean appear = true;
		for (int i = 2; i < parsedData.length; i++)
		{
			int current = Integer.parseInt(parsedData[i]);
			int previous = Integer.parseInt(parsedData[i - 1]);
			if (current >= previous)
			{
				appear = false;
				break;
			}
		}
		return appear;
	}

	// Method to return true if the given nameRecord rankings become less popular every decade
	public boolean lessPopular()
	{
		boolean appear = true;
		for (int i = 2; i < parsedData.length; i++)
		{
			int current = Integer.parseInt(parsedData[i]);
			int previous = Integer.parseInt(parsedData[i - 1]);
			if (current <= previous)
			{
				appear = false;
				break;
			}
		}
		return appear;
	}

}

class Test
{
	public boolean runTests()
	{	
		System.out.println("Running tests...");
		testNameRecord();
		testGetName();
		testGetDecade();
		testAppearsOnce();
		testAppearAlways();
		testMorePop();
		testLessPop();
		testOneSearch();;
		testHighestDecade();
		testReverseDecade();
		System.out.println("Tests successfull!");
		return true;
	}

	// Various Tests...
	public static void testNameRecord()
	{
		nameRecord testRecord = new nameRecord("Abigail 0 0 0 0 854 654 615 317 150 50 14");
		String[] testArray = new String[] {"Abigail", "1001", "1001", "1001", "1001", "854", "654", "615", "317", "150", "50", "14"};
		
		assert Arrays.equals(testRecord.getRecord(), testArray);

		testRecord = new nameRecord("Abigail 0 0 0 0 854 654 615 317 150 50 14 20");
		testArray = new String[] {"Abigail", "1001", "1001", "1001", "1001", "854", "654", "615", "317", "150", "50", "14", "20"};
		
		assert Arrays.equals(testRecord.getRecord(), testArray);
	}

	public static void testGetName()
	{
		nameRecord testRecord = new nameRecord("Abigail 0 0 0 0 854 654 615 317 150 50 14");
		assert testRecord.getName().equals("Abigail");
	}

	public static void testGetDecade()
	{
		nameRecord testRecord = new nameRecord("Abigail 0 0 0 0 854 654 615 317 150 50 14");
		int testInt = 1;
		int startDecade = 1900;
		
		assert testRecord.getDecade(testInt) == 1900;
		testInt = 2;
		assert testRecord.getDecade(testInt) == 1910;
		testInt = 3;
		assert testRecord.getDecade(testInt) == 1920;
		testInt = 4;
		assert testRecord.getDecade(testInt) == 1930;
		testInt = 5;
		assert testRecord.getDecade(testInt) == 1940;
		testInt = 6;
		assert testRecord.getDecade(testInt) == 1950;
		testInt = 7;
		assert testRecord.getDecade(testInt) == 1960;
		testInt = 8;
		assert testRecord.getDecade(testInt) == 1970;
		testInt = 9;
		assert testRecord.getDecade(testInt) == 1980;
		testInt = 10;
		assert testRecord.getDecade(testInt) == 1990;
		testInt = 11;
		assert testRecord.getDecade(testInt) == 2000;

		startDecade = 1800;
		testRecord.setDecadeStart(startDecade);
		testInt = 1;
		assert testRecord.getDecade(testInt) == 1800;
		testInt = 2;
		assert testRecord.getDecade(testInt) == 1810;
		testInt = 3;
		assert testRecord.getDecade(testInt) == 1820;
		testInt = 4;
		assert testRecord.getDecade(testInt) == 1830;
		testInt = 5;
		assert testRecord.getDecade(testInt) == 1840;
		testInt = 6;
		assert testRecord.getDecade(testInt) == 1850;
		testInt = 7;
		assert testRecord.getDecade(testInt) == 1860;
		testInt = 8;
		assert testRecord.getDecade(testInt) == 1870;
		testInt = 9;
		assert testRecord.getDecade(testInt) == 1880;
		testInt = 10;
		assert testRecord.getDecade(testInt) == 1890;
		testInt = 11;
		assert testRecord.getDecade(testInt) == 1900;


	}


	public static void testAppearsOnce()
	{
		nameRecord testRecord = new nameRecord("Abagail 0 0 0 0 0 0 0 0 0 0 958");
		assert testRecord.appearsOnce();
	}


	public static void testAppearAlways()
	{
		nameRecord testRecord = new nameRecord("Aaron 193 208 218 274 279 232 132 36 32 31 41");
		assert testRecord.appearAlways();
	}

	public static void testMorePop()
	{
		nameRecord testRecord = new nameRecord("Eduardo 592 585 538 433 430 349 301 226 194 121 116");
		assert testRecord.morePopular();
	}

	public static void testLessPop()
	{
		nameRecord testRecord = new nameRecord("Albert 16 18 21 32 48 76 103 123 147 211 282");
		assert testRecord.lessPopular();
	}

	public static void testOneSearch()
	{
		nameRecord testRecord = new nameRecord("Albert 16 18 21 32 48 76 103 123 147 211 282");
		String s = "Albert 16 18 21 32 48 76 103 123 147 211 282";
		Scanner sc = new Scanner(s);
		Names n = new Names(sc);
		assert n.searchOneNameIndex("Albert") == 0;
		assert (n.searchOneName(0)).equals(testRecord.info());
	}


	public static void testHighestDecade()
	{
		nameRecord testRecord = new nameRecord("Albert 16 18 21 32 48 76 103 123 147 211 282");
		assert testRecord.getHighestDecade() == 1900;
	}

	public static void testReverseDecade()
	{
		nameRecord testRecord = new nameRecord("Albert 16 18 21 32 48 76 103 123 147 211 282");
		assert testRecord.getReverseDecade(1900) == 1;
	}


}
