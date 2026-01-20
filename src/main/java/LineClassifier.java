import java.math.BigDecimal;
import java.math.BigInteger;

public class LineClassifier {
    public LineClassifier() {
    }

    public static ValueType classify(String line) {
        if (line == null) {
            return ValueType.STRING;
        }

        String s = line.trim();

        if (s.isEmpty()) {
            return ValueType.STRING;
        }

        try {
            new BigInteger(s);
            return ValueType.INTEGER;
        } catch (NumberFormatException ignored) {
        }

        try {
            new BigDecimal(s);
            return ValueType.FLOAT;
        } catch (NumberFormatException ignored) {

        }
        return ValueType.STRING;
    }

}
