package application;

public class booked {
	int id_p;
	int id_v;
    int price;
	
	public int getId_p() {
		return id_p;
	}

	public void setId_p(int id_p) {
		this.id_p = id_p;
	}

	public int getId_v() {
		return id_v;
	}

	public void setId_v(int id_v) {
		this.id_v = id_v;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public booked(int id_p, int id_v, int price) {
		super();
		this.id_p = id_p;
		this.id_v = id_v;
		this.price = price;
	}
}
