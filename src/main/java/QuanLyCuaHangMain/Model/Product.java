package QuanLyCuaHangMain.Model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String Name;
	private String Description;
	private String URL;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID;
	
	public Product() {}
	
	public Product(int iD, String name, String description, String uRL) {
		ID = iD;
		Name = name;
		Description = description;
		URL = uRL;
	}
	
	public Product(String name, String description, String uRL) {
		Name = name;
		Description = description;
		URL = uRL;
	}
	
	public Product(ProductForm p) {
		ID = p.getID();
		Name = p.getName();
		Description = p.getDescription();
		URL = p.getURL();
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}

}
