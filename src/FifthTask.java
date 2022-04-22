import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FifthTask {
	public static void main(String[] args) {

		List<String[]> salesList = readCSV("data/input/SalesList.csv");
		List<String[]> saleItem = readCSV("data/input/SalesItem.csv");

		printSales(saleItem, salesList);

	}

	private static void printSales(List<String[]> salesItem, List<String[]> salesList) {
		Calendar date = Calendar.getInstance();
		Map<String, Integer> salesMaps = new HashMap<>();
		String salesDate;
		String startDate = salesList.get(1)[0];
		String[] startDateArr = startDate.split("/");

		int[] dateArray = Stream.of(startDateArr).mapToInt(Integer::parseInt).toArray();
		int sales = 0;
		int tmp = 0;
		int itemCost = 0;
		int itemNums = 0;

		date.set(dateArray[0], dateArray[1], dateArray[2]);

		while (!((date.get(Calendar.MONTH) == 2) && (date.get(Calendar.DATE) == 16))) {
			salesMaps.put(getDate(date), 0);
			date.add(Calendar.DAY_OF_MONTH, 1);
		}

		for (int rowIdx = 1; rowIdx < salesList.size(); rowIdx++) {

			String itemCode = salesList.get(rowIdx)[1];
			salesDate = salesList.get(rowIdx)[0];

			for (int itemIdx = 1; itemIdx < salesItem.size(); itemIdx++) {
				itemCost = Integer.parseInt(salesItem.get(itemIdx)[2]);
				itemNums = Integer.parseInt(salesList.get(rowIdx)[2]);

				if (itemCode.equals(salesItem.get(itemIdx)[0])) {
					sales = itemCost * itemNums;
				}
			}

			if (salesMaps.containsKey(salesDate)) {
				tmp = salesMaps.get(salesDate) + sales;
				salesMaps.put(salesDate, tmp);
			}

		}

		date.set(2020, 1, 15);
		System.out.println("販売日,売上総額");
		while (!((date.get(Calendar.MONTH) == 2) && (date.get(Calendar.DATE) == 16))) {
			System.out.printf("%s,%d円\n", getDate(date), salesMaps.get(getDate(date)));
			date.add(Calendar.DAY_OF_MONTH, 1);
		}

	}

	private static String getDate(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		String dayStr = Integer.valueOf(day).toString();
		String monthStr = Integer.valueOf(month).toString();
		if (day < 10) {
			dayStr = "0" + day;
		}

		if (month < 10) {
			monthStr = "0" + month;
		}

		return year + "/" + monthStr + "/" + dayStr;
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
