package fiap.spring.avaliacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer rm;
    private String nome;
    private String logradouro, cep, cidade, estado, pais, telefone;
    private Boolean possuiCartao = false;
    @OneToMany(mappedBy = "aluno")
    @JsonIgnore
    @PodamExclude
    private List<Cartao> cartoes;

}
