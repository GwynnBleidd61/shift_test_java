import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumMap;
import java.util.Map;

public class ResultWriters implements AutoCloseable {

    private final Path outputDir;
    private final String prefix;
    private final boolean append;

    private final Map<ValueType, BufferedWriter> writers = new EnumMap<>(ValueType.class);

    public ResultWriters(Path outputDir, String prefix, boolean append) {
        this.outputDir = outputDir;
        this.prefix = prefix == null ? "" : prefix;
        this.append = append;
    }

    public void write(ValueType type, String line) throws IOException {
        BufferedWriter w = writers.get(type);
        if (w == null) {
            w = openWriter(type);
            writers.put(type, w);
        }
        w.write(line);
        w.newLine();
        w.flush();
    }

    private BufferedWriter openWriter(ValueType type) throws IOException {
        Files.createDirectories(outputDir);

        Path file = outputDir.resolve(prefix + baseName(type));
        if (append) {
            return Files.newBufferedWriter(
                    file,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND
            );
        } else {
            return Files.newBufferedWriter(
                    file,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        }
    }

    private static String baseName(ValueType type) {
        return switch (type) {
            case INTEGER -> "integers.txt";
            case FLOAT -> "floats.txt";
            case STRING -> "strings.txt";
        };
    }

    @Override
    public void close() throws Exception {
        IOException first = null;
        for (BufferedWriter w : writers.values()) {
            try {
                w.close();
            } catch (IOException e) {
                if (first == null) first = e;
            }
        }
        writers.clear();
        if (first != null) throw first;
    }
}
