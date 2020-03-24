package ua.aleks4ay.kiyv.domain.name;

public enum TypePosition {
    NEW(0, "новый"),
    KB(1, "КБ"),
    FACTORY(2, "ЦЕХ"),
    TECHNOLOGICAL(3, "Техн."),
    ABC(4, "АВС"),
    OTHER(5, "Прочее");

    private final int typeIndex;
    private final String typeName;

    TypePosition(int i, String s) {
        this.typeIndex = i;
        this.typeName = s;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getTypeNameByIndex(int type) {
        for (TypePosition t : TypePosition.values()) {
            if (t.typeIndex == type) {
                return t.getTypeName();
            }
        }
        return "";
    }
}