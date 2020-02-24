package fiap.spring.avaliacao.util;

import fiap.spring.avaliacao.model.Compra;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralUtilsTest {

    @Test
    public void gerarExtratoTest() throws IOException {
        Compra compra = new Compra();
        compra.setData(new Date());
        compra.setValor(10.22);
        compra.setDescricao("Compra Teste");
        List<Compra> compras = new ArrayList<>();
        compras.add(compra);

        assertEquals(XSSFWorkbook.class, GeneralUtils.gerarExtrato(compras).getClass());
    }

    @Test
    public void writeToFileTest() throws IOException {
        GeneralUtils.writeToFile("teste.txt", "");
    }

    @Test
    public void deleteFile() {
        GeneralUtils.deleteFile("teste.txt");
    }

}
