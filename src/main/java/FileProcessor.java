import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class FileProcessor {

    public ProcessingResult process(CliOptions opt) {
        IntegerStats intStats = new IntegerStats();
        FloatStats floatStats = new FloatStats();
        StringStats stringStats = new StringStats();
        List<String> errors = new ArrayList<>();

        try (ResultWriters writers = new ResultWriters(opt.getOutputDir(), opt.getPrefix(), opt.isAppend())) {

            for (String fileName : opt.getInputFiles()) {
                Path path;
                try {
                    path = Path.of(fileName);
                } catch (Exception e) {
                    errors.add("Invalid input path: " + fileName + " (" + e.getMessage() + ")");
                    continue;
                }

                try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        ValueType t = LineClassifier.classify(line);

                        try {
                            writers.write(t, line);
                        } catch (IOException e) {
                            errors.add("Write failed for type " + t + ": " + e.getMessage());
                        }

                        // статистика
                        switch (t) {
                            case INTEGER -> {
                                try {
                                    intStats.accept(new BigInteger(line.trim()));
                                } catch (Exception e) {
                                    // классификатор сказал INTEGER, но парсинг не удался — это редкость, но не падаем
                                    errors.add("Failed to parse integer: '" + line + "'");
                                }
                            }
                            case FLOAT -> {
                                try {
                                    floatStats.accept(new BigDecimal(line.trim()));
                                } catch (Exception e) {
                                    errors.add("Failed to parse float: '" + line + "'");
                                }
                            }
                            case STRING -> stringStats.accept(line);
                        }
                    }
                } catch (IOException e) {
                    errors.add("Failed to read file: " + path + " (" + e.getMessage() + ")");
                }
            }

        } catch (Exception e) {
            errors.add("Output initialization failed: " + e.getMessage());
        }

        return new ProcessingResult(intStats, floatStats, stringStats, errors);
    }
}
