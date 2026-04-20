package se.su.ovning3;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Exercise3 {

	private final List<Recording> recordings = new ArrayList<>();

	public void exportRecordings(String fileName) {

	}

	public void importRecordings(String fileName) {

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

