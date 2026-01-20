import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

    @TempDir
    Path tempDir;

    @Test
    void processes_two_input_files_and_writes_by_type_in_order() throws Exception {
        // given
        Path in1 = tempDir.resolve("in1.txt");
        Path in2 = tempDir.resolve("in2.txt");

        Files.write(in1, List.of(
                "Hello",
                "45",
                "3.14"
        ), StandardCharsets.UTF_8);

        Files.write(in2, List.of(
                "World",
                "-1",
                "1.0E-3"
        ), StandardCharsets.UTF_8);

        CliOptions opt = new CliOptions(
                tempDir,
                "res_",
                false,
                CliOptions.StatsMode.NONE,
                List.of(in1.toString(), in2.toString())
        );

        ProcessingResult result = new FileProcessor().process(opt);

        Path ints = tempDir.resolve("res_integers.txt");
        Path flts = tempDir.resolve("res_floats.txt");
        Path strs = tempDir.resolve("res_strings.txt");

        assertTrue(Files.exists(ints));
        assertTrue(Files.exists(flts));
        assertTrue(Files.exists(strs));

        assertEquals(List.of("45", "-1"), Files.readAllLines(ints, StandardCharsets.UTF_8));
        assertEquals(List.of("3.14", "1.0E-3"), Files.readAllLines(flts, StandardCharsets.UTF_8));
        assertEquals(List.of("Hello", "World"), Files.readAllLines(strs, StandardCharsets.UTF_8));

        assertTrue(result.getErrors().isEmpty());
    }
}
