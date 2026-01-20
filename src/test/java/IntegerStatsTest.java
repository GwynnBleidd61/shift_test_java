import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class IntegerStatsTest {

    @Test
    void empty_stats_has_zero_count_and_no_min_max_sum_avg() {
        IntegerStats st = new IntegerStats();

        assertEquals(0, st.getCount());
        assertNull(st.getMin());
        assertNull(st.getMax());
        assertNull(st.getSum());
        assertNull(st.getAverage());
    }

    @Test
    void collects_count_min_max_sum_and_average() {
        IntegerStats st = new IntegerStats();

        st.accept(new BigInteger("45"));
        st.accept(new BigInteger("100500"));
        st.accept(new BigInteger("1234567890123456789"));

        assertEquals(3, st.getCount());
        assertEquals(new BigInteger("45"), st.getMin());
        assertEquals(new BigInteger("1234567890123456789"), st.getMax());
        assertEquals(new BigInteger("1234567890123557334"), st.getSum());

        assertEquals(new BigDecimal("411522630041185778.0000000000"), st.getAverage());
    }
}
