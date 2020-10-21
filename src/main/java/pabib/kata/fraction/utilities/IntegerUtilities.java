package pabib.kata.fraction.utilities;

public class IntegerUtilities {
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
