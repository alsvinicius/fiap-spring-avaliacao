package fiap.spring.avaliacao;

import fiap.spring.avaliacao.controller.AlunoController;
import fiap.spring.avaliacao.controller.CartaoController;
import fiap.spring.avaliacao.controller.CompraController;
import fiap.spring.avaliacao.model.Aluno;
import fiap.spring.avaliacao.model.Cartao;
import fiap.spring.avaliacao.model.Compra;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@Transactional
public class IntegrationTests {

    @Autowired
    AlunoController alunoController;

    @Autowired
    CartaoController cartaoController;

    @Autowired
    CompraController compraController;

    PodamFactory factory = new PodamFactoryImpl();

    void cadastrarAlunos() {
        for (int i = 0; i < 5; i++) {
            Aluno aluno = factory.manufacturePojoWithFullData(Aluno.class);
            alunoController.create(aluno);
        }
    }

    public List<Aluno> listarAlunos() {
        return alunoController.list(null);
    }

    void cadastrarCartoes(List<Aluno> alunos) {
        for (Aluno alunoCadastrado : alunos) {
            Cartao cartao = factory.manufacturePojoWithFullData(Cartao.class);
            cartaoController.create(alunoCadastrado.getId(), cartao);
        }
    }

    public List<Cartao> listarCartoes(Aluno aluno) {
        return cartaoController.list(aluno.getId());
    }

    void gerarCompras(List<Aluno> alunos) {
        obterCartoesPorAluno(alunos);
    }

    void obterCartoesPorAluno(List<Aluno> alunos) {
        for (Aluno aluno : alunos) {
            cadastrarCompraPorCartao(aluno, listarCartoes(aluno));
        }
    }

    void cadastrarCompraPorCartao(Aluno aluno, List<Cartao> cartoes) {
        for (Cartao cartao : cartoes) {
            cadastrarCompras(aluno, cartao);
        }
    }

    void cadastrarCompras(Aluno aluno, Cartao cartao) {
        for (int i = 0; i < 5; i++) {
            Compra compra = factory.manufacturePojoWithFullData(Compra.class);
            compra.setCartaoId(cartao.getCartaoId());
            compraController.create(cartao.getCartaoId(), compra);
        }
    }

    List<Compra> listarCompras(Cartao cartao) {
        return compraController.list(cartao.getCartaoId());
    }

    void excluirAluno(Aluno aluno) {
        alunoController.delete(aluno.getId());
    }

    void excluirCartao(Aluno aluno, Cartao cartao) {
        cartaoController.delete(aluno.getId(), cartao.getCartaoId());
    }

    void excluirCompra(Cartao cartao, Compra compra) {
        compraController.delete(cartao.getCartaoId(), compra.getCompraId());
    }

    @Test
    void applicationFlow() {
        cadastrarAlunos();
        List<Aluno> alunos = listarAlunos();
        assertEquals(5, alunos.size());
        cadastrarCartoes(alunos);
        for (Aluno aluno : alunos) {
            assertEquals(1, listarCartoes(aluno).size());
            for (Cartao cartao : listarCartoes(aluno)) {
                cadastrarCompras(aluno, cartao);
                assertEquals(5, listarCompras(cartao).size());
            }
        }

        Aluno aluno = alunos.get(0);
        List<Cartao> cartoes = listarCartoes(aluno);

        for(Cartao cartao : cartoes) {
            List<Compra> compras = listarCompras(cartao);
            for(Compra compra : compras) {
                excluirCompra(cartao, compra);
            }
            assertEquals(0, listarCompras(cartao).size());

            excluirCartao(aluno, cartao);
        }
        assertEquals(0, listarCartoes(aluno).size());
        excluirAluno(aluno);
        assertTrue(alunos.size() != listarAlunos().size());
    }
}
