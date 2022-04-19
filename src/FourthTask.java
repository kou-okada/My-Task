import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FourthTask {

	public static void main(String[] args) {
		List<String[]> record = new ArrayList<>();
		record = readCSV("./data/input/TestResult.csv");

		System.out.println("-------------------------------");
		printMaxScore(record);
		System.out.println("-------------------------------");
		printAverage(record);
		System.out.println("-------------------------------");
		printSum(record);

	}

	private static void printMaxScore(List<String[]> record) {
		String[] colName = record.get(0);

		for (int colIdx = 1; colIdx < colName.length; colIdx++) {

			String maxName = record.get(1)[0];
			int maxScore = Integer.parseInt(record.get(1)[colIdx]);

			for (int rowIdx = 2; rowIdx < record.size(); rowIdx++) {
				String usrName = record.get(rowIdx)[0];
				int usrScore = Integer.parseInt(record.get(rowIdx)[colIdx]);

				if (maxScore < usrScore) {

					maxName = usrName;
					maxScore = usrScore;
				}

			}

			System.out.printf("%sの最高得点者は%sさん、%d点です。\n", colName[colIdx], maxName, maxScore);
		}

	}

	private static void printAverage(List<String[]> record) {
		String[] colName = record.get(0);

		for (int colIdx = 1; colIdx < colName.length; colIdx++) {

			float sum = 0;

			for (int rowIdx = 1; rowIdx < record.size(); rowIdx++) {
				sum += Integer.parseInt(record.get(rowIdx)[colIdx]);
			}

			float average = sum / (record.size() - 1);
			System.out.printf("%sの平均得点は、%.2fです。\n", colName[colIdx], average);
		}
	}

	private static void printSum(List<String[]> record) {

		for (int rowIdx = 1; rowIdx < record.size(); rowIdx++) {
			int sum = 0;
			String name = record.get(rowIdx)[0];
			for (int valueIdx = 0; valueIdx < record.get(rowIdx).length; valueIdx++) {

				if (valueIdx > 0) {
					sum += Integer.parseInt(record.get(rowIdx)[valueIdx]);
				}
			}

			System.out.printf("%sさんの合計点は%dです。\n", name, sum);

		}

	}

	private static List<String[]> readCSV(String filepath) {
		File file = new File(filepath);
		List<String[]> records = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String text;

			while ((text = br.readLine()) != null) {

				String[] row = text.split(",");

				records.add(row);

			}

		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return records;
	}
}
