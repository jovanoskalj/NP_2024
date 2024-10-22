package aud5_A.WordCount;

public class LineCounter {
    private  int lines;
    private  int words;
    private  int chars;

    public LineCounter(int lines, int words, int chars) {
        this.lines = lines;
        this.words = words;
        this.chars = chars;
    }
    public LineCounter(String s){
        ++lines;
        words = s.split("\\s+").length;
        chars=s.length();
    }
    public LineCounter sum(LineCounter lineCounter){
        return new LineCounter(this.lines+lineCounter.lines, this.words+lineCounter.words,this
                .chars+lineCounter.chars);
    }
}
