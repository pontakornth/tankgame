package wobject;

/**
 * Interface for an object that can belong to a faction.
 */
public interface FactionObject {
    Faction getFaction();
    boolean sameFaction(FactionObject factionObject);
}
