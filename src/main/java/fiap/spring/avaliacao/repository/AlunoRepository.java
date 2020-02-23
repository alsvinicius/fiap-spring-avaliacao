package fiap.spring.avaliacao.repository;

import fiap.spring.avaliacao.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{

    List<Aluno> findAllByPossuiCartao(Boolean possuiCartao);

}
