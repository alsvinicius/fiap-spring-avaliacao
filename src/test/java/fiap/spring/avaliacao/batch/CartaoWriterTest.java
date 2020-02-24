package fiap.spring.avaliacao.batch;

import fiap.spring.avaliacao.model.Cartao;
import fiap.spring.avaliacao.service.CartaoService;
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
public class CartaoWriterTest {

    @Mock
    CartaoService cartaoService;

    @InjectMocks
    CartaoWriter writer = new CartaoWriter();

    @Test
    public void writeTest() throws Exception {
        Cartao cartao = new Cartao();
        List<Cartao> cartoes = new ArrayList<>();
        cartoes.add(cartao);
        writer.write(cartoes);
    }

}
