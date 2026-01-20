import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CliOptionsValueOptionsTest {

    @Test
    void parses_output_dir_option_o() {
        CliOptions opt = CliOptions.parse(new String[]{"-o", "out, in1.txt"});
        assertEquals(Paths.get("out"), opt.getOutputDir());
        assertEquals(1, opt.getInputFiles().size());
        assertEquals("in1.txt", opt.getInputFiles().getFirst());
    }

    @Test
    void parses_prefix_option_p() {
        CliOptions opt = CliOptions.parse(new String[]{"-p", "sample-", "in1.txt"});
        assertEquals("sample-", opt.getPrefix());
    }

    @Test
    void parses_o_and_p_together_and_keeps_file_order() {
        CliOptions opt = CliOptions.parse(new String[] {
                "-o", "out",
                "-p", "res_",
                "in1.txt", "in2.txt"
        });

        assertEquals(Paths.get("out"), opt.getOutputDir());
        assertEquals("res_", opt.getPrefix());
        assertEquals(2, opt.getInputFiles().size());
        assertEquals("in1.txt", opt.getInputFiles().getFirst());
        assertEquals("in2.txt", opt.getInputFiles().get(1));
    }

    @Test
    void throws_on_missing_value_for_o() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> CliOptions.parse(new String[]{"-o"})
        );
        assertTrue(ex.getMessage().contains("Missing value for -o"));
    }

    @Test
    void throws_on_missing_value_for_p() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> CliOptions.parse(new String[]{"-p"})
        );
        assertTrue(ex.getMessage().contains("Missing value for -p"));
    }
}
