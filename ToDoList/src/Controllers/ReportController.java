package Controllers;

import Data.ListRepository;
import Handlers.ReportHandler;
import Models.ReportData;

public class ReportController
{
    private ReportHandler reportHandler = new ReportHandler();

    public ReportData generateReport(ListRepository listRepository)
    {
        return reportHandler.processGenerateReport(listRepository.getAllLists());
    }

    public void exportReportData()
    {

    }

}
