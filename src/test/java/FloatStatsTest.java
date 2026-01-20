import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FloatStatsTest {

    @Test
    void empty_stats_has_zero_count_and_no_min_max_sum_avg() {
        FloatStats st = new FloatStats();

        assertEquals(0, st.getCount());
        assertNull(st.getMin());
        assertNull(st.getMax());
        assertNull(st.getSum());
        assertNull(st.getAverage());
    }

    @Test
    void collects_count_min_max_sum_and_average() {
        FloatStats st = new FloatStats();

        st.accept(new BigDecimal("3.1415"));
        st.accept(new BigDecimal("-0.001"));
        st.accept(new BigDecimal("1.528535047E-25"));

        assertEquals(3, st.getCount());
        assertEquals(new BigDecimal("-0.001"), st.getMin());
        assertEquals(new BigDecimal("3.1415"), st.getMax());
        assertEquals(new BigDecimal("3.1405"), st.getSum());

        assertEquals(new BigDecimal("1.0468333333"), st.getAverage());
    }
}
