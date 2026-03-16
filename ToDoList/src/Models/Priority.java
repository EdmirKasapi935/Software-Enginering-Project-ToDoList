package Models;

import java.io.Serializable;

public enum Priority implements Serializable {

    LOW(3),
    MEDIUM(2),
    HIGH(1);

    private int importanceIndex;

    public int getImportanceIndex() {
        return importanceIndex;
    }

    private Priority(int importanceIndex)
    {
        this.importanceIndex = importanceIndex;
    }
}
