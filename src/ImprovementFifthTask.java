import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Sales.Sales;
import Sales.SalesItem;

public class ImprovementFifthTask {
	public static void main(String[] args) {
		ArrayList<Sales> salesArr = readSales("data/input/SalesList.csv");
		ArrayList<SalesItem> salesItemArr = readSalesItem("data/input/SalesItem.csv");
		printSum(salesArr, salesItemArr);

	}

	/**
	 * 販売日と売り上げ総額を出力するメソッド
	 * @param salesArr Salesのオブジェクトが入ったArrayList
	 * @param salesItemArr SalesItemのオブジェクトが入ったArrayList
	 */
	private static void printSum(ArrayList<Sales> salesArr, ArrayList<SalesItem> salesItemArr) {
//		日付をkeyに総売上をvalueにもつHashMap
		Map<String, Integer> salesMap = new HashMap<>();
		//csvの日付の初め
		Calendar start = Calendar.getInstance();
//		salesList.csvに記入されている日付@type Calender
		Calendar salesCalender = Calendar.getInstance();
		//csvの日付の終わり
		Calendar end = Calendar.getInstance();
//		salesList.csvに記入されている日付@type Date
		Date salesDate;
//		SaleList.csvのitemCode
		String itemCodeSales;
//		SaleItem.csvのitemCode
		String itemCodeSalesItem;
//		日付をStringに変換するための変数
		String dateStr;
//		一時的にsalesMapのvalueの値を格納するための変数
		int tmp = 0;
//		個数
		int amount = 0;
//		金額
		int price = 0;
//		売り上げ
		int sales = 0;

//		csvの日付の初めをセット
		start.set(2020, 1, 15);
//		csvの日付の終わりをセット
		end.set(2020, 2, 16);

		do {
			salesMap.put(getDate(start), 0);
			start.add(Calendar.DAY_OF_MONTH, 1);
		} while (!(compareDate(start, end)));

		for (Sales salesObj : salesArr) {

			salesDate = salesObj.getSaleDate();
//			Date -> Calenderへ型変換
			salesCalender.setTime(salesDate);
			salesCalender.add(Calendar.MONTH, 1);
//			Calender -> String に日付の型変換
			dateStr = getDate(salesCalender);

			itemCodeSales = salesObj.getItemCode();
			amount = salesObj.getAmount();

			for (SalesItem salesItemObj : salesItemArr) {

				itemCodeSalesItem = salesItemObj.getItemCode();
				price = salesItemObj.getPrice();

				if (itemCodeSales.equals(itemCodeSalesItem)) {
					sales = price * amount;
				}
			}

			if (salesMap.containsKey(dateStr)) {
				tmp = salesMap.get(dateStr);
				salesMap.put(dateStr, tmp + sales);
			}
		}

		start.set(2020, 1, 15);
		System.out.println("販売日,売上総額");

//		販売日と売り上げをここで出力している
		do {
			System.out.printf("%s,%d円\n", getDate(start), salesMap.get(getDate(start)));
			start.add(Calendar.DAY_OF_MONTH, 1);
		} while (!(compareDate(start, end)));

	}
	/**
	 * 日付を文字列に変換するメソッド
	 * @param cal カレンダーのオブジェクト
	 * @return　0000/00/00の形のStringで返す
	 */
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
	/**
	 * 日付が等しいか判別するメソッド
	 * @param calender1 
	 * @param calender2
	 * @return 等しい場合はtrue, 違っている場合はfalse
	 */
	private static boolean compareDate(Calendar calender1, Calendar calender2) {
		if (calender1.compareTo(calender2) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * SalesList.csvを読み込み、Sales ObjectのArrayListで返すメソッド
	 * @param filepath　ファイルのパス
	 * @return Salesのオブジェクトが入ったArrayList
	 */
	
	private static ArrayList<Sales> readSales(String filepath) {
		File file = new File(filepath);
		Date salesDate;
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		ArrayList<Sales> objArr = new ArrayList<Sales>();
		String itemCode;
		String customer;
		int amount;
		int linesNumber = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String text;

			while ((text = br.readLine()) != null) {

				String[] row = text.split(",");

//				一行目はcolumns名なため飛ばす
				if (linesNumber > 0) {

					salesDate = df.parse(row[0]);
					itemCode = row[1];
					amount = Integer.parseInt(row[2]);
					customer = row[3];
					Sales sales = new Sales(salesDate, itemCode, amount, customer);
					objArr.add(sales);

				}

				linesNumber++;

			}

		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return objArr;

	}
	/**
	 * SalesItem.csvを読み込み、SalesItem ObjectのArrayListで返すメソッド
	 * @param filepath　ファイルのパス
	 * @return SalesItemのオブジェクトが入ったArrayList
	 */

	private static ArrayList<SalesItem> readSalesItem(String filepath) {
		File file = new File(filepath);
		ArrayList<SalesItem> objArr = new ArrayList<SalesItem>();
		String itemCode;
		String itemName;
		int price;
		int linesNumber = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String text;

			while ((text = br.readLine()) != null) {

				String[] row = text.split(",");

				if (linesNumber > 0) {

					itemCode = row[0];
					itemName = row[1];
					price = Integer.parseInt(row[2]);
					SalesItem salesItem = new SalesItem(itemCode, itemName, price);
					objArr.add(salesItem);

				}

				linesNumber++;

			}

		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return objArr;
	}

}
