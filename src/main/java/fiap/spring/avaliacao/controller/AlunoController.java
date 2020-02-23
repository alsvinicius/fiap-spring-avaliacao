package fiap.spring.avaliacao.controller;

import fiap.spring.avaliacao.model.Aluno;
import fiap.spring.avaliacao.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("aluno")
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    @PostMapping
    public Aluno create(@RequestBody Aluno aluno) {
        return alunoService.insert(aluno);
    }

    @PatchMapping("/{idAluno}")
    public Aluno update(
            @PathVariable Integer idAluno,
            @RequestBody Aluno aluno
    ) {
        return alunoService.update(idAluno, aluno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(
            @PathVariable Integer id
    ) {
        alunoService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<Aluno> list(
            @RequestParam(required = false) Boolean possuiCartao
    ) {
        if(possuiCartao == null){
            return alunoService.list();
        }

        return alunoService.listByCard(possuiCartao);
    }
}
