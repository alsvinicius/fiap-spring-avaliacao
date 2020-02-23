package fiap.spring.avaliacao.batch;

import fiap.spring.avaliacao.model.Aluno;
import fiap.spring.avaliacao.service.AlunoService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static fiap.spring.avaliacao.util.GeneralUtils.writeToFile;

@Component
public class AlunoWriter implements ItemWriter<Aluno> {

    @Autowired
    AlunoService alunoService;

    @Override
    public void write(List<? extends Aluno> list) throws Exception {
        for(Aluno aluno : list) {
            alunoService.insert(aluno);
        }
        writeToFile(
                "./files/pending.csv",
                new String("")
        );
    }
}
