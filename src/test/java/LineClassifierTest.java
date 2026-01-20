import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LineClassifierTest {

    @Test
    void detects_integer() {
        assertEquals(ValueType.INTEGER, LineClassifier.classify("42"));
        assertEquals(ValueType.INTEGER, LineClassifier.classify("-100023"));
    }

    @Test
    void detects_float() {
        assertEquals(ValueType.FLOAT, LineClassifier.classify("3.14"));
        assertEquals(ValueType.FLOAT, LineClassifier.classify("-0.001"));
        assertEquals(ValueType.FLOAT, LineClassifier.classify("1.534532323E-25"));
    }

    @Test
    void detects_string() {
        assertEquals(ValueType.STRING, LineClassifier.classify("hello"));
        assertEquals(ValueType.STRING, LineClassifier.classify("1231adbas"));
        assertEquals(ValueType.STRING, LineClassifier.classify(""));
    }
}

