package fiap.spring.avaliacao.controller;

import fiap.spring.avaliacao.model.Aluno;
import fiap.spring.avaliacao.service.AlunoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlunoControllerTest {

    AlunoController alunoController = new AlunoController();

    @BeforeAll
    void init(@Mock AlunoService alunoService) {
        ReflectionTestUtils.setField(alunoController, "alunoService", createAlunoServiceMock(alunoService));
    }

    public AlunoService createAlunoServiceMock(AlunoService alunoService) {
        Aluno aluno = new Aluno();
        aluno.setRm(123);
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(aluno);
        aluno.setPossuiCartao(true);
        alunos.add(aluno);
        List<Aluno> alunosCartao = new ArrayList<>();
        alunosCartao.add(aluno);
        when(alunoService.insert(any())).thenReturn(aluno);
        when(alunoService.get(any())).thenReturn(aluno);
        when(alunoService.list()).thenReturn(alunos);
        when(alunoService.listByCard(true)).thenReturn(alunosCartao);
        when(alunoService.listByCard(false)).thenReturn(new ArrayList<>());

        return alunoService;
    }

    @Test
    public void insert() {
        assertEquals(123, alunoController.create(new Aluno()).getRm());
    }

    @Test
    public void delete() {
        assertEquals(HttpStatus.NO_CONTENT, alunoController.delete(1).getStatusCode());
    }

    @Test
    public void list() {
        assertEquals(2, alunoController.list(null).size());
        assertEquals(1, alunoController.list(true).size());
        assertEquals(0, alunoController.list(false).size());
    }

}
