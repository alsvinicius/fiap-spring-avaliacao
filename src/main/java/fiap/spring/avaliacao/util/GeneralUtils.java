package fiap.spring.avaliacao.util;

import fiap.spring.avaliacao.model.Compra;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GeneralUtils {

    public static XSSFWorkbook gerarExtrato (List<Compra> compras) throws IOException {

        String excelFileName = "extrato.xlsx";

        String sheetName = "Extrato";

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName) ;

        XSSFRow row = sheet.createRow(0);

        XSSFCell cell = row.createCell(0);
        cell.setCellValue("Descrição");
        cell = row.createCell(1);
        cell.setCellValue("Valor");
        cell = row.createCell(2);
        cell.setCellValue("Data");

        int comprasIndex = 1;
        for (Compra compra : compras) {
            row = sheet.createRow(comprasIndex);
            cell = row.createCell(0);
            cell.setCellValue(compra.getDescricao());
            cell = row.createCell(1);
            cell.setCellValue(compra.getValor());
            cell = row.createCell(2);
            cell.setCellValue(formattedDate(compra.getData()));

            comprasIndex++;
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        return wb;
    }

    public static void writeToFile(String fileName, String content) throws IOException {
        FileWriter writer = new FileWriter(fileName, false);
        writer.write(content);
        writer.close();
    }

    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

    private static String formattedDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

}
