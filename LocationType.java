
/**
 * Enumeration class LocationType - write a description of the enum class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public enum LocationType
{
    TUNNEL,
    BRIDGE,
    HALL { 
        boolean locked = true;
    },
    STORE,
    TRAP,
    ENTRANCE,
    OUTSIDE,
    GUARDED,
    ROOM,
    DUNGEON,
    BEDROOM,
    BRIDGEGATE,
    
}
