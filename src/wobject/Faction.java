package wobject;

public enum Faction {
    // Player 1
    Blue,
    // Player 2
    Red,
    // AI
    Gray;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
