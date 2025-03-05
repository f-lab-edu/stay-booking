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
    private Long productId;

    private String productName;

    private Long productPrice;

//    private ProductStatus productStatus; //ENUM

//    private ProductType productType; // ENUM

    private LocalDateTime sale_started_time;

    private LocalDateTime sale_end_time;

    private LocalDateTime created_time;

    private LocalDateTime updated_time;



}
