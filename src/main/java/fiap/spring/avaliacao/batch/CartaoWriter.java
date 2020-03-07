package fiap.spring.avaliacao.batch;

import fiap.spring.avaliacao.model.Cartao;
import fiap.spring.avaliacao.service.CartaoService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static fiap.spring.avaliacao.util.GeneralUtils.writeToFile;

@Component
public class CartaoWriter implements ItemWriter<Cartao> {

    @Autowired
    CartaoService cartaoService;

    @Override
    public void write(List<? extends Cartao> list) throws Exception {
        for(Cartao cartao : list) {
            cartaoService.insert(cartao.getAlunoId(), cartao);
        }

        writeToFile(
                "./files/pending.txt",
                new String("")
        );
    }
}
