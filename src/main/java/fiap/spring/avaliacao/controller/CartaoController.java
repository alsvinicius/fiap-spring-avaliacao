package fiap.spring.avaliacao.controller;

import fiap.spring.avaliacao.model.Cartao;
import fiap.spring.avaliacao.service.CartaoService;
import fiap.spring.avaliacao.service.CompraService;
import fiap.spring.avaliacao.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/aluno/{idAluno}/cartao")
public class CartaoController {

    @Autowired
    CartaoService cartaoService;

    @Autowired
    CompraService compraService;

    @PostMapping
    public Cartao create(
            @PathVariable Integer idAluno,
            @RequestBody Cartao cartao
    ) {
        return cartaoService.insert(idAluno, cartao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(
            @PathVariable Integer idAluno,
            @PathVariable Integer id
    ) {
        cartaoService.delete(idAluno, id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<Cartao> list(
            @PathVariable Integer idAluno
    ) {
        return cartaoService.list(idAluno);
    }

    @GetMapping("/{id}/extrato")
    @ResponseBody
    public void getExtrato(
            @PathVariable Integer id,
            HttpServletResponse response
    ) throws IOException {
        response.setHeader("Content-disposition", "attachment; filename=extrato.xlsx");
        GeneralUtils.gerarExtrato(compraService.listByCartao(id)).write(response.getOutputStream());
    }
}
