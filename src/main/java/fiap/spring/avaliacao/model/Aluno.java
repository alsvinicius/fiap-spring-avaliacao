package fiap.spring.avaliacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
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
    private List<Cartao> cartoes;

}
