import java.math.BigDecimal;
import java.math.RoundingMode;

public class FloatStats {

    private long count = 0;
    private BigDecimal min = null;
    private BigDecimal max = null;
    private BigDecimal sum = null;

    public void accept(BigDecimal value) {
        if (value == null) return;

        if (count == 0) {
            min = value;
            max = value;
            sum = value;
        } else {
            if (value.compareTo(min) < 0) min = value;
            if (value.compareTo(max) > 0) max = value;
            sum = sum.add(value);
        }
        count++;
    }

    public long getCount() {
        return count;
    }

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getSum() {
        if (sum == null) return null;
        return sum.setScale(4, RoundingMode.HALF_UP);
    }

    public BigDecimal getAverage() {
        if (count == 0) return null;
        return sum
                .divide(BigDecimal.valueOf(count), 10, RoundingMode.HALF_UP)
                .setScale(10, RoundingMode.HALF_UP);

    }
}
