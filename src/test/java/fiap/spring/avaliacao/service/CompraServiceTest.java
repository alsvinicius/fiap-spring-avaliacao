package fiap.spring.avaliacao.service;

import fiap.spring.avaliacao.controller.CartaoControllerTest;
import fiap.spring.avaliacao.model.Compra;
import fiap.spring.avaliacao.repository.CompraRepository;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompraServiceTest {

    @InjectMocks
    CompraService compraService = new CompraService();

    @BeforeAll
    void init(@Mock CompraRepository compraRepository, @Mock CartaoService cartaoService) {
        List<Compra> compras = new ArrayList<>();

        when(compraRepository.save(any())).thenReturn(new Compra());
        when(compraRepository.findByCartao(any())).thenReturn(compras);
        when(compraRepository.getOne(anyInt())).thenReturn(new Compra());

        cartaoService = new CartaoControllerTest().createCartaoServiceMock(cartaoService);

        ReflectionTestUtils.setField(compraService, "compraRepository", compraRepository);
        ReflectionTestUtils.setField(compraService,"cartaoService", cartaoService);
    }

    @Test
    void insert() {
        Assertions.assertEquals(Compra.class, compraService.insert(1, new Compra()).getClass());
    }

    @Test
    void delete() {
        compraService.delete(1, 1);
    }

    @Test
    void list() {
        Assertions.assertEquals(0, compraService.list().size());
    }

    @Test
    void listByCartao() {
        Assertions.assertEquals(0, compraService.listByCartao(1).size());
    }

    @Test
    void get() {
        Assertions.assertEquals(Compra.class, compraService.get(1).getClass());
    }

}
