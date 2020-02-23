package fiap.spring.avaliacao.repository;

import fiap.spring.avaliacao.model.Aluno;
import fiap.spring.avaliacao.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao, Integer> {

    public List<Cartao> findAllByAluno(Aluno aluno);

}
