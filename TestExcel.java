import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;

public class TestExcel {
    public static void main(String[] args) {
        try {
            // Read Excel file
            InputStream is = new FileInputStream("d:\\erp\\test_bom_excel.xlsx");
            Workbook wb = WorkbookFactory.create(is);
            
            // Get first sheet
            Sheet sheet = wb.getSheetAt(0);
            
            // Read header row
            Row headerRow = sheet.getRow(0);
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                System.out.print(cell.getStringCellValue() + "\t");
            }
            System.out.println();
            
            // Read data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row dataRow = sheet.getRow(i);
                for (int j = 0; j < dataRow.getLastCellNum(); j++) {
                    Cell cell = dataRow.getCell(j);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                System.out.print(cell.getStringCellValue() + "\t");
                                break;
                            case NUMERIC:
                                System.out.print(cell.getNumericCellValue() + "\t");
                                break;
                            default:
                                System.out.print("\t");
                                break;
                        }
                    } else {
                        System.out.print("\t");
                    }
                }
                System.out.println();
            }
            
            wb.close();
            is.close();
            System.out.println("Excel file parsed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}