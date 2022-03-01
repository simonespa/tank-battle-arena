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

package tankBattle.utilities.xmlParser;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Questa classe &egrave; l'handler del parser XML. I metodi sovrascritti
 * rilanciano l'eccezione al chiamante, in modo che possa essere gestita al
 * livello superiore, ovvero a quello del parser.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class Handler implements ErrorHandler {

	@Override
	public void error(SAXParseException exception) throws SAXException {
		throw new SAXException(exception);
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		throw new SAXException(exception);
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		throw new SAXException(exception);
	}

}
