package fi.helsinki.cs.okkopa.main.stage;

public abstract class Stage<I, O> {
    
    private Stage<O, ?> next;
    
    public void setNext(Stage<O, ?> next) {
        this.next = next;
    }
    
    public abstract void process(I in);
    
    public void processNextStages(O out) {
        if (next != null) {
            next.process(out);
        }
    }
}
