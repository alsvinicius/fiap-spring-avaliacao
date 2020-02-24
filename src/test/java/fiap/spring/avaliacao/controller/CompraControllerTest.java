package fiap.spring.avaliacao.controller;

import fiap.spring.avaliacao.model.Compra;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompraControllerTest {

    CompraController compraController = new CompraController();

    @BeforeAll
    void init(@Mock CompraService compraService) {
        Compra compra = new Compra();
        compra.setDescricao("Compra Teste");
        compra.setValor(12.22);
        compra.setData(new Date());
        List<Compra> compras = new ArrayList<>();
        compras.add(compra);
        when(compraService.insert(anyInt(), any())).thenReturn(compra);
        when(compraService.listByCartao(anyInt())).thenReturn(compras);
        ReflectionTestUtils.setField(compraController, "compraService", compraService);
    }

    @Test
    public void insert() {
        assertEquals(12.22, compraController.create(123, new Compra()).getValor());
    }

    @Test
    public void delete() {
        assertEquals(HttpStatus.NO_CONTENT, compraController.delete(123, 123).getStatusCode());
    }

    @Test
    public void list() {
        assertEquals(1, compraController.list(123).size());
    }
}
