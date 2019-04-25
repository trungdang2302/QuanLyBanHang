package QuanLyCuaHangMain.Service;

import org.springframework.stereotype.Repository;
import QuanLyCuaHangMain.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductService extends JpaRepository<Product, Integer> {

}
