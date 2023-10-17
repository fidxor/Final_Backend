package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DeleteBasket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delete_basket_id")
    private Long id;

    private Long memberId;
    private Long basketId;

    private Long contentId;
    private Long productId;
    private Long martId;

    private LocalDateTime deleteDate;

    public static DeleteBasket createDeleteBasket(Long memberId, Long basketId, Long contentId, Long productId, Long martId) {
        DeleteBasket deleteBasket = new DeleteBasket();

        deleteBasket.memberId = memberId;
        deleteBasket.basketId = basketId;
        deleteBasket.contentId = contentId;
        deleteBasket.productId = productId;
        deleteBasket.martId = martId;

        deleteBasket.deleteDate = LocalDateTime.now();

        return deleteBasket;

    }
}
