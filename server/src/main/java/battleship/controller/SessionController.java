package battleship.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import battleship.Log;
import battleship.common.GameDTO;
import battleship.common.GridData;
import battleship.common.Position;

import battleship.common.Ship;
import battleship.utils.ShipGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class SessionController {

	@Autowired
	MapSessionRepository mapSessionRepository;

	@RequestMapping("/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(HttpSession session) {
		Log.error("logout");
		session.invalidate();
	}

	@GetMapping(value ="/startSession")
	@ResponseBody
	public GameDTO startSession(
			@RequestParam(value="sessionId", defaultValue="0") String sessionId
			,@RequestParam(value="x", defaultValue="0") int x
			,@RequestParam(value="y", defaultValue="0") int y){

		Log.error("sessionId: "+ sessionId);
		if (mapSessionRepository.getSession(sessionId) == null) {
			Log.error("No session found, greating new one.");
			ExpiringSession session = mapSessionRepository.createSession();

			ShipGenerator shipGenerator = new ShipGenerator();

			GridData gridData = new GridData();

			gridData.setBotScore(0);
			gridData.setUserScore(0);
			gridData.setHeight(ShipGenerator.gridHeight);
			gridData.setWidth(ShipGenerator.gridWidth);
			gridData.setEnemyShips(shipGenerator.getShips());
			session.setAttribute("data", gridData);

			mapSessionRepository.save(session);

			GameDTO gameDTO = new GameDTO();
			gameDTO.setSessionId(session.getId());
			return gameDTO;
		} else {
			Log.error("Session found.");
			ExpiringSession session = mapSessionRepository.getSession(sessionId);
			GridData gridData = session.getAttribute("data");
			return getGameDTOFromGridData(sessionId, gridData, x, y);
		}
	}

	private GameDTO getGameDTOFromGridData(String sessionId, GridData gridData, int x, int y) {
		GameDTO gameDTO = new GameDTO();
		gameDTO.setHitStatus(isHit(gridData.getEnemyShips(), x, y));
		gameDTO.setSessionId(sessionId);
		return gameDTO;
	}

	private boolean isHit(List<Ship> ships, int x, int y) {
		for (Ship ship : ships) {
			for (Position position : ship.getPositions()) {
				if (position.getX() == x && position.getY() == y) {
					Log.debug("ship: "+ ship.getShipType().getName());
					return true;
				}
			}
		}
		return false;
	}

}
