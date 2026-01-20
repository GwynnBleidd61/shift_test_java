import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CliOptionsFlagsTest {

    @Test
    void append_flag_sets_append_true() {
        CliOptions opt = CliOptions.parse(new String[]{"-a", "in1.txt"});
        assertTrue(opt.isAppend());
        assertEquals(1, opt.getInputFiles().size());
    }

    @Test
    void no_append_flag_defaults_to_false() {
        CliOptions opt = CliOptions.parse(new String[]{"in1.txt"});
        assertFalse(opt.isAppend());
    }

    @Test
    void short_stats_flag_sets_stats_mode_short() {
        CliOptions opt = CliOptions.parse(new String[]{"-s", "in1.txt"});
        assertEquals(CliOptions.StatsMode.SHORT, opt.getStatsMode());
    }

    @Test
    void full_stats_flag_sets_stats_mode_full() {
        CliOptions opt = CliOptions.parse(new String[]{"-f", "in1.txt"});
        assertEquals(CliOptions.StatsMode.FULL, opt.getStatsMode());
    }

    @Test
    void stats_flags_last_one_wins() {
        CliOptions opt = CliOptions.parse(new String[]{"-s", "-f", "in1.txt"});
        assertEquals(CliOptions.StatsMode.FULL, opt.getStatsMode());

        CliOptions opt2 = CliOptions.parse(new String[]{"-f", "-s", "in1.txt"});
        assertEquals(CliOptions.StatsMode.SHORT, opt2.getStatsMode());
    }

}
