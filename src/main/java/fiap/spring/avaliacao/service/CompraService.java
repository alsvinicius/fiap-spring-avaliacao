package fiap.spring.avaliacao.service;

import fiap.spring.avaliacao.model.Cartao;
import fiap.spring.avaliacao.model.Compra;
import fiap.spring.avaliacao.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    @Autowired
    CompraRepository compraRepository;

    @Autowired
    CartaoService cartaoService;

    public Compra insert(Integer idCartao, Compra compra) {
        Cartao cartao = cartaoService.get(idCartao);
        cartao.setCartaoId(idCartao);
        cartao.setLimiteAtual(cartao.getLimiteAtual() - compra.getValor());
        compra.setCartao(cartao);
        return compraRepository.save(compra);
    }

    public void delete(Integer idCartao, Integer id) {
        Compra compra = get(id);
        Cartao cartao = cartaoService.get(idCartao);
        cartao.setCartaoId(idCartao);
        cartao.setLimiteAtual(cartao.getLimiteAtual() + compra.getValor());
        compraRepository.delete(compra);
    }

    public List<Compra> list() {
        return compraRepository.findAll();
    }

    public List<Compra> listByCartao(Integer idCartao) {
        Cartao cartao = new Cartao();
        cartao.setCartaoId(idCartao);
        return compraRepository.findByCartao(cartao);
    }

    public Compra get(Integer id) {
        return compraRepository.getOne(id);
    }

}
