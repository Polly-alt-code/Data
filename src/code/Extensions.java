package code;

public class Extensions {

    public static int tryParseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int tryParseInt(Object value, int defaultValue) {
        return tryParseInt(String.valueOf(value), defaultValue);
    }
}
