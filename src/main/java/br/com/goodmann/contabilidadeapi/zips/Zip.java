package br.com.goodmann.contabilidadeapi.zips;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "zips")
public class Zip {

	@Id
	private String id;
	private String city;
	private String zip;
	private Integer pop;
	private String state;
	private Object loc;

	public Object getLoc() {
		return loc;
	}

	public void setLoc(Object loc) {
		this.loc = loc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getPop() {
		return pop;
	}

	public void setPop(Integer pop) {
		this.pop = pop;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
