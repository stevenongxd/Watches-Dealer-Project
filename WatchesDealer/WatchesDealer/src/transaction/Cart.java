package transaction;

public class Cart {
	private Integer userID;
	private Integer watchID;
	private Integer quantity;

	public Cart(Integer userID, Integer watchID, Integer quantity) {
		super();
		this.userID = userID;
		this.watchID = watchID;
		this.quantity = quantity;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
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

}
