package fiap.spring.avaliacao.service;

import fiap.spring.avaliacao.model.Aluno;
import fiap.spring.avaliacao.repository.AlunoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlunoServiceTest {

    @InjectMocks
    AlunoService alunoService = new AlunoService();

    @BeforeAll
    void init(@Mock AlunoRepository alunoRepository) {
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(new Aluno());
        alunos.add(new Aluno());

        when(alunoRepository.save(any())).thenReturn(new Aluno());
        when(alunoRepository.findAll()).thenReturn(alunos);
        when(alunoRepository.findAllByPossuiCartao(anyBoolean())).thenReturn(alunos);
        when(alunoRepository.getOne(anyInt())).thenReturn(new Aluno());

        ReflectionTestUtils.setField(alunoService, "alunoRepository", alunoRepository);
    }

    @Test
    void insert() {
        assertEquals(Aluno.class, alunoService.insert(new Aluno()).getClass());
    }

    @Test
    void update() {
        assertEquals(Aluno.class, alunoService.update(1, new Aluno()).getClass());
    }

    @Test
    void delete() {
        alunoService.delete(1);
    }

    @Test
    void list() {
        assertEquals(2, alunoService.list().size());
    }

    @Test
    void listByCard() {
        assertEquals(2, alunoService.listByCard(false).size());
    }

    @Test
    void get() {
        assertEquals(Aluno.class, alunoService.get(1).getClass());
    }

}
