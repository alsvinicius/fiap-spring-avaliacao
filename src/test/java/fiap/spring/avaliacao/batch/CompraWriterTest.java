package fiap.spring.avaliacao.batch;

import fiap.spring.avaliacao.model.Compra;
import fiap.spring.avaliacao.service.CompraService;
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
public class CompraWriterTest {

    @Mock
    CompraService compraService;

    @InjectMocks
    CompraWriter writer = new CompraWriter();

    @Test
    public void writeTest() throws Exception {
        Compra compra = new Compra();
        List<Compra> compras = new ArrayList<>();
        compras.add(compra);
        writer.write(compras);
    }

}
