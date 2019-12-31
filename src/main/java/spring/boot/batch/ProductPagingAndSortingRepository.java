package spring.boot.batch;

import org.springframework.data.repository.PagingAndSortingRepository;

import spring.boot.batch.model.Product;

public interface ProductPagingAndSortingRepository extends PagingAndSortingRepository<Product, Long> {

}
