import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FifthTask {
	public static void main(String[] args) {

		List<String[]> salesList = readCSV("data/input/SalesList.csv");
		List<String[]> saleItem = readCSV("data/input/SalesItem.csv");

		printSales(saleItem, salesList);

	}

	private static void printSales(List<String[]> salesItem, List<String[]> salesList) {

		String[] listColumns = salesList.get(0);
		String itemCode;
		String date;
		String listCode;
		int price;
		int itemCount;
		int sales;

		System.out.printf("%s,売り上げ総額\n", listColumns[0]);
		for (int rowIdx = 1; rowIdx < salesList.size(); rowIdx++) {

			date = salesList.get(rowIdx)[0];
			listCode = salesList.get(rowIdx)[1];
			itemCount = Integer.parseInt(salesList.get(rowIdx)[2]);

			for (int itemIdx = 1; itemIdx < salesItem.size(); itemIdx++) {

				itemCode = salesItem.get(itemIdx)[0];
				price = Integer.parseInt(salesItem.get(itemIdx)[2]);

				if (listCode.equals(itemCode)) {
					sales = price * itemCount;
					System.out.printf("%s,%d円\n", date, sales);
				}

			}
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
