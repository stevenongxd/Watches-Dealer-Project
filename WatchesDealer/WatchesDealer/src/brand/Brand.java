package brand;

public class Brand {
	private Integer brandID;
	private String brandName;

	public Brand(Integer brandID, String brandName) {
		super();
		this.brandID = brandID;
		this.brandName = brandName;
	}

	public Integer getBrandID() {
		return brandID;
	}

	public void setBrandID(Integer brandID) {
		this.brandID = brandID;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Override
	public String toString() {
		return this.brandName;
	}

}
