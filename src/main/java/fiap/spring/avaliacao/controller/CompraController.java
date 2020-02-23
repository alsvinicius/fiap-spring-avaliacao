package fiap.spring.avaliacao.controller;

import fiap.spring.avaliacao.model.Compra;
import fiap.spring.avaliacao.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno/{idAluno}/cartao/{idCartao}/compra")
public class CompraController {

    @Autowired
    CompraService compraService;

    @PostMapping
    public Compra create(
            @PathVariable Integer idCartao,
            @RequestBody Compra compra
    ) {
        return compraService.insert(idCartao, compra);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(
            @PathVariable Integer idCartao,
            @PathVariable Integer id
    ) {
        compraService.delete(idCartao, id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<Compra> list(
            @PathVariable Integer idCartao
    ) {
        return compraService.listByCartao(idCartao);
    }
}
