function initGrid(columns, rows) {
    for (iR = 0; iR < rows; iR++) {
        row = new Array();
        for (iC = 0; iC < columns; iC++) {
            cell = Object.create(Cell).init();
            position = Object.create(Position).init();
            position.x = iR;
            position.y = iC;
            cell.position = position;
            row.push(cell);
        }
        this.grid.push(row)
    }
}

function setEnemyShips() {
    for (shipIter in this.ships) {
        ship = this.ships[shipIter];
        positions = ship.positions;
        for (positionIter in positions) {
            shipPosition = positions[positionIter];
            for (rowIter in this.grid) {
                row = this.grid[rowIter];
                console.log("row: "+row.length);
                for (cellIter in row) {
                    cell = row[cellIter];
                    if (cell.position.x == shipPosition[0] && cell.position.y == shipPosition[1]) {
                        cell.ship = ship;
                    }
                }
            }
        }
    }
}

function setShipTypes(data) {
    for (shipType in data.shipTypes) {
        ship = Object.create(Ship).init();
        ship.type = shipType;
        ship.isAlive = true;
        ship.size = data.shipTypes[shipType].size;
        for(var i = 0; i < ship.size; i++) {
            life = Object.create(Life).init();
            life.active = true;
            ship.lives.push(life);
        }
        for (layout in data.layout) {
            if (data.layout[layout].ship === ship.type) {
                ship.positions = data.layout[layout].positions;
            }
        }
        this.ships.push(ship);
    }
}

function reduseShipLife(ship) {
    var lifeCounter = 0;
    for (lifeIter in ship.lives) {
        life = ship.lives[lifeIter];
        lifeCounter++;
        if (life.active == true) {
            life.active = false;
            break;
        }
    }
    if (lifeCounter == ship.lives.length) {
        ship.isAlive = false;
    }
    return ship.isAlive;
}

data = {
    "shipTypes": {
        "carrier": { "size": 5, "count": 1 },
        "battleship": { "size": 4, "count": 1 },
        "cruiser": { "size": 3, "count": 1 },
        "submarine": { "size": 3, "count": 1 },
        "destroyer": { "size": 2, "count": 1 },
    },
    "layout": [
        { "ship": "carrier", "positions": [[2,9], [3,9], [4,9], [5,9], [6,9]] },
        { "ship": "battleship", "positions": [[5,2], [5,3], [5,4], [5,5]] },
        { "ship": "cruiser", "positions": [[8,1], [8,2], [8,3]] },
        { "ship": "submarine", "positions": [[3,0], [3,1], [3,2]] },
        { "ship": "destroyer", "positions": [[0,0], [1,0]] }
    ]
}

Position = {
    init: function () {
        this.x = null;
        this.y = null;
        return (this);
    },
    x: Number,
    y: Number
}

Ship = {
    init: function() {
        this.type = null;
        this.positions = [];
        this.size = 0;
        this.isAlive = false;
        this.lives = [];
        return (this);
    },
    type: null,
    positions: [],
    size: Number,
    isAlive: Boolean,
    lives: []
}

Life = {
    init: function(){
        this.active = false;
        return (this);
    },
    active: Boolean
}

Cell = {
    init: function() {
        this.position = null;
        this.ship = null;
        this.isClicked = false;
        this.background = "";
        return (this);
    },
    position: Position,
    ship: Ship,
    isClicked: Boolean,
    background: String
}
 
var AppComponent = ng.core.Component({
    selector: 'my-app',
    templateUrl: 'app/app.component.html'
    })
  .Class({
    constructor: function() {
        this.contentLoaded = false;
        this.gameover = false;
        this.userScore = 0;
        this.grid = new Array();
        this.ships = new Array();
        initGrid.call(this, 10, 10);
        setShipTypes.call(this, data);
        setEnemyShips.call(this);
        this.contentLoaded = true;
    }
});

AppComponent.prototype.checkClikedCell = function (cell) {
    console.log("checkClikedCell(): " + cell.ship);
    if (cell.ship == null) {
        cell.background = 'url(assets/miss.png)';
    } else {
        if (cell.ship.isAlive == true) {
            cell.background = 'url(assets/hit.png)';
            if (!reduseShipLife(cell.ship)) {
                this.userScore++;
                sunkShips=0;
                for(shipIter in this.ships){
                    if (!this.ships[shipIter].isAlive) {
                        sunkShips++;
                    }
                }
                if (sunkShips == this.ships.length) {
                    this.gameover = true;
                }

                console.log("checkClikedCell(): " + cell.ship.type + " was sunk!");
            } else {
                console.log("checkClikedCell(): " + cell.ship.type + " was damaged!");
            }
        }
    }
}