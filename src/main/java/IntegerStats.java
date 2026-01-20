import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class IntegerStats {

    private long count = 0;
    private BigInteger min = null;
    private BigInteger max = null;
    private BigInteger sum = null;

    public void accept(BigInteger value) {
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

    public BigInteger getMin() {
        return min;
    }

    public BigInteger getMax() {
        return max;
    }

    public BigInteger getSum() {
        return sum;
    }

    public BigDecimal getAverage() {
        if (count == 0) return null;
        return new BigDecimal(sum).divide(BigDecimal.valueOf(count), 10, RoundingMode.HALF_UP );
    }
}
