import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


public class CliOptionsTest {

    @Test
    void no_options_only_files() {
        CliOptions opt = CliOptions.parse(new String[]{"in1.txt"});

        assertEquals(1, opt.getInputFiles().size());
        assertEquals("in1.txt", opt.getInputFiles().getFirst());
    }

}
