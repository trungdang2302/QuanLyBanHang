package QuanLyCuaHangMain.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import QuanLyCuaHangMain.DAO.ProductDAO;
import QuanLyCuaHangMain.Model.Product;
import QuanLyCuaHangMain.Model.ProductForm;
import QuanLyCuaHangMain.Service.ProductService;

@RestController
public class MainRESTController {

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductService productService;

	// Get all
	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Product> getAllProduct() {
		List<Product> list = productService.findAll();
		return list;
	}

	// Get product by ID
	@RequestMapping(value = "/product/{productID}", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Product getProductByID(@PathVariable("productID") Integer productID) {
		return productDAO.getProductByID(productID);
	}

	// Add product
	@RequestMapping(value = "/product", //
			method = RequestMethod.POST, //
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Product addProduct(@Valid @RequestBody String pdf) {

		try {
			JSONObject obj = new JSONObject(pdf);
			// ProductForm productform = new ProductForm();

			String name = obj.getString("Name");
			String des = obj.getString("Description");
			String url = obj.getString("URL");
			Product product = new Product(name, des, url);

			// return productDAO.addProduct(productform);
			return productService.save(product);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	// Update product
	@RequestMapping(value = "/product", //
			method = RequestMethod.PUT, //
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<Product> updateProduct(@RequestBody String pdf) {

		try {
			JSONObject obj = new JSONObject(pdf);

			Integer id = obj.getInt("ID");
			String name = obj.getString("Name");
			String des = obj.getString("Description");
			String url = obj.getString("URL");
			Product product = new Product(id, name, des, url);

			Product pro = productService.getOne(id);
			if (pro == null) {
				return ResponseEntity.notFound().build();
			}

			pro.setName(product.getName());
			pro.setDescription(product.getDescription());
			pro.setURL(product.getURL());

			Product updatedContact = productService.save(pro);
			return ResponseEntity.ok(updatedContact);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	// Delete product
	@RequestMapping(value = "/product/{productID}", //
			method = RequestMethod.DELETE, //
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<Product> deleteProduct(@PathVariable("productID") Integer productID) {
		Product product = productService.getOne(productID);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		productService.delete(product);
		return ResponseEntity.ok().build();
	}

}
