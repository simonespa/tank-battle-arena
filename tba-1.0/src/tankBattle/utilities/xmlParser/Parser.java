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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * &Egrave; il parser che verifica se il file XML che contenente le mappe di
 * gioco, è VALIDO e BEN FORMATO rispetto alla relativa DTD.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class Parser {

	/** una lista di mappe */
	private ArrayList<Map> maps;
	/** il descrittore del file XML */
	private File fileXML;

	/**
	 * Crea un parser XML, ricevendo in input il nome del file.
	 * 
	 * @param fileName
	 *            il nome del file XML.
	 */
	public Parser(String fileName) {
		maps = new ArrayList<Map>();
		fileXML = new File(fileName);
	}

	/**
	 * Parserizza e restituisce il documento parserizzato.
	 * 
	 * Questo documento è una struttura dati ad albero, che contiene tutti gli
	 * elementi del file parserizzato.
	 * 
	 * @return una struttura dati ad albero che rappresenta il documento
	 *         parserizzato.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document parse() throws ParserConfigurationException, SAXException,
			IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(new Handler());
		return builder.parse(fileXML);
	}

	/**
	 * Crea le strutture dati apposite per memorizzare le mappe in oggetti java.
	 * 
	 * @param document
	 *            il documento parserizzato.
	 */
	public void createTree(Document document) {
		// restituisce il nodo radice
		Element element = document.getDocumentElement();
		// acquisisce i figli del nodo radice (gli elementi "map")
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			// acquisisce il riferimento al nodo map
			Node node = nodeList.item(i);
			// restituisce un oggetto di tipo "map"
			Map map = createMap(node);
			// aggiunge i nodi "wall" al nodo map
			setWall(node, map);
			// aggiunge l'oggetto map alla lista
			maps.add(map);
		}
	}

	/**
	 * Restituisce l'i-esima mappa.
	 * 
	 * @param i
	 *            l'indice della lista.
	 * @return restituisce un oggetto "Mappa".
	 */
	public Map getMap(int i) {
		return maps.get(i);
	}

	/**
	 * Restituisce la mappa che corrisponde all'ID passato come parametro.
	 * 
	 * Se non esiste nessun oggetto con questo ID, il metodo restituisce {@code
	 * null}.
	 * 
	 * @param id
	 *            l'ID della mappa.
	 * @return restituisce un oggetto "Mappa".
	 */
	public Map getMap(String id) {
		for (Map map : maps) {
			if (map.getID().equals(id)) {
				return map;
			}
		}
		return null;
	}

	/**
	 * Restituisce una mappa in modo random.
	 * 
	 * @return un oggetto di tipo {@code tankBattle.xmlParser.Map}
	 */
	public Map getMap() {
		if (maps.size() > 0) {
			Random r = new Random(System.nanoTime());
			int index = r.nextInt(maps.size());
			return maps.get(index);
		} else {
			return null;
		}
	}

	/**
	 * Restituisce il numero di mappe presenti.
	 * 
	 * @return un intero che rappresenta il numero di mappe.
	 */
	public int getMapSize() {
		return maps.size();
	}

	/**
	 * Crea una mappa passando in input i diversi elementi di tipo {@code Node}.
	 * 
	 * @param node
	 *            il nodo (elemento) del documento.
	 * @return restituisce una mappa.
	 */
	private Map createMap(Node node) {
		// acquisice gli attributi del nodo "map"
		NamedNodeMap attribs = node.getAttributes();
		// acquisisce l'ID
		Node id = attribs.getNamedItem("id");
		// acquisisce il path dell'immagine se non è nullo
		Node srcImage = attribs.getNamedItem("src-image");
		// crea una mappa
		Map map = new Map(id.getNodeValue());
		// se il path non è nullo
		if (srcImage != null) {
			map.setSrcImage(srcImage.getNodeValue());
			// acquisisce gli altri elementi
			Node width = attribs.getNamedItem("width");
			Node height = attribs.getNamedItem("height");
			// e li setta nella mappa
			map.setWidth(width.getNodeValue());
			map.setHeight(height.getNodeValue());
		}
		return map;
	}

	/**
	 * Setta il muretto nella mappa.
	 * 
	 * @param node
	 *            l'elemento del documento.
	 * @param map
	 *            la mappa a cui aggiungere il muretto.
	 */
	private void setWall(Node node, Map map) {
		NodeList wals = node.getChildNodes();
		for (int i = 0; i < wals.getLength(); i++) {
			NamedNodeMap attribs = wals.item(i).getAttributes();
			Node x = attribs.getNamedItem("x");
			Node y = attribs.getNamedItem("y");
			map.addWall(x.getNodeValue(), y.getNodeValue());
		}
	}

}
