package fi.helsinki.cs.okkopa.main.stage;

public abstract class Stage<I, O> {

    private Stage<O, ?> next;

    /**
     * Sets the Stage after this Stage.
     *
     * @param next Stage after this Stage.
     */
    public void setNext(Stage<O, ?> next) {
        this.next = next;
    }

    /**
     * What the Stage does.
     *
     * @param in What the Stage gets from the Stage before it.
     */
    public abstract void process(I in);

    /**
     * Should be called in process(). Makes other Stages after this one process
     * the object.
     *
     * @param out Object given to Stage after this.
     */
    public void processNextStages(O out) {
        if (next != null) {
            next.process(out);
        }
    }
}
