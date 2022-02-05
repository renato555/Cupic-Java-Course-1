package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;

/**
 * Turns a given document into a tree like structure.
 * @author renat
 */
public class SmartScriptParser {
	/**
	 * Document to be processed.
	 */
	private String documentBody;
	/**
	 * Lexer for extracting tokens.
	 */
	private Lexer lexer;
	/**
	 * Root of a document tree.
	 */
	private DocumentNode documentNode;
	
	/**
	 * Default constructor.
	 * @param documentBody document to be processed
	 * @throws NullPointerException if documentBody equals null
	 */
	public SmartScriptParser( String documentBody) {
		if( documentBody == null) throw new NullPointerException( "documentBody ne moze biti null");
		this.documentBody = documentBody;
		this.lexer = new Lexer( documentBody);
		
		try {
			parse();			
		}catch( Exception e) {
			throw new SmartScriptParserException( e.getMessage());
		}
	}
	
	/**
	 * Creates tree like structure for a given document.
	 * DocumentNode is a root node.
	 * TextNode only strings outside of tags.
	 * ForLoopNode represents a for-loop tag: starts with variable name after which 2 or 3 elements(variables, constans or strings) follow.
	 * EchoNode represents a '=' tag: contains other elements.
	 * @throws SmartScriptParserException if something bad happens
	 */
	private void parse() {
		ObjectStack stack =  new ObjectStack();
		documentNode = new DocumentNode();
		stack.push( documentNode);
		
		Token token = lexer.nextToken();
		while( token.getType() != TokenType.EOF) {
			if( token.getType() == TokenType.STRING) {
				// radi se o textNodu - samo dohvacaj rijeci do taga
				String temp = "";
				while( token.getType() == TokenType.STRING) {
					temp += token.getValue() + " ";
					token = lexer.nextToken();
				}
				
				//dodaj ga roditelju na vrhu stacka
				TextNode textNode = new TextNode( temp);
				Node nodeOnTop = (Node) stack.peek();
				nodeOnTop.addChildNode( textNode);
				
			}else if( token.getType() == TokenType.CHANGE_STATE) {
				// promijeni stanje
				if( ((char) token.getValue()) == '#') {
					lexer.setState( LexerState.TEXT_MODE);
				}else {
					lexer.setState( LexerState.TAG_MODE);
				}
				token = lexer.nextToken();
				
			}else if( token.getType() == TokenType.TAG_NAME) {
				// unutar taga FOR ili "=" ili END
				String tagName = (String) token.getValue();
				if( tagName.equals( "=")) {
					ArrayIndexedCollection arr = new ArrayIndexedCollection();
					token = lexer.nextToken();
					while( token.getType() != TokenType.CHANGE_STATE) {
						arr.add( elementFromToken( token));
						token = lexer.nextToken();
					}
					
					Element[] elements = new Element[ arr.size()];
					for( int i = 0; i < elements.length; i++) {
						elements[i] = (Element) arr.get( i);
					}
					
					// dodaj EchoNode roditelju na vrhu stacka
					EchoNode echoNode = new EchoNode( elements);
					Node nodeOnTop = (Node) stack.peek();
					nodeOnTop.addChildNode( echoNode);
					
				}else if( tagName.equals( "FOR")) {
					token = lexer.nextToken();
					
					// element variable pa 2 ili 3 elementa
					if( token.getType() != TokenType.VARIABLE) throw new SmartScriptParserException( "nema varijable u for tagu");
					ElementVariable elementVariable = (ElementVariable) elementFromToken( token);
					
					token = lexer.nextToken();
					if( token.getType() == TokenType.CHANGE_STATE || token.getType() == TokenType.EOF) {
						throw new SmartScriptParserException( "pre malo argumenata u foru");
					}
					if( token.getType() != TokenType.VARIABLE && token.getType() != TokenType.CONSTANT_DOUBLE && 
						token.getType() != TokenType.CONSTANT_INTEGER && token.getType() != TokenType.STRING) {
						throw new SmartScriptParserException( "krivi argumenti u foru");
					}
					Element first = elementFromToken( token);
					
					token = lexer.nextToken();
					if( token.getType() == TokenType.CHANGE_STATE || token.getType() == TokenType.EOF) {
						throw new SmartScriptParserException( "pre malo argumenata u foru");
					}
					if( token.getType() != TokenType.VARIABLE && token.getType() != TokenType.CONSTANT_DOUBLE && 
						token.getType() != TokenType.CONSTANT_INTEGER && token.getType() != TokenType.STRING) {
							throw new SmartScriptParserException( "krivi argumenti u foru");
					}
					Element second = elementFromToken( token);
					
					token = lexer.nextToken();
					ForLoopNode forLoopNode;
					// dodaj jos step
					if( token.getType() != TokenType.CHANGE_STATE) {
						if( token.getType() != TokenType.VARIABLE && token.getType() != TokenType.CONSTANT_DOUBLE && 
							token.getType() != TokenType.CONSTANT_INTEGER && token.getType() != TokenType.STRING) {
								throw new SmartScriptParserException( "krivi argumenti u foru");
						}
						Element third = elementFromToken( token);
						forLoopNode = new ForLoopNode( elementVariable, first, second, third);
						token = lexer.nextToken();
					}else {
						forLoopNode = new ForLoopNode( elementVariable, first, second, null);
					}
					
					if( token.getType() != TokenType.CHANGE_STATE) throw new SmartScriptParserException( "previse argumenata u foru");
					// dodaj forLoopNode nodu na vrh stoga i pushaj ga na vrh
					Node nodeOnTop = (Node) stack.peek();
					nodeOnTop.addChildNode( forLoopNode);
					stack.push( forLoopNode);
				}else {
					// ovdje jedino moze doc END
					stack.pop();
					token = lexer.nextToken();
				}
			}
		}
		if( stack.size() != 1) {
			throw new SmartScriptParserException( "krivi input");
		}
	}
	
	/**
	 * Turns a token into a corresponding Element.
	 * @param token for which an element will be created
	 * @return Corresponding element that contains token value
	 * @throws NullPointerException if token equals null
	 */
	private Element elementFromToken( Token token) {
		if( token == null) throw new NullPointerException( "token ne moze biti null");
		TokenType tip = token.getType();
		
		if( tip == TokenType.VARIABLE) {
			return new ElementVariable( (String) token.getValue());
		}else if( tip == TokenType.STRING) {
			return new ElementString( (String) token.getValue());
		}else if( tip == TokenType.FUNCTION) {
			return new ElementFunction( (String) token.getValue());
		}else if( tip == TokenType.OPERATOR) {
			return new ElementOperator( (String) token.getValue());
		}else if( tip == TokenType.CONSTANT_INTEGER) {
			return new ElementConstantInteger( (int) token.getValue());
		}else if( tip == TokenType.CONSTANT_DOUBLE) {
			return new ElementConstantDouble( (double) token.getValue());
		}else {
			throw new SmartScriptParserException( "nepoznata pretvorba iz tokena u element");
		}
	}
	
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
