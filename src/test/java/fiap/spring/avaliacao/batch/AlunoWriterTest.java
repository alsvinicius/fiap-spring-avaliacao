package fiap.spring.avaliacao.batch;

import fiap.spring.avaliacao.model.Aluno;
import fiap.spring.avaliacao.service.AlunoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlunoWriterTest {

    @Mock
    AlunoService alunoService;

    @InjectMocks
    AlunoWriter writer = new AlunoWriter();

    @Test
    public void writeTest() throws Exception {
        Aluno aluno = new Aluno();
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(aluno);
        writer.write(alunos);
    }

}
