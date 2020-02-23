package fiap.spring.avaliacao.service;

import fiap.spring.avaliacao.model.Aluno;
import fiap.spring.avaliacao.model.Cartao;
import fiap.spring.avaliacao.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoService {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    AlunoService alunoService;

    public Cartao insert(Integer idAluno, Cartao cartao) {
        Aluno aluno = alunoService.get(idAluno);
        aluno.setPossuiCartao(true);
        alunoService.update(idAluno, aluno);
        cartao.setAluno(aluno);
        cartao.setLimiteAtual(cartao.getLimiteTotal());
        return cartaoRepository.save(cartao);
    }

    public void delete(Integer idAluno, Integer id) {
        Cartao cartao = new Cartao();
        cartao.setCartaoId(id);
        cartaoRepository.delete(cartao);
        if (list(idAluno).size() == 0) {
            Aluno aluno = alunoService.get(idAluno);
            aluno.setPossuiCartao(false);
            alunoService.update(idAluno, aluno);
        }
    }

    public List<Cartao> list(Integer idAluno) {
        Aluno aluno = new Aluno();
        aluno.setId(idAluno);
        return cartaoRepository.findAllByAluno(aluno);
    }

    public Cartao get(Integer id) {
        return cartaoRepository.getOne(id);
    }

}
