import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultWritersTest {

    @TempDir
    Path tempDir;

    @Test
    void does_not_create_files_until_first_write() throws Exception {
        ResultWriters w = new ResultWriters(tempDir, "", false);

        Path intFile = tempDir.resolve("integers.txt");
        Path floatFile = tempDir.resolve("floats.txt");
        Path strFile = tempDir.resolve("strings.txt");

        assertFalse(Files.exists(intFile));
        assertFalse(Files.exists(floatFile));
        assertFalse(Files.exists(strFile));

        w.close();
    }

    @Test
    void creates_only_needed_file_on_write() throws Exception {
        ResultWriters w = new ResultWriters(tempDir, "", false);

        w.write(ValueType.INTEGER, "45");
        w.close();

        assertTrue(Files.exists(tempDir.resolve("integers.txt")));
        assertFalse(Files.exists(tempDir.resolve("floats.txt")));
        assertFalse(Files.exists(tempDir.resolve("strings.txt")));

        List<String> lines = Files.readAllLines(tempDir.resolve("integers.txt"), StandardCharsets.UTF_8);
        assertEquals(List.of("45"), lines);
    }

    @Test
    void applies_prefix_and_output_dir() throws Exception {
        ResultWriters w = new ResultWriters(tempDir, "sample-", false);

        w.write(ValueType.STRING, "Hello");
        w.close();

        Path out = tempDir.resolve("sample-strings.txt");
        assertTrue(Files.exists(out));

        List<String> lines = Files.readAllLines(out, StandardCharsets.UTF_8);
        assertEquals(List.of("Hello"), lines);
    }

    @Test
    void append_mode_adds_to_existing_file() throws Exception {
        Path out = tempDir.resolve("integers.txt");
        Files.write(out, List.of("1"), StandardCharsets.UTF_8);

        ResultWriters w = new ResultWriters(tempDir, "", true);
        w.write(ValueType.INTEGER, "2");
        w.close();

        List<String> lines = Files.readAllLines(out, StandardCharsets.UTF_8);
        assertEquals(List.of("1", "2"), lines);
    }

    @Test
    void overwrite_mode_replaces_existing_file() throws Exception {
        Path out = tempDir.resolve("integers.txt");
        Files.write(out, List.of("old"), StandardCharsets.UTF_8);

        ResultWriters w = new ResultWriters(tempDir, "", false);
        w.write(ValueType.INTEGER, "new");
        w.close();

        List<String> lines = Files.readAllLines(out, StandardCharsets.UTF_8);
        assertEquals(List.of("new"), lines);
    }
}
