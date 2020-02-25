package fiap.spring.avaliacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartaoId;
    private String numero;
    private String dataValidade;
    private Integer diaVencimento;
    private double limiteTotal;
    private double limiteAtual;
    @JsonIgnore
    @Transient
    private Integer alunoId;
    @ManyToOne
    @JsonIgnore
    @PodamExclude
    private Aluno aluno;
    @OneToMany(mappedBy = "cartao")
    @JsonIgnore
    @PodamExclude
    private Set<Compra> transacoes;

}
