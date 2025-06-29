package rs.ac.singidunum.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "orders")
public class Orders {

    @Id
    @Column(name ="orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @ManyToOne(optional = false)
   @JoinColumn(name = "customer_id" ,nullable = false)
   private Customer customer;

   @Column(nullable = false)
   private Integer productId;

   @Column(nullable = false)
   private String productName;

   @Column(nullable = false)
   private Integer totalPrice;

   @Column(nullable = false)
   private Integer quantity;
   
   private LocalDateTime paidAt;

   @Column(nullable = false)
   private LocalDateTime createdAt;

   private LocalDateTime updatedAt;

   @JsonIgnore
   private LocalDateTime deletedAt;

}
