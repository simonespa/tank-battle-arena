/*
 * Copyright © 2008 Simone Spaccarotella
 * 
 * This file is part of "Tank Battle Arena" game.
 * 
 * Tank Battle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Tank Battle Arena is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Tank Battle.  If not, see <http://www.gnu.org/licenses/>. 
 * 
 */

package tankBattle.net;

/**
 * Quest'interfaccia fornisce le stringhe appartenenti al protocollo di
 * comunicazione
 * 
 * @author Simone Spaccarotella
 * 
 */
public interface ClientServerCapable {

	/** Who is the server, answer me on port <port> */
	String CLIENT_REQUEST_WHO_IS_SERVER = "wHoIsThEsErVeR-AnSwErMeOnPoRt:";
	/**  */
	String SERVER_RESPONSE_AM_I = "AmI";
	/**  */
	String CLIENT_REQUEST_CAN_I_PLAY = "cAnIpLaY";
	/**  */
	String SERVER_RESPONSE_YES_OF_COURSE = "YeSoFcOuRsE-oNpOrT:";

	/* ---------- Sono i comandi che servono a muovere i componenti ---------- */
	String MESSAGE = "#message";
	String CREATE = "#create";
	String DESTROY = "#destroy";
	String CLOSE_GAME = "#closeGame";
	String MOVE = "#move";
	String TURN_BODY = "#turnBody";
	String TURN_TURRET = "#turnTurret";
	String SHOOT = "#shoot";
	String LAY_MINE = "#layMine";
	String SET_VISIBLE = "#setVisible";
	String SET_ARMOUR = "#setArmour";
	String INCREASE_ENERGY = "#increaseEnergy";
	String DECREASE_ENERGY = "#decreaseEnergy";
	String TRUE = "true";
	String FALSE = "false";
	/* ----------------------------------------------------------------------- */
}
