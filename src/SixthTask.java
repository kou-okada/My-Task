import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Sales.Customer;
import Sales.Sales;
import Sales.SalesItem;

public class SixthTask {
	public static void main(String[] args) {
		ArrayList<Sales> salesArr = readSales("data/input/SalesList.csv");
		ArrayList<SalesItem> salesItemArr = readSalesItem("data/input/SalesItem.csv");
		ArrayList<Customer> customerArr = readCustomer("data/input/Customer.csv");

		Map<String, Map<String, Integer>> itemMap = createMap(salesArr, salesItemArr);
		printSales(itemMap, customerArr);

	}

	/**
	 * 割引後の売り上げとアイテム名を出力するメソッド
	 * @param itemMap createMapで返されたMap
	 * @param custtomerArr Customerのオブジェクトが入ったArrayList
	 */
	private static void printSales(Map<String, Map<String, Integer>> itemMap, ArrayList<Customer> customerArr) {
//		会社名をkey, 割引後の売り上げをvalueにもつMap
		Map<String, Integer> salesMap = new HashMap<String, Integer>();
		int total;
		int tmp;
		int tmp2;
		String customerName;
		String itemName;
//		割引率
		double discountRate;

		for (Map.Entry<String, Map<String, Integer>> entry : itemMap.entrySet()) {

			itemName = entry.getKey();
			total = 0;

			for (Customer customer : customerArr) {

				customerName = customer.getCustomer();
				discountRate = customer.getDiscountRate();

				if (entry.getValue().containsKey(customerName)) {

					tmp2 = (int) (entry.getValue().get(customerName) * (1 - discountRate));
					entry.getValue().put(customerName, tmp2);

					total += tmp2;

				}

			}

			if (salesMap.containsKey(itemName)) {
				tmp = salesMap.get(itemName);
				total += tmp;
				salesMap.put(itemName, tmp);

			} else {
				salesMap.put(itemName, total);
			}

		}

//		出来上がったMapをvalueの降順にソート
		salesMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.forEach(e -> System.out.println("「" + e.getKey() + "」の売り上げ合計は、" + e.getValue() + "円です。"));

	}

	/**
	 * アイテム名、会社名、売り上げの順番のマップを返すメソッド
	 * 
	 * @param Salesのオブジェクトが入ったArrayList
	 * @param SalesItemのオブジェクトが入ったArrayList
	 * @return Map(アイテム名, Map(会社名, 売り上げ))のマップ
	 */
	private static Map<String, Map<String, Integer>> createMap(ArrayList<Sales> salesArr,
			ArrayList<SalesItem> salesItemArr) {
		Map<String, Map<String, Integer>> itemsMap = new HashMap<>();

		int sales;
		int tmp;

//		商品単価
		int price;
//		SalesItem.csvのアイテムコード
		String itemCode;
//     SalesItem.csvのアイテム名
		String itemName;

//		数量
		int amount;
//		SalesList.csvのアイテムコード
		String listItemCode;
//	    SalesList.csvの会社名
		String listCostomer;

		for (SalesItem salesItem : salesItemArr) {
			itemCode = salesItem.getItemCode();
			itemName = salesItem.getItemName();
			price = salesItem.getPrice();

//			会社名をkey 売り上げをvalueにもつMap
			Map<String, Integer> customerMap = new HashMap<>();

			for (Sales salesList : salesArr) {
				listItemCode = salesList.getItemCode();
				listCostomer = salesList.getCustomer();
				amount = salesList.getAmount();

				if (itemCode.equals(listItemCode)) {
					sales = price * amount;

					if (customerMap.containsKey(listCostomer)) {
						tmp = customerMap.get(listCostomer);
						sales += tmp;
						customerMap.put(listCostomer, sales);

					} else {

						customerMap.put(listCostomer, sales);
					}
					itemsMap.put(itemName, customerMap);
				}

			}

		}

		return itemsMap;

	}

	/**
	 * SalesList.csvを読み込み、Sales ObjectのArrayListで返すメソッド
	 * 
	 * @param filepath ファイルのパス
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
	 * 
	 * @param filepath ファイルのパス
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

	/**
	 * Customer.csvを読み込み、Customer ObjectのArrayListで返すメソッド
	 * 
	 * @param filepath ファイルのパス
	 * @return Customerのオブジェクトが入ったArrayList
	 */
	private static ArrayList<Customer> readCustomer(String filepath) {
		File file = new File(filepath);
		ArrayList<Customer> objArr = new ArrayList<Customer>();
		String customer;
		double discountRate;
		int linesNumber = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String text;

			while ((text = br.readLine()) != null) {

				String[] row = text.split(",");

				if (linesNumber > 0) {

					customer = row[0];
					discountRate = Double.parseDouble(row[1]);
					Customer salesCustomer = new Customer(customer, discountRate);
					objArr.add(salesCustomer);

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
