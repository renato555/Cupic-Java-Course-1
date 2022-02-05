package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * A node that represents a sinle for-loop construct.
 * @author renat
 */
public class ForLoopNode extends Node {
	/**
	 * For loop variable.
	 */
	private ElementVariable variable;
	/**
	 * Initial value.
	 */
	private Element startExpression;
	/**
	 * End value.
	 */
	private Element endExpression;
	/**
	 * Step value.
	 */
	private Element stepExpression;
	
	/**
	 * Default constructor.
	 * @throws NullPointerException if variable or startExpression or endExpression equals null 
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		if( variable == null) throw new NullPointerException( "variable can not be null");
		if( startExpression == null) throw new NullPointerException( "startExpression can not be null");
		if( endExpression == null) throw new NullPointerException( "endExpression can not be null");
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	public ElementVariable getVariable() {
		return variable;
	}

	public Element getStartExpression() {
		return startExpression;
	}

	public Element getEndExpression() {
		return endExpression;
	}

	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		String treci = stepExpression == null ? "" : stepExpression.toString();
		String pocetak = "{$FOR " + variable + " "+ startExpression + " " + endExpression + " " + treci + " $}";
		String middle = super.toString();
		String kraj = "{$END$}";
		
		return "\n"+ pocetak + "\n" + middle + "\n" + kraj;
	}
	
	
}
