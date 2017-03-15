package battleship.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {
    private String sessionId;
    private String shipType;
    private boolean hitStatus;
    private String gameStatus;
    private Position clickedPosition;
    private boolean turn;
}
