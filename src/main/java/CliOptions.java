import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CliOptions {
    public enum StatsMode {NONE, SHORT, FULL }

    private final Path outputDir;
    private final String prefix;
    private final boolean append;
    private final StatsMode statsMode;
    private final List<String> inputFiles;

    public CliOptions(Path outputDir, String prefix, boolean append, StatsMode statsMode, List<String> inputFiles) {
        this.outputDir = outputDir;
        this.prefix = prefix;
        this.append = append;
        this.statsMode = statsMode;
        this.inputFiles = List.copyOf(inputFiles);
    }

    public static CliOptions parse(String[] args) {
        Path outputDir = Paths.get(".");
        String prefix = "";
        boolean append = false;
        StatsMode statsMode = StatsMode.NONE;
        List<String> inputFiles = new ArrayList<>();
        
        for (int i = 0; i < args.length;) {
            String s = args[i];
            
            if ("-a".equals(s)) {
                append = true;
                i++;
            } else if ("-s".equals(s)) {
                statsMode = StatsMode.SHORT;
                i++;
            } else if ("-f".equals(s)) {
                statsMode = StatsMode.FULL;
                i++;
            } else if ("-o".equals(s)) {
                if (i + 1 >= args.length) throw new IllegalArgumentException("Missing value for -o");
                outputDir = Paths.get(args[i + 1]);
                i += 2;
            } else if ("-p".equals(s)) {
                if (i + 1 >= args.length) throw new IllegalArgumentException("Missing value for -p");
                prefix = args[i + 1];
                i += 2;
            }
            else if (s.startsWith("-")) {
                throw new IllegalArgumentException("Unknown option " + s);
            } else {
                inputFiles.add(s);
                i++;
            }
        }
        return new CliOptions(outputDir, prefix, append, statsMode, inputFiles);
    }

    public Path getOutputDir() {
        return outputDir;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppend() {
        return append;
    }

    public StatsMode getStatsMode() {
        return statsMode;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }
}
