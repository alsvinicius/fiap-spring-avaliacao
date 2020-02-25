package fiap.spring.avaliacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Compra {

    @Id
    @JoinColumn
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer compraId;
    @JsonIgnore
    @Transient
    private Integer cartaoId;
    @ManyToOne
    @JoinColumn(name = "cartaoId")
    @JsonIgnore
    @PodamExclude
    private Cartao cartao;
    private String descricao;
    private double valor;
    @CreatedDate
    private Date data;

}
