package spring.boot.batch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Entity
public class Product {
    
    @Id
    @Column(name = "product_id")
    private Long id;
    
    @Column
    private String name;
    
    @Column
    private String description;
    
    @Column
    private Double price;
}