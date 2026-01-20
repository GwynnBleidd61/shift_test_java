import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringStatsTest {

    @Test
    void empty_stats_has_zero_count_and_no_min_max_len() {
        StringStats st = new StringStats();

        assertEquals(0, st.getCount());
        assertNull(st.getMinLength());
        assertNull(st.getMaxLength());
    }

    @Test
    void collects_count_min_len_and_max_len() {
        StringStats st = new StringStats();

        st.accept("Hello");
        st.accept("A");
        st.accept("Пример");

        assertEquals(3, st.getCount());
        assertEquals(1, st.getMinLength());
        assertEquals(6, st.getMaxLength());
    }

    @Test
    void counts_empty_string_as_string_with_length_zero() {
        StringStats st = new StringStats();

        st.accept("");

        assertEquals(1, st.getCount());
        assertEquals(0, st.getMinLength());
        assertEquals(0, st.getMaxLength());
    }
}
