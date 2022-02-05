package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.components.CalcBinaryOperatorInv;
import hr.fer.zemris.java.gui.calc.components.CalcBinaryOperator;
import hr.fer.zemris.java.gui.calc.components.CalcDigitButton;
import hr.fer.zemris.java.gui.calc.components.CalcFunction;
import hr.fer.zemris.java.gui.calc.components.InvertName;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Calculator frame.
 * @author renat
 */
public class Calculator extends JFrame {

	/**
	 * Constructor.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(600, 300);
		initGUI();
	}

	/**
	 * Initialises GUI components.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));

		// initialise model
		CalcModel calcModel = new CalcModelImpl();

		// Jlabel
		JLabel numberString = new JLabel(calcModel.toString());
		numberString.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		numberString.setOpaque(true);
		numberString.setBackground(Color.YELLOW);
		numberString.setHorizontalAlignment(SwingConstants.RIGHT);
		numberString.setFont(numberString.getFont().deriveFont(30f));
		CalcValueListener updateNumberString = (model) -> {
			numberString.setText(model.toString());
		};
		calcModel.addCalcValueListener(updateNumberString);
		cp.add(numberString, "1,1");

		// digit buttons
		ActionListener addDigit = a -> {
			CalcDigitButton b = (CalcDigitButton) a.getSource();
			calcModel.insertDigit(b.getDigit());
		};
		cp.add(new CalcDigitButton(0, addDigit), "5,3");
		cp.add(new CalcDigitButton(1, addDigit), "4,3");
		cp.add(new CalcDigitButton(2, addDigit), "4,4");
		cp.add(new CalcDigitButton(3, addDigit), "4,5");
		cp.add(new CalcDigitButton(4, addDigit), "3,3");
		cp.add(new CalcDigitButton(5, addDigit), "3,4");
		cp.add(new CalcDigitButton(6, addDigit), "3,5");
		cp.add(new CalcDigitButton(7, addDigit), "2,3");
		cp.add(new CalcDigitButton(8, addDigit), "2,4");
		cp.add(new CalcDigitButton(9, addDigit), "2,5");

		// insert decimal dot button
		ActionListener addDecimalPoint = a -> {
			calcModel.insertDecimalPoint();
		};
		JButton decimalButton = new JButton(".");
		decimalButton.addActionListener(addDecimalPoint);
		cp.add(decimalButton, "5,5");

		// change sign
		ActionListener swapSign = a -> {
			calcModel.swapSign();
		};
		JButton signButton = new JButton("+/-");
		signButton.addActionListener(swapSign);
		cp.add(signButton, "5,4");

		// invert checkbox
		JCheckBox invButton = new JCheckBox("Inv");
		cp.add(invButton, "5,7");

		// binary operators
		ActionListener binaryAction = a -> {
			if (calcModel.hasFrozenValue())
				throw new CalculatorInputException("mora se upisat neki broj");
			CalcBinaryOperator operator = (CalcBinaryOperator) a.getSource();

			if (!calcModel.isActiveOperandSet()) {
				calcModel.setActiveOperand(calcModel.getValue());
			} else {
				double result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
						calcModel.getValue());
				calcModel.setActiveOperand(result);
			}
			calcModel.setPendingBinaryOperation(operator.getOperation());
			calcModel.clear();
		};
		cp.add(new CalcBinaryOperator("/", (x, y) -> x / y, binaryAction), "2,6");
		cp.add(new CalcBinaryOperator("*", (x, y) -> x * y, binaryAction), "3,6");
		cp.add(new CalcBinaryOperator("-", (x, y) -> x - y, binaryAction), "4,6");
		cp.add(new CalcBinaryOperator("+", (x, y) -> x + y, binaryAction), "5,6");

		CalcBinaryOperatorInv power = new CalcBinaryOperatorInv("x^n", Math::pow, "x^(1/n)",
				(x, n) -> Math.pow(x, 1 / n), invButton, binaryAction);
		cp.add(power, "5,1");

		// functions
		ActionListener functionAction = a -> {
			if (calcModel.hasFrozenValue())
				throw new CalculatorInputException("mora se upisat neki broj");

			CalcFunction f = (CalcFunction) a.getSource();
			double result = f.getFunction(invButton.isSelected()).apply(calcModel.getValue());
			calcModel.setValue(result);
		};
		List<InvertName> invertNameListeners = new LinkedList<>();
		CalcFunction sin = new CalcFunction("sin", "arcsin", Math::sin, Math::asin, functionAction);
		cp.add(sin, "2,2");
		invertNameListeners.add(sin);
		CalcFunction cos = new CalcFunction("cos", "arccos", Math::cos, Math::acos, functionAction);
		cp.add(cos, "3,2");
		invertNameListeners.add(cos);
		CalcFunction tan = new CalcFunction("tan", "arctan", Math::tan, Math::atan, functionAction);
		cp.add(tan, "4,2");
		invertNameListeners.add(tan);
		CalcFunction ctg = new CalcFunction("ctg", "arcctg", x -> 1 / Math.tan(x), x -> Math.PI / 2 - Math.atan(x),
				functionAction);
		cp.add(ctg, "5,2");
		invertNameListeners.add(ctg);
		CalcFunction reciprocal = new CalcFunction("1/x", "1/x", x -> 1 / x, x -> 1 / x, functionAction);
		cp.add(reciprocal, "2,1");
		invertNameListeners.add(reciprocal);
		CalcFunction log = new CalcFunction("log", "10^x", Math::log10, x -> Math.pow(10, x), functionAction);
		cp.add(log, "3,1");
		invertNameListeners.add(log);
		CalcFunction ln = new CalcFunction("ln", "e^x", Math::log, Math::exp, functionAction);
		cp.add(ln, "4,1");
		invertNameListeners.add(ln);

		invertNameListeners.add(power);

		ActionListener invChangeName = a -> {
			JCheckBox inv = (JCheckBox) a.getSource();
			invertNameListeners.forEach(f -> f.invertName(inv.isSelected()));
		};
		invButton.addActionListener(invChangeName);

		// clear button
		ActionListener clrAction = a -> {
			calcModel.clear();
		};
		JButton clrButton = new JButton("clr");
		clrButton.addActionListener(clrAction);
		cp.add(clrButton, "1,7");

		// res button
		ActionListener resAction = a -> {
			calcModel.clearAll();
		};
		JButton resButton = new JButton("res");
		resButton.addActionListener(resAction);
		cp.add(resButton, "2,7");

		// equals button
		JButton equalsButton = new JButton("=");
		ActionListener equalsListener = a -> {
			if (!calcModel.isActiveOperandSet()) {
				calcModel.setValue(calcModel.getValue());
			} else {
				double result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
						calcModel.getValue());
				calcModel.setValue(result);
				calcModel.clearActiveOperand();
				calcModel.setPendingBinaryOperation(null);
			}
		};
		equalsButton.addActionListener(equalsListener);
		cp.add(equalsButton, "1,6");

		Stack<Double> stack = new Stack<>();
		// push button
		JButton pushButton = new JButton("push");
		ActionListener pushListener = a -> {
			stack.push(calcModel.getValue());
		};
		pushButton.addActionListener(pushListener);
		cp.add(pushButton, "3,7");

		// pop button
		JButton popButton = new JButton("pop");
		ActionListener popListener = a -> {
			if (stack.isEmpty())
				throw new CalculatorInputException("stog je prazan");
			calcModel.setValue(stack.pop());
		};
		popButton.addActionListener(popListener);
		cp.add(popButton, "4,7");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
