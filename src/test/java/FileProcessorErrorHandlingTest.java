import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorErrorHandlingTest {

    @TempDir
    Path tempDir;

    @Test
    void missing_input_file_is_reported_but_other_files_are_processed() throws Exception {
        Path good = tempDir.resolve("good.txt");
        Files.write(good, List.of("1", "hello"), StandardCharsets.UTF_8);

        Path missing = tempDir.resolve("missing.txt");

        CliOptions opt = new CliOptions(
                tempDir,
                "",
                false,
                CliOptions.StatsMode.NONE,
                List.of(missing.toString(), good.toString())
        );

        ProcessingResult r = new FileProcessor().process(opt);

        assertFalse(r.getErrors().isEmpty());

        assertTrue(Files.exists(tempDir.resolve("integers.txt")));
        assertTrue(Files.exists(tempDir.resolve("strings.txt")));
        assertFalse(Files.exists(tempDir.resolve("floats.txt")));

        assertEquals(List.of("1"), Files.readAllLines(tempDir.resolve("integers.txt"), StandardCharsets.UTF_8));
        assertEquals(List.of("hello"), Files.readAllLines(tempDir.resolve("strings.txt"), StandardCharsets.UTF_8));
    }
}
