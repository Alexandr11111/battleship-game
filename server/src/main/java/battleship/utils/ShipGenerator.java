package battleship.utils;

import battleship.Log;
import battleship.common.Position;
import battleship.common.Ship;
import battleship.common.ShipOnGrid;
import battleship.common.ShipType;

import java.util.ArrayList;
import java.util.List;

public class ShipGenerator {

    public static final int gridHeight = 10;

    public static final int gridWidth = 10;

    private List<ShipOnGrid> shipsOnGrid;

    public List<Ship> getShips () {

        List<Ship> ships = new ArrayList<>();
        this.shipsOnGrid = new ArrayList<>();
        List<ShipType> shipTypes = new ArrayList<>();

        shipTypes.add(new ShipType("carrier", 5));
        shipTypes.add(new ShipType("battleship", 4));
        shipTypes.add(new ShipType("cruiser", 3));
        shipTypes.add(new ShipType("submarine", 3));
        shipTypes.add(new ShipType("destroyer", 2));

        for (ShipType shipType : shipTypes) {
            Log.debug("shipType: " + shipType.getName());
            ships.add(getNewRandomShip(shipType));
        }
        return ships;
    }

    private List<ShipOnGrid> genShipPositions(int size) {
        List<ShipOnGrid> response = new ArrayList<>();
        for (int row = 0; row < gridHeight-size; row++) {
            for (int col = 0; col < gridWidth; col++) {
                ShipOnGrid shipOnGridResponse = new ShipOnGrid(true, new Position(col, row), size);
                if (!isNewShipPersecShips(shipOnGridResponse)) {
                    this.shipsOnGrid.add(shipOnGridResponse);
                    response.add(shipOnGridResponse);
                }
            }
        }
        return response;
    }

    private boolean isNewShipPersecShips (ShipOnGrid shipOnGrid) {

        int firstOffest = shipOnGrid.getOffset();
        int firstX = shipOnGrid.getFirstPosition().getX();
        int firstY = shipOnGrid.getFirstPosition().getY();

        for (ShipOnGrid shipAlreadyOnGrid : this.shipsOnGrid) {

            int secondOffest = shipAlreadyOnGrid.getOffset();
            int secondX = shipAlreadyOnGrid.getFirstPosition().getX();
            int secondY = shipAlreadyOnGrid.getFirstPosition().getY();

            if (shipOnGrid.isVertical()) {
                if (shipAlreadyOnGrid.isVertical()) {
                    if (secondX == firstX) {
                        if (secondY >= firstY && (firstY + firstOffest-1) >= secondY) {
                            return true;
                        } else if (firstY >= secondY && (secondY + secondOffest-1) >= firstY) {
                            return true;
                        } else if (secondY == firstY ) {
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        if (this.shipsOnGrid.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private Ship getNewRandomShip(ShipType shipType) {
        List<ShipOnGrid> shipsOnGrid = genShipPositions(shipType.getSize());
        int random_ship = 0 + (int) (Math.random() * shipsOnGrid.size());
        ShipOnGrid randShipOnGrid = shipsOnGrid.get(random_ship);
        Ship ship = new Ship();
        ship.setShipType(shipType);
        for (int i = 0; i < randShipOnGrid.getOffset(); i++) {
            if (randShipOnGrid.isVertical()) {
                Log.debug("ship: pos_x: " + randShipOnGrid.getFirstPosition().getX()+", pos_y: "+(randShipOnGrid.getFirstPosition().getY()+i));
                ship.getPositions().add(new Position(randShipOnGrid.getFirstPosition().getX(), randShipOnGrid.getFirstPosition().getY()+i));
            }
        }
        return ship;
    }
}
