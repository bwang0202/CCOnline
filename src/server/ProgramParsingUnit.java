package server;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import parser.AGramSym_ToString;
import parser.IGrammarSymbol;
import client.model.exec.ExecValue;
import client.model.exec.ExecVisitor;
import client.model.exec.IOperand;

public class ProgramParsingUnit {
	/**
	 * Executes the parsed program (no initialization code)
	 */
	private final ExecValue programExec;
	/**
	 * The parsing unit for this program's language.
	 */
	private final LanguageParsingUnit unit;
	/**
	 * 
	 * @param unit unit parsing unit for this program's language.
	 * @param programReader program reader.
	 * 
	 */
	public ProgramParsingUnit(LanguageParsingUnit unit, Reader programReader) {
		this.unit = unit;
		this.programExec = parseProgram(programReader);
	}
	

	/**
	 * execute the program using inputOpMap as op maps, put execution result in string builder sb.
	 * @param initExecutor init executior.
	 * @param sb The place to put execution result.
	 */
	public void executeProgram(ExecValue initExecutor, StringBuilder sb) {
		Map<String, IOperand> symbolTable = new HashMap<>();
		initExecutor.run(symbolTable, sb::append);
		programExec.run(symbolTable, sb::append);
		System.out.println("Both ran");
	}
	

	/**
	 * 
	 * @param programReader program reader
	 * @return Return the executable value
	 */
	public ExecValue parseProgram(Reader programReader) {
		IGrammarSymbol parseResult = unit.parseProgram(programReader);
		System.err.println("Result = \n" + parseResult.typeExecute(AGramSym_ToString.Singleton));
		return parseResult.typeExecute(new ExecVisitor(unit.getOpMap()));
	
	}

	/**
	 * getter for language parsing unit.
	 * @return the language parsing unit.
	 */
	public LanguageParsingUnit getLangUnit() {
		return unit;
	}
}
