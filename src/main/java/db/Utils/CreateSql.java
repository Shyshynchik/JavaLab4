package db.Utils;

public class CreateSql {
    private static String sql = "FROM ";

    public static String makeSql(String table, String name) {
        StringBuilder stringBuilder = new StringBuilder(sql);
        stringBuilder.append(table);
        stringBuilder.append(" WHERE name = '");
        stringBuilder.append(name);
        stringBuilder.append("'");

        return stringBuilder.toString();
    }
}
