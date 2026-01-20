import java.util.ArrayList;
import java.util.List;

public class ProcessingResult {

    private final IntegerStats integerStats;
    private final FloatStats floatStats;
    private final StringStats stringStats;
    private final List<String> errors;

    public ProcessingResult(IntegerStats integerStats, FloatStats floatStats, StringStats stringStats, List<String> errors) {
        this.integerStats = integerStats;
        this.floatStats = floatStats;
        this.stringStats = stringStats;
        this.errors = List.copyOf(errors);
    }

    public IntegerStats getIntegerStats() {
        return integerStats;
    }

    public FloatStats getFloatStats() {
        return floatStats;
    }

    public StringStats getStringStats() {
        return stringStats;
    }

    public List<String> getErrors() {
        return errors;
    }

    public static ProcessingResult empty() {
        return new ProcessingResult(new IntegerStats(), new FloatStats(), new StringStats(), new ArrayList<>());
    }
}
