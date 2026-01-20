import java.math.BigDecimal;
import java.math.BigInteger;

public class IntegerStats {

    private long count = 0;
    private BigInteger min = null;
    private BigInteger max = null;
    private BigInteger sum = null;

    public void accept(BigInteger value) {}

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

    public BigDecimal getAverage() {}
}
