package ua.aleks4ay.kiyv.domain.name;

public enum StatusPosition {
    NEW(0, "?"),
    PARSED(1, "Распределен\nавтоматич."),
    ENTERED_KB(2, "Поступил\n  в КБ"),
    START_DESIGNING(3, "Разработка"),
    MATCHING(4, "Согласование\nс менеджером"),
    RENEWAL_DESIGNING(5, "Разработка\nпосле\nсогласования"),
    END_DESIGNING(6, "Разработка\nокончена"),
    START_MAKING(7, "Запуск\nв цехе"),
    LASER(8, "Лазер"),
    FLEXING(9, "Гибка"),
    WELDING(10, "Сварка"),
    POLISHING(11, "Полировка"),
    ASSEMBLY(12, "Сборка"),
    OTK(13, "ОТК"),
    STORAGE(14, "Склад"),
    SHIPMENT(15, "Отгрузка"),
    DONE_MAKING(16, "ВЫПОЛНЕН"),
    EMPTY(17, "---"),
    TECHNOLOGICAL(18, "Технологич.\nоборудов.");

    private final String statusName;
    private final int statusIndex;

    StatusPosition(int i, String s) {
        this.statusIndex = i;
        this.statusName = s;
    }

    public String getStatusName() {
        return statusName;
    }

    public int getStatusIndex() {
        return statusIndex;
    }

    public String getStatusNameByIndex(int status) {
        for (StatusPosition s : StatusPosition.values()) {
            if (s.statusIndex == status) {
                return s.getStatusName();
            }
        }
        return "";
    }
}