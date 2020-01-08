package spring.boot.batch.constants;

public interface AppConstants {

    String STEP_CSV_TO_DB = "Csv-to-Db";
    String STEP_DB_TO_CSV = "Db-to-Csv";

    String STEP_COMPLETED = "COMPLETED";
    String STEP_FAILED = "FAILED";

    int STEP_CHUNK = 10;

    String[] EXTRACT_FIELDS = new String[] { "id", "name", "description", "price" };
    String DELIMETER = ";";
}