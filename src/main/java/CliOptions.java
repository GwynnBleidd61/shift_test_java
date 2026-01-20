import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CliOptions {
    private final List<String> inputFiles;

    public CliOptions(List<String> inputFiles) {
        this.inputFiles = List.copyOf(inputFiles);
    }

    public static CliOptions parse(String[] args) {
        List<String> files = new ArrayList<>(Arrays.asList(args));
        return new CliOptions(files);
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }
}
