package battleship.common;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GridData {

    int userScore;
    int botScore;

    boolean isFirstUserTurn;

    int height;
    int width;
    Map<String, Cell> cells;
    List<Ship> enemyShips;
    List<ShipType> shipTypes;
}
