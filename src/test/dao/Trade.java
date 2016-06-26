package test.dao;

public class Trade {
	private final int id;
	private final String value;

	public Trade(int id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public int getId() {
		return id;
	}
	
	public String getValue() {
		return value;
	}
}
