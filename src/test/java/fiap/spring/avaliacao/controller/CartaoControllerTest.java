package fiap.spring.avaliacao.controller;

import fiap.spring.avaliacao.model.Cartao;
import fiap.spring.avaliacao.model.Compra;
import fiap.spring.avaliacao.service.CartaoService;
import fiap.spring.avaliacao.service.CompraService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartaoControllerTest {

    CartaoController cartaoController = new CartaoController();

    @BeforeAll
    public void init(@Mock CartaoService cartaoService, @Mock CompraService compraService) {
        ReflectionTestUtils.setField(cartaoController, "cartaoService", createCartaoServiceMock(cartaoService));

        when(compraService.listByCartao(anyInt())).thenReturn(new ArrayList<Compra>());
        ReflectionTestUtils.setField(cartaoController, "compraService", compraService);
    }

    public CartaoService createCartaoServiceMock(CartaoService cartaoService) {
        Cartao cartao = new Cartao();
        cartao.setCartaoId(1234);
        List<Cartao> cartoes = new ArrayList<>();
        cartoes.add(cartao);
        when(cartaoService.insert(anyInt(), any())).thenReturn(cartao);
        when(cartaoService.get(anyInt())).thenReturn(cartao);
        when(cartaoService.list(anyInt())).thenReturn(cartoes);

        return cartaoService;
    }

    @Test
    public void create() {
        assertEquals(1234, cartaoController.create(123, new Cartao()).getCartaoId());
    }

    @Test
    public void delete() {
        assertEquals(HttpStatus.NO_CONTENT, cartaoController.delete(123, 123).getStatusCode());
    }

    @Test
    public void list() {
        assertEquals(1, cartaoController.list(123).size());
    }

    @Test
    public void getExtrato() throws IOException {
        cartaoController.getExtrato(123, new MockHttpServletResponse());
    }

}
