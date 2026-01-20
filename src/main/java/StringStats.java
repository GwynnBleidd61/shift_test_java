public class StringStats {
    private long count = 0;
    private Integer minLength = null;
    private Integer maxLength = null;

    public void accept(String s) {
        if (s == null) return;

        int len = s.length();

        if (count == 0) {
            minLength = len;
            maxLength = len;
        } else {
            if (len < minLength) minLength = len;
            if (len > maxLength) maxLength = len;
        }

        count ++;
    }

    public long getCount() {
        return count;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }
}
