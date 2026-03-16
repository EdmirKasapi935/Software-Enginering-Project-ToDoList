package Observers;

import Models.ReportData;

public interface ReportObserver {

    public void onReportStateChanged(ReportData reportData);

}
