package fiap.spring.avaliacao.service;

import fiap.spring.avaliacao.model.Aluno;
import fiap.spring.avaliacao.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public Aluno insert(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public void delete(Integer id) {
        Aluno aluno = new Aluno();
        aluno.setId(id);
        alunoRepository.delete(aluno);
    }

    public Aluno update(Integer id, Aluno novoAluno) {
        return alunoRepository.save(novoAluno);
    }

    public List<Aluno> list() {
        return alunoRepository.findAll();
    }

    public List<Aluno> listByCard(Boolean possuiCartao) {
        return alunoRepository.findAllByPossuiCartao(possuiCartao);
    }

    public Aluno get(Integer id) {
        return alunoRepository.getOne(id);
    }

}
