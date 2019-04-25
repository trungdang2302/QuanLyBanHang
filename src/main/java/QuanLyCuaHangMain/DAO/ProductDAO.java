package QuanLyCuaHangMain.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import QuanLyCuaHangMain.Model.Product;
import QuanLyCuaHangMain.Model.ProductForm;

@Repository
public class ProductDAO {

	private static final Map<Integer, Product > productMap = new HashMap<Integer, Product>();
	
	static {
		initProduct();
	}
	
	private static void initProduct() {
		Product product1 = new Product(1, "TiVi SamSung", "100inch", "image01.png");
		Product product2 = new Product(2, "Macbook Pro", "2019", "image02.png");
		Product product3 = new Product(3, "Chuot Logitech", "1000 dpi", "image03.png");
		Product product4 = new Product(4, "Ban phim co", "RBG", "image04.png");
		Product product5 = new Product(5, "Chip core i7", "2.5 GHZ", "image05.png");
  
        productMap.put(product1.getID(),product1);
        productMap.put(product2.getID(),product2);
        productMap.put(product3.getID(),product3);
        productMap.put(product4.getID(),product4);
        productMap.put(product5.getID(),product5);
        
    }
	
	public Integer getMaxProductID() {
        Set<Integer> keys = productMap.keySet();
        Integer max = 0;
        for (Integer key : keys) {
            if (key > max) {
                max = key;
            }
        }
        return max;
    }
	
	public Product getProductByID(Integer productID) {
        return productMap.get(productID);
    }
	
	public Product addProduct(ProductForm pdf) {
        Integer currentid= this.getMaxProductID()+ 1;
        pdf.setID(currentid);
        Product newProduct = new Product(pdf);  
        productMap.put(newProduct.getID(), newProduct);
        return newProduct;
    }
	
	public Product updateProduct(ProductForm pdf) {
		Product product = this.getProductByID(pdf.getID());
        if(product != null)  {
        	product.setName(pdf.getName());
        	product.setDescription(pdf.getDescription());
        	product.setURL(pdf.getURL());
        }  
        return product;
    }
	
	public void deleteProduct(Integer productID) {
		productMap.remove(productID);
    }
	
	public List<Product> getAllProduct() {
        Collection<Product> data = productMap.values();
        List<Product> list = new ArrayList<Product>();
        list.addAll(data);
        return list;
    }
	
}
