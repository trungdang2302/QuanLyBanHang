package QuanLyCuaHangMain.Repository;

import org.springframework.stereotype.Repository;

import QuanLyCuaHangMain.Entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
