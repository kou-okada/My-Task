package Sales;

public class Customer {
	private String customer;
	private double discountRate;
	
	public Customer(String customer, double discountRate) {
		super();
		setCustomer(customer);
		setDiscountRate(discountRate);
	}
	
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
}
