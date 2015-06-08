package client.model.exec;

import static hw03Common.defs.WellKnownOperator.CONCATENATION;
import hw03Common.defs.WellKnownOperator;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;

import parser.AGramSymVisitor;
import parser.IGrammarSymbol;
import parser.SequenceSymbol;
import parser.TerminalSymbol;


/**
 * A visitor that is executed by a parsed BNF tree to execute the program that the tree defines
 * @author Derek
 *
 */
public class ExecVisitor extends AGramSymVisitor<ExecValue, Void>{

	/**
	 * A visitor specifically for terminal symbols to determine what to do with them
	 */
	private AGramSymVisitor<ExecValue, Void> termHelper;
	
	/**
	 * Creates the visitor.
	 * @param opMap map of operators to their names as defined by the language
	 *
	 */
	public ExecVisitor(Map<String, WellKnownOperator> opMap) {
		// by default, throw an error, as it indicates something has gone terribly wrong
		super((index, host, params) -> {
			throw new RuntimeException("Bad index: " + index);
		});
		
		// this helper visitor visits terminal symbols, reacting according to their lexemes
		// the default is to do nothing, as any unrecognized symbol is assumed to only exist for the purposes
		// of structuring the BNF
		termHelper = new AGramSymVisitor<ExecValue, Void>((i, h, p) -> new ExecValue() ) {
			
			/**
			 * Helper function for easily setting operations on doubles
			 * @param operator the operator to define
			 * @param operation the operation on doubles corresponding to the operator
			 */
			private void setMathCmd(Map.Entry<String, WellKnownOperator> entry, DoubleBinaryOperator operation) {
				setCmd(entry.getKey(), (index, host, params) -> {
					return new ExecValue(new BiOperator<>(entry.getValue(), NumValue.class, (a,b) -> new NumValue(operation.applyAsDouble(a.getNum(), b.getNum()))));
				});
			}
			
			// init block
			{
				for (Map.Entry<String, WellKnownOperator> entry : opMap.entrySet()) {
					switch (entry.getValue()) {
					case ASSIGN:
						setCmd(entry.getKey(), (index, host, params) -> {
							// the assignment operator, which puts a value in the symbol table
							return new ExecValue(new IOperator() {
								@Override
								public ExecValue execute(
										List<IOperand> operands) {
									if (operands.size() != 2) {
										return new ExecValue(this, operands);
									}
									return new ExecValue(new IOperand() {
										@Override
										public IOperand run(
												Map<String, IOperand> symbolTable, Consumer<String> input) {

											if (!(operands.get(0) instanceof SymbolValue) ) {
												throw new RuntimeException("Cannot assign anything to " + operands.get(0).getClass() + " " + operands.get(0));
											}
											symbolTable.put(operands.get(0).toString(), operands.get(1).run(symbolTable, input));
											return operands.get(1); // languages are allowed to reuse results of assignment
										}
										public String toString() {
											return "ASSIGN " + operands.get(1) + " to " + operands.get(0);
										}
										
									});
								}
							});
						});
						break;
					case PRINT:
						setCmd(entry.getKey(), (index, host, params) -> {
							// print a value to the established printer
							return new ExecValue(new IOperator() {
								@Override
								public ExecValue execute(List<IOperand> operands) {
									if (operands.size() != 1) {
										return new ExecValue(this, operands);
									}
									return new ExecValue(new IOperand() {
										public IOperand run(Map<String, IOperand> symbolTable, Consumer<String> output) {
											IOperand result = operands.get(0).run(symbolTable, output);
											output.accept(result.toString() + "\n");
											return result;
										}
										public String toString() {
											return "PRINT " + operands.get(0);
										}
									});
								}
							});
						});
						break;
					case SUMMATION:
						setMathCmd(entry, Double::sum);
						break;
					case SUBTRACTION:
						setMathCmd(entry, (a,b) -> a - b);
						break;
					case MULTIPLICATION:
						setMathCmd(entry, (a,b) -> a * b);
						break;
					case DIVISION:
						setMathCmd(entry, (a,b) -> a / b);
						break;
					case EXPONENTIATION:
						setMathCmd(entry, Math::pow);
						break;
					case MAX:
						setMathCmd(entry, Double::max);
						break;
					case MIN:
						setMathCmd(entry, Double::min);
						break;
					case MODULO:
						setMathCmd(entry, (a,b) -> a % b);
						break;
					case CONCATENATION:
						setCmd(entry.getKey(), (index, host, params) -> {
							// concatenation automatically converts values to strings
							return new ExecValue(new BiOperator<>(CONCATENATION, IOperand.class, (a,b) -> new StringValue(a.toString() + b.toString())));
						});
					}
				}
				
				// Ids are symbol values, which use the symbol table to gain and use values
				setCmd("Id", (index, host, params) -> {
					return new ExecValue(new SymbolValue((((TerminalSymbol) host).getLexeme())));
				});
				
				// Nums are number values
				setCmd("Num", (index, host, params) -> {
					return new ExecValue(new NumValue(Double.parseDouble(host.toString())));
				});
				
				// Quoted strings are string values
				setCmd("QuotedStringToken", (index, host, params) -> {
					return new ExecValue(new StringValue(host.toString()));
				});
			}
		};
		
		// execute a sequence by combining the results of executing its parts in order
		setCmd("SequenceSymbol", (index, host, params) -> {
            SequenceSymbol seqHost = (SequenceSymbol) host;
            IGrammarSymbol first = seqHost.getSymbol1();
            IGrammarSymbol second = seqHost.getSymbol2();
            return new ExecValue(first.typeExecute(this), second.typeExecute(this)).execute();
		});
		
		// the empty symbol has no value, and an empty exec value is the combination identity
		setCmd("MTSymbol", (index, host, params) -> {
			return new ExecValue();
		});
		
		// terminals use another level of parsing depending on their lexemes
		setCmd("TerminalSymbol", (index, host, params) -> {
			return host.execute(termHelper);
		});
	}
	
}
