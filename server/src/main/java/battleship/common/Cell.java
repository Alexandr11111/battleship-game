package battleship.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell {
    Position position;
    Ship ship;
    boolean isClicked;
}
