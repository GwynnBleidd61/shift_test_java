public final class StatsPrinter {

    private StatsPrinter() {}

    public static String format(CliOptions.StatsMode mode, ProcessingResult r) {
        if (mode == null || mode == CliOptions.StatsMode.NONE) return "";

        StringBuilder sb = new StringBuilder();

        if (mode == CliOptions.StatsMode.SHORT) {
            sb.append("Integers: count=").append(r.getIntegerStats().getCount()).append('\n');
            sb.append("Floats: count=").append(r.getFloatStats().getCount()).append('\n');
            sb.append("Strings: count=").append(r.getStringStats().getCount()).append('\n');
            return sb.toString();
        }

        appendFullIntegers(sb, r.getIntegerStats());
        appendFullFloats(sb, r.getFloatStats());
        appendFullStrings(sb, r.getStringStats());
        return sb.toString();
    }

    private static void appendFullIntegers(StringBuilder sb, IntegerStats st) {
        sb.append("Integers: count=").append(st.getCount());
        if (st.getCount() == 0) {
            sb.append('\n');
            return;
        }
        sb.append(", min=").append(st.getMin())
                .append(", max=").append(st.getMax())
                .append(", sum=").append(st.getSum())
                .append(", avg=").append(st.getAverage())
                .append('\n');
    }

    private static void appendFullFloats(StringBuilder sb, FloatStats st) {
        sb.append("Floats: count=").append(st.getCount());
        if (st.getCount() == 0) {
            sb.append('\n');
            return;
        }
        sb.append(", min=").append(st.getMin())
                .append(", max=").append(st.getMax())
                .append(", sum=").append(st.getSum())
                .append(", avg=").append(st.getAverage())
                .append('\n');
    }

    private static void appendFullStrings(StringBuilder sb, StringStats st) {
        sb.append("Strings: count=").append(st.getCount());
        if (st.getCount() == 0) {
            sb.append('\n');
            return;
        }
        sb.append(", minLen=").append(st.getMinLength())
                .append(", maxLen=").append(st.getMaxLength())
                .append('\n');
    }
}
