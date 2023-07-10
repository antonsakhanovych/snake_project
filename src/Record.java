public class Record implements Comparable<Record>{
    private String name;
    private long score;

    public Record(String name, long score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public long getScore() {
        return score;
    }

    @Override
    public int compareTo(Record o) {
        return (int) (this.score - o.score);
    }
}
