package Models;

public enum Category {
    PERSONAL(1),
    WORK(2),
    EVENT(3),
    MISCELLANEOUS(4);

    private int categoryIndex;

    private Category(int categoryIndex)
    {
        this.categoryIndex = categoryIndex;
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }
}
