package com.BankProject;

public class Transaction 
{
	private int transaction_id;
	private int accountNumber;
	private String transaction_date;
	private int amount;
	private String transaction_type;
	
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(int transaction_id, int accountNumber, String transaction_date, int amount,
			String transaction_type) {
		super();
		this.transaction_id = transaction_id;
		this.accountNumber = accountNumber;
		this.transaction_date = transaction_date;
		this.amount = amount;
		this.transaction_type = transaction_type;
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(String transaction_date) {
		this.transaction_date = transaction_date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	@Override
	public String toString() {
		return "Transaction [transaction_id=" + transaction_id + ", accountNumber=" + accountNumber
				+ ", transaction_date=" + transaction_date + ", amount=" + amount + ", transaction_type="
				+ transaction_type + "]";
	}

}
