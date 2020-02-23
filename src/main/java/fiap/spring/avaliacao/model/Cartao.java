package fiap.spring.avaliacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
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
    private Aluno aluno;
    @OneToMany(mappedBy = "cartao")
    @JsonIgnore
    private Set<Compra> transacoes;

}
