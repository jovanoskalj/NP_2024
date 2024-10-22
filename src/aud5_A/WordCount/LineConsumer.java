package aud5_A.WordCount;

import java.util.function.Consumer;

public class LineConsumer implements Consumer<String> {

    private  int lines;
    private  int words;
    private  int chars;

    public LineConsumer(int lines, int words, int chars) {
        this.lines = lines;
        this.words = words;
        this.chars = chars;
    }

    public LineConsumer() {

    }


    @Override
    public void accept(String s) {
        ++lines;
        words = s.split("\\s+").length;
        chars=s.length();
    }

    @Override
    public String toString() {
        return "LineConsumer{" +
                "lines=" + lines +
                ", words=" + words +
                ", chars=" + chars +
                '}';
    }
}
