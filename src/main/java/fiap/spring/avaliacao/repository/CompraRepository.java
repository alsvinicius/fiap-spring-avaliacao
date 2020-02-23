package fiap.spring.avaliacao.repository;

import fiap.spring.avaliacao.model.Cartao;
import fiap.spring.avaliacao.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Integer> {

    public List<Compra> findByCartao(Cartao cartao);

}
