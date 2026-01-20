import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class StatsPrinterTest {

    @Test
    void no_stats_mode_prints_empty_string() {
        ProcessingResult r = new ProcessingResult(new IntegerStats(), new FloatStats(), new StringStats(), java.util.List.of());
        String out = StatsPrinter.format(CliOptions.StatsMode.NONE, r);
        assertEquals("", out);
    }

    @Test
    void short_stats_prints_only_counts() {
        IntegerStats is = new IntegerStats();
        is.accept(new BigInteger("1"));
        is.accept(new BigInteger("2"));

        FloatStats fs = new FloatStats();
        fs.accept(new BigDecimal("3.0"));

        StringStats ss = new StringStats();
        ss.accept("a");
        ss.accept("bb");
        ss.accept("ccc");

        ProcessingResult r = new ProcessingResult(is, fs, ss, java.util.List.of());

        String out = StatsPrinter.format(CliOptions.StatsMode.SHORT, r);

        assertTrue(out.contains("Integers: count=2"));
        assertTrue(out.contains("Floats: count=1"));
        assertTrue(out.contains("Strings: count=3"));
    }

    @Test
    void full_stats_prints_numbers_min_max_sum_avg_and_string_lengths() {
        IntegerStats is = new IntegerStats();
        is.accept(new BigInteger("10"));
        is.accept(new BigInteger("-5"));

        FloatStats fs = new FloatStats();
        fs.accept(new BigDecimal("1.50"));
        fs.accept(new BigDecimal("2.50"));

        StringStats ss = new StringStats();
        ss.accept("a");
        ss.accept("abcd");

        ProcessingResult r = new ProcessingResult(is, fs, ss, java.util.List.of());

        String out = StatsPrinter.format(CliOptions.StatsMode.FULL, r);


        assertTrue(out.contains("Integers: count=2"));
        assertTrue(out.contains("min=-5"));
        assertTrue(out.contains("max=10"));
        assertTrue(out.contains("sum=5"));
        assertTrue(out.contains("avg=2.5000000000"));

        assertTrue(out.contains("Floats: count=2"));
        assertTrue(out.contains("min=1.50"));
        assertTrue(out.contains("max=2.50"));
        assertTrue(out.contains("sum=4.0000"));
        assertTrue(out.contains("avg=2.0000000000"));

        assertTrue(out.contains("Strings: count=2"));
        assertTrue(out.contains("minLen=1"));
        assertTrue(out.contains("maxLen=4"));
    }
}
