package fiap.spring.avaliacao.batch;

import fiap.spring.avaliacao.model.Compra;
import fiap.spring.avaliacao.service.CompraService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static fiap.spring.avaliacao.util.GeneralUtils.writeToFile;

@Component
public class CompraWriter implements ItemWriter<Compra> {

    @Autowired
    CompraService compraService;

    @Override
    public void write(List<? extends Compra> list) throws Exception {
        for(Compra compra : list) {
            compraService.insert(compra.getCartaoId(), compra);
        }

        writeToFile(
                "./files/pending.txt",
                new String("")
        );
    }
}
