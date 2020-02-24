package fiap.spring.avaliacao.service;

import fiap.spring.avaliacao.controller.AlunoControllerTest;
import fiap.spring.avaliacao.model.Cartao;
import fiap.spring.avaliacao.repository.CartaoRepository;
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
public class CartaoServiceTest {

    @InjectMocks
    CartaoService cartaoService = new CartaoService();

    @BeforeAll
    void init(@Mock CartaoRepository cartaoRepository, @Mock AlunoService alunoService) {
        List<Cartao> cartoes = new ArrayList<>();

        when(cartaoRepository.save(any())).thenReturn(new Cartao());
        when(cartaoRepository.findAllByAluno(any())).thenReturn(cartoes);
        when(cartaoRepository.getOne(anyInt())).thenReturn(new Cartao());

        alunoService = new AlunoControllerTest().createAlunoServiceMock(alunoService);

        ReflectionTestUtils.setField(cartaoService, "cartaoRepository", cartaoRepository);
        ReflectionTestUtils.setField(cartaoService,"alunoService", alunoService);
    }

    @Test
    void insert() {
        Assertions.assertEquals(Cartao.class, cartaoService.insert(1, new Cartao()).getClass());
    }

    @Test
    void delete() {
        cartaoService.delete(1, 1);
    }

    @Test
    void list() {
        Assertions.assertEquals(0, cartaoService.list(1).size());
    }

    @Test
    void get() {
        Assertions.assertEquals(Cartao.class, cartaoService.get(1).getClass());
    }

}
