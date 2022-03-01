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

public class Parser {
	
	private ArrayList<Map> maps;
	private File fileXML;
	
	public Parser(String fileName) {
		maps = new ArrayList<Map>();
		fileXML = new File(fileName);
	}
	
	public Document parse() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(new Handler());
		return builder.parse(fileXML);
	}
	
	public void createTree(Document document) {
		// restituisce il nodo radice
		Element element = document.getDocumentElement();
		// acquisisce i figli del nodo radice (gli elementi "map")
		NodeList nodeList = element.getChildNodes();
		for( int i = 0; i < nodeList.getLength(); i++ ) {
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
	
	public Map getMap(int i) {
		return maps.get(i);
	}
	
	public int getMapSize() {
		return maps.size();
	}
	
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
		if( srcImage != null ) {
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
	
	private void setWall(Node node, Map map) {
		NodeList wals = node.getChildNodes();
		for( int i = 0; i < wals.getLength(); i++ ) {
			NamedNodeMap attribs = wals.item(i).getAttributes();
			Node x = attribs.getNamedItem("x");
			Node y = attribs.getNamedItem("y");
			map.addWall(x.getNodeValue(), y.getNodeValue());
		}
	}

	public static void main(String[] args) {
		Parser parser = new Parser("files/map.xml");
		Document document = null;
		try {
			// parserizza il documento e lo restituisce
			document = parser.parse();
			/*
			 *  passa il documento al metodo che crea
			 *  una struttura dati apposita
			 */
			parser.createTree(document);
			// genera un numero casuale
			Random r = new Random(System.nanoTime());
			int index = r.nextInt(parser.getMapSize());
			// acquisisce una mappa casuale
			Map map = parser.getMap(index);
			// stampa i valori della mappa
			System.out.println(map.getID());
			System.out.println(map.getSrcImage());
			for( int i = 0; i < map.getWallSize(); i++ ) {
				System.out.println(map.getWall(i).getX());
				System.out.println(map.getWall(i).getY());
			}
		} catch (ParserConfigurationException e) {
			System.err.println(e.getMessage());
		} catch (SAXException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
}
