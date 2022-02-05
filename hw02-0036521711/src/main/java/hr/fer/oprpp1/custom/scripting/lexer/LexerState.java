package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enum for lexer states.
 * TEXT_MODE - STRING, CHANGE_STATE
 * TAG_MODE - VARIABLE, CONSTANT_INTEGER, CONSTANT_DOUBLE, STRING, FUNCTION, OPERATOR, TAG_NAME, CHANGE_STATE
 * @author renat
 */
public enum LexerState {
	TEXT_MODE, TAG_MODE
}
