package se.su.ovning3;

import java.io.*;
import java.util.*;

public class Exercise3 {

	private final List<Recording> recordings = new ArrayList<>();

	public void exportRecordings(String fileName) {

	}

	/**
	 * importRecordings - tar som parameter ett filnamn och läser in det som
	 * finns i filen (som är i formatet för importfilen, se nästa sektion), skapar
	 * Recording-objekt av det och stoppar in dem i listan. Om någon fil med
	 * det givna namnet inte finns ska en felutskrift om detta göras.
	 */

	public void importRecordings(String fileName) {
		try{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int lineCounter = Integer.parseInt(bufferedReader.readLine());
			for (int holder = 0; holder < lineCounter; holder++) {
				String row = bufferedReader.readLine();
				String[] columns = row.split(";");
				String artist = columns[0];
				String title = columns[1];
				int year = Integer.parseInt(columns[2]);
				
				int genreCount = Integer.parseInt(bufferedReader.readLine());
				Set<String> genreSet = new HashSet<>();
				for (int g = 0; g < genreCount; g++) {
					String genre = bufferedReader.readLine();
					genreSet.add(genre);
				}
				recordings.add(new Recording(artist, title, year, genreSet));
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Map<Integer, Double> importSales(String fileName) {
		Map<Integer, Double> salesMap = new HashMap<>();

		try (DataInputStream in = new DataInputStream(new FileInputStream(fileName))) {
			int numberOfPosts = in.readInt();

			for (int i = 0; i < numberOfPosts; i++) {
				int year = in.readInt();
				int month = in.readInt();
				int day = in.readInt();
				double value = in.readDouble();

				int key = year * 100 + month;

				salesMap.put(key, salesMap.getOrDefault(key, 0.0) + value);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Filen kunde inte hittas: " + fileName);
		} catch (IOException e) {
			System.out.println("Fel vid läsning av filen: " + fileName);
		}
		return salesMap;
	}

	public List<Recording> getRecordings() {
		return Collections.unmodifiableList(recordings);
	}

	public void setRecordings(List<Recording> recordings) {
		this.recordings.clear();
		this.recordings.addAll(recordings);
	}
}

