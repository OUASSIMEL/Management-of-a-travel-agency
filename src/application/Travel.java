package application;

public class Travel {
	int id_v;
	String dateI;
	String dateE;
	String CityS;
	String CityD;
	int Nmax;
	String Country;
	String logement;
	String sub_logement;
	String imgtravelURL;
	int price;
	String description;
	public Travel(int id_v, String dateI, String dateE, String cityS, String cityD, int nmax, String country,
			String logement, String sub_logement,String imgtravelURL,int price,String description) {
		super();
		this.id_v = id_v;
		this.dateI = dateI;
		this.dateE = dateE;
		CityS = cityS;
		CityD = cityD;
		Nmax = nmax;
		Country = country;
		this.logement = logement;
		this.sub_logement = sub_logement;
		this.imgtravelURL = imgtravelURL;
		this.price = price;
		this.description = description;
	}
	public int getId_v() {
		return id_v;
	}
	public void setId_v(int id_v) {
		this.id_v = id_v;
	}
	public String getDateI() {
		return dateI;
	}
	public void setDateI(String dateI) {
		this.dateI = dateI;
	}
	public String getDateE() {
		return dateE;
	}
	public void setDateE(String dateE) {
		this.dateE = dateE;
	}
	public String getCityS() {
		return CityS;
	}
	public void setCityS(String cityS) {
		CityS = cityS;
	}
	public String getCityD() {
		return CityD;
	}
	public void setCityD(String cityD) {
		CityD = cityD;
	}
	public int getNmax() {
		return Nmax;
	}
	public void setNmax(int nmax) {
		Nmax = nmax;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getLogement() {
		return logement;
	}
	public void setLogement(String logement) {
		this.logement = logement;
	}
	public String getSub_logement() {
		return sub_logement;
	}
	public void setSub_logement(String sub_logement) {
		this.sub_logement = sub_logement;
	}
	public String getImgtravelURL() {
		return imgtravelURL;
	}
	public void setImgtravelURL(String imgtravelURL) {
		this.imgtravelURL = imgtravelURL;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
