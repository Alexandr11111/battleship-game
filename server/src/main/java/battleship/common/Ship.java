package battleship.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Ship {

    private ShipType shipType;

    private Set<Position> positions = new HashSet<>();

    private int life;
}
