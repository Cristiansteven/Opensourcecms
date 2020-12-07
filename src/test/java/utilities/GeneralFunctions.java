package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class GeneralFunctions {

	/**
	 * Marks the start of a function
	 * 
	 * @author JM
	 * @since 02/08/2018
	 */
	public static void functionStart(String strFunctionName) {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||| Entered the function: " + strFunctionName);
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println();
	}

	/**
	 * Read the CSV file and return from the second row.
	 * 
	 * @author FD
	 * @return Rows
	 * @since 03/08/2018
	 */
	public static List<String> readCsvFile(String filePath) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			List<String> lines = new LinkedList<>();
			boolean band = false;

			while (br.ready()) {
				if (band)
					lines.add(br.readLine());
				else {
					br.readLine();
					band = true;
				}
			}
			br.close();
			return lines;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
