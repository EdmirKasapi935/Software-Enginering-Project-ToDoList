package Controllers;

import CustomExceptions.EmptyInputException;
import Data.ListRepository;
import Observers.ReportObserver;
import Services.ExportService;
import Services.ReportService;
import Models.ReportData;
import Views.ThemedDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReportController
{
    private static final ReportService reportHandler = new ReportService();
    private static final ExportService exportHandler = new ExportService();
    private static final ArrayList<ReportObserver> reportObservers = new ArrayList<>();

    public void addReportObserver(ReportObserver observer) {
        reportObservers.add(observer);
    }

    public static void notifyReportObserverFromBackground(ListRepository listRepository)//notifies the report observer from the background, the report view does not need an internal way to update the observer
    {
        reportObservers.forEach(n -> n.onReportStateChanged(reportHandler.processGenerateReport(listRepository.getAllLists())));
    }


    public ReportData generateReport(ListRepository listRepository)
    {
        return reportHandler.processGenerateReport(listRepository.getAllLists());
    }

    public void exportReportData(File file, ReportData report)
    {
        try {
            exportHandler.processExportReportData(file, report);
            ThemedDialog.message("Your report was exported successfully!", "Report Exported");
        } catch (IOException | EmptyInputException e) {
            ThemedDialog.message(e.getMessage(), "Error");
        }
    }
}