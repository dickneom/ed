/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.config;

/**
 *
 * @author richneom
 */
public enum ConfigData {
    APP_TITLE("APP_TITLE", "Titulo", "", "String", "Edificio"),
    APP_LOOK_AND_FEEL("APP_LOOK_AND_FEEL", "WOPTIONS_PNL_APARIENCIA_LOOKANDFEEL", "", "String", ""),
    APP_DIR_WORK("APP_DIR_WORK", "Directorio de Trabajo", "", "String", ""),
    
    APP_LANGUAJE("APP_LANGUAJE", "Idioma", "", "String", "es"), // ya
    APP_COUNTRY("APP_COUNTRY", "País", "", "String", "EC"), // ya
    
    DBASE_DIR_DATA("APP_DIR_DATA", "WOPTIONS_PNL_DATA_PATH", "", "String", "/home/richneom/Programas/Edificio/"),
    DBASE_DATA_FILE("DBASE_DATA_FILE", "WOPTIONS_PNL_DATA_DATABASE", "", "String", "edificio05.db"),
//    DBASE_DATA_FULL_PATH("DBASE_CYBER_FULL_PATH", "Base de datos", "","String", "/home/richneom/Proyectos/Edificio/edificio05.db"), //
    DBASE_DATA_USER("DBASE_CYBER_USER", "WOPTIONS_PNL_DATA_USER", "","String", ""), //    
    DBASE_DATA_PASSWORD("DBASE_CYBER_PASSWORD", "WOPTIONS_PNL_DATA_PASSWORD", "","String", ""),
    
    IVA_PORCENTAJE("IVA_PORCENTAJE", "WOPTIONS_PNL_CONSTANTES_IVA_PORCENTAJE", "","Double", "12.00"),
    
    FORMAT_DATE("FORMAT_DATE", "Formato Fecha", "", "String", "yyyy-MM-dd"),
    FORMAT_DATE_HOUR_SHORT("FORMAT_DATE_HOUR", "Formato FechaHora", "", "String", "HH:mm"),
    FORMAT_DATE_HOUR_LONG("FORMAT_DATE_HOUR", "Formato FechaHora", "", "String", "HH:mm:ss"),
    FORMAT_DATE_HOUR("FORMAT_DATE_HOUR", "Formato FechaHora", "", "String", "yyyy-MM-dd HH:mm:ss"),
    FORMAT_NUMBER_DECIMAL_SHORT("FORMAT_NUMBER_DECIMAL_SHORT", "Formato Decmal Corto", "", "String", "### ### ##0.00;(-### ### ##0.00)"),
    FORMAT_NUMBER_DECIMAL_LONG("FORMAT_NUMBER_DECIMAL_LONG", "Formato Decimal Largo", "", "String", "### ### ##0.0000;(-### ### ##0.0000)"),
    FORMAT_SEPARATOR_DECIMAL("FORMAT_SEPARATOR_DECIMAL", "Separador Decimal", "", "String", "."),
    FORMAT_NUMBER_INT("FORMAT_NUMBER_INT", "Formato Enteros", "", "String", "### ### ##0;(-### ### ##0)"),
    
    COLUMN_WIDTH_ID("COLUMN_WIDTH_ID", "WOPTIONS_PNL_APARIENCIA_COL_WIDTH_ID", "", "Integer", "35"),
    COLUMN_WIDTH_BOOL("COLUMN_WIDTH_BOOL", "WOPTIONS_PNL_APARIENCIA_COL_WIDTH_BOOLEAN", "", "Integer", "20"),
    COLUMN_WIDTH_INT("COLUMN_WIDTH_INT", "WOPTIONS_PNL_APARIENCIA_COL_WIDTH_INTEGER", "", "Integer", "30"),
    COLUMN_WIDTH_DOUBLE_SHORT("COLUMN_WIDTH_DOUBLE_SHORT", "WOPTIONS_PNL_APARIENCIA_COL_WIDTH_REAL_SHORT", "", "Integer", "50"),
    COLUMN_WIDTH_DOUBLE_LONG("COLUMN_WIDTH_DOUBLE_LONG", "WOPTIONS_PNL_APARIENCIA_COL_WIDTH_REAL_LONG", "", "Integer", "60"),
    COLUMN_WIDTH_DATE("COLUMN_WIDTH_DATE", "WOPTIONS_PNL_APARIENCIA_COL_WIDTH_DATE", "", "Integer", "90"),
    
    INVOICES_YEAR_INIT("INVOICES_YEAR_INIT", "WOPTIONS_PNL_CONSTANTES_INVOICES_YEAR_INIT", "", "Integer", "2010"),
    INVOICES_YEAR_FINAL("INVOICES_YEAR_FINAL", "WOPTIONS_PNL_CONSTANTES_INVOICES_YEAR_FINAL", "", "Integer", "2025"),
    INVOICES_SERIE("INVOICES_SERIE", "WOPTIONS_PNL_CONTANTES_INVOICES_SERIE", "", "String", "002-001"),
//    INVOICES_NUMBER_LAST("INVOICES_NUMBER_LAST", "WOPTIONS_PNL_CONTANTES_INVOICES_NUMBER_LAST", "", "Integer", "1980"),
    INVOICES_NUMBER_TAM("INVOICES_NUMBER_TAM", "WOPTIONS_PNL_CONTANTES_INVOICES_NUMBER_TAM", "", "Integer", "9");
    
    
    
    private final String code;
    private final String title;
    private final String category;
    private final String type;
    private final String value;

    private ConfigData(String code, String title, String category, String type, String value) {
        this.code = code;
        this.title = title;
        this.category = category;
        this.type = type;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
    
    
}
