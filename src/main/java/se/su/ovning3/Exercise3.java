package se.su.ovning3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Exercise3 {
	private final List<Recording> recordings = new ArrayList<>();

	/**
	 * exportRecordings - tar som parameter ett filnamn och skriver ut listan med
	 * inspelningar till filen (i formatet för exportfilen).
	 */
	public void exportRecordings(String fileName) {
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				for (Recording recording : recordings) {
					bufferedWriter.write("<recording>");
					bufferedWriter.newLine();
					bufferedWriter.write("<artist>");
					bufferedWriter.write(recording.getArtist());
					bufferedWriter.write("</artist>");
					bufferedWriter.newLine();
					bufferedWriter.write("<title>");
					bufferedWriter.write(recording.getTitle());
					bufferedWriter.write("</title>");
					bufferedWriter.newLine();
					bufferedWriter.write("<year>");
					bufferedWriter.write(Integer.toString(recording.getYear()));
					bufferedWriter.write("</year>");
					bufferedWriter.newLine();
					bufferedWriter.write("<genres>");
					for (String genre : recording.getGenre()) {
						bufferedWriter.write("<genre>");
						bufferedWriter.write(genre);
						bufferedWriter.write("</genre>");
						bufferedWriter.newLine();
					}
					bufferedWriter.write("</genres>");
					bufferedWriter.newLine();

					bufferedWriter.write("</recording>");
					bufferedWriter.newLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * importRecordings - tar som parameter ett filnamn och läser in det som
	 * finns i filen (som är i formatet för importfilen, se nästa sektion), skapar
	 * Recording-objekt av det och stoppar in dem i listan. Om någon fil med
	 * det givna namnet inte finns ska en felutskrift om detta göras.
	 */
	public void importRecordings(String fileName) {
		try {
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
