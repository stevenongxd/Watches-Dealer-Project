package model;

import controller.WatchController;

public class TransactionDetail {
	private Integer transactionID;
	private Integer watchID;
	private Integer quantity;

	public TransactionDetail(Integer transactionID, Integer watchID, Integer quantity) {
		super();
		this.transactionID = transactionID;
		this.watchID = watchID;
		this.quantity = quantity;
	}

	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public Integer getWatchID() {
		return watchID;
	}

	public void setWatchID(Integer watchID) {
		this.watchID = watchID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public String getWatchName() {
		return WatchController.getWatch(watchID).getWatchName();
	}

	public String getWatchBrand() {
		return WatchController.getWatch(watchID).getBrandName();
	}
	
	public Integer getWatchPrice() {
		return WatchController.getWatch(watchID).getWatchPrice();
	}
	
	public Integer getSubTotal() {
		return WatchController.getWatch(watchID).getWatchPrice() * quantity;
	}

}
