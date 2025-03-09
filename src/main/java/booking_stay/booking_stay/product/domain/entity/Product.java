package booking_stay.booking_stay.product.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long productPrice;

//    private ProductStatus productStatus; //ENUM

//    private ProductType productType; // ENUM

    @Column(nullable = false)
    private LocalDateTime sale_started_time;

    @Column(nullable = false)
    private LocalDateTime sale_end_time;

    @Column(nullable = false)
    private LocalDateTime created_time;

    @Column(nullable = false)
    private LocalDateTime updated_time;



}
