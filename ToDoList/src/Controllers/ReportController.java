package Controllers;

import Data.ListRepository;
import Services.ExportService;
import Services.ReportService;
import Models.ReportData;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class ReportController
{
    private final ReportService reportHandler = new ReportService();
    private final ExportService exportHandler = new ExportService();

    public ReportData generateReport(ListRepository listRepository)
    {
        return reportHandler.processGenerateReport(listRepository.getAllLists());
    }

    public void exportReportData(File file, ReportData report)
    {
        try {
            exportHandler.processExportReportData(file, report);
            JOptionPane.showMessageDialog(null, "Your report was exported successfully!", "Report Data Exported", JOptionPane.INFORMATION_MESSAGE);
        }catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
