package debug;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Decorator for a PrintStream that prepends to any output a reference to the line that called for the output.
 * All methods forward to the decorated stream.
 * @author Derek
 *
 */
public class Logger extends PrintStream {

	/**
	 * If this is false, Logger is a completely transparent decorator
	 */
	private static final boolean LINE = true;
	
	/**
	 * Set to true after System.out has been decorated once, to prevent repeates
	 */
	private static final AtomicBoolean SET_OUT = new AtomicBoolean(false);
	
	/**
	 * Decorates System.out with a logger, if it hasn't been decorated yet
	 */
	public static void setOut() {
		if (SET_OUT.compareAndSet(false, true)) {
			System.setOut(new Logger(System.out));
		}
	}
	
	/**
	 * The decorated stream
	 */
	private PrintStream out;

	/**
	 * Creates a logger that logs on the given stream.
	 * @param out
	 */
	public Logger(PrintStream out) {
		super(out);
		this.out = out;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return out.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public void write(byte[] b) throws IOException {
		out.write(b);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		return out.equals(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return out.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public void flush() {
		out.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	public void close() {
		out.close();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean checkError() {
		return out.checkError();
	}

	/**
	 * {@inheritDoc}
	 */
	public void write(int b) {
		out.write(b);
	}

	/**
	 * {@inheritDoc}
	 */
	public void write(byte[] buf, int off, int len) {
		out.write(buf, off, len);
	}

	/**
	 * {@inheritDoc}
	 */
	public void print(boolean b) {
		out.print(info());
		out.print(b);
	}

	/**
	 * {@inheritDoc}
	 */
	public void print(char c) {
		out.print(info());
		out.print(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public void print(int i) {
		out.print(info());
		out.print(i);
	}

	/**
	 * {@inheritDoc}
	 */
	public void print(long l) {
		out.print(info());
		out.print(l);
	}

	/**
	 * {@inheritDoc}
	 */
	public void print(float f) {
		out.print(info());
		out.print(f);
	}

	/**
	 * {@inheritDoc}
	 */
	public void print(double d) {
		out.print(info());
		out.print(d);
	}

	/**
	 * {@inheritDoc}
	 */
	public void print(char[] s) {
		out.print(info());
		out.print(s);
	}

	/**
	 * {@inheritDoc}
	 */
	public void print(String s) {
		out.print(info());
		out.print(s);
	}

	/**
	 * {@inheritDoc}
	 */
	public void print(Object obj) {
		out.print(info());
		out.print(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	public void println() {
		out.print(info());
		out.println();
	}

	/**
	 * {@inheritDoc}
	 */
	public void println(boolean x) {
		out.print(info());
		out.println(x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void println(char x) {
		out.print(info());
		out.println(x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void println(int x) {
		out.print(info());
		out.println(x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void println(long x) {
		out.print(info());
		out.println(x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void println(float x) {
		out.print(info());
		out.println(x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void println(double x) {
		out.print(info());
		out.println(x);
	}

	/**
	 * {@inheritDoc}
	 */
	public void println(char[] x) {
		out.print(info());
		out.println(x);
	}

	/**
	 * {@inheritDoc}
	 */
	public PrintStream printf(String format, Object... args) {
		out.print(info());
		return out.printf(format, args);
	}

	/**
	 * {@inheritDoc}
	 */
	public PrintStream printf(Locale l, String format, Object... args) {
		out.print(info());
		return out.printf(l, format, args);
	}

	/**
	 * {@inheritDoc}
	 */
	public PrintStream format(String format, Object... args) {
		out.print(info());
		return out.format(format, args);
	}

	/**
	 * {@inheritDoc}
	 */
	public PrintStream format(Locale l, String format, Object... args) {
		out.print(info());
		return out.format(l, format, args);
	}

	/**
	 * {@inheritDoc}
	 */
	public PrintStream append(CharSequence csq) {
		out.print(info());
		return out.append(csq);
	}

	/**
	 * {@inheritDoc}
	 */
	public PrintStream append(CharSequence csq, int start, int end) {
		out.print(info());
		return out.append(csq, start, end);
	}

	/**
	 * {@inheritDoc}
	 */
	public PrintStream append(char c) {
		out.print(info());
		return out.append(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public void println(String output) {
		out.print(info());
		out.println(output);
	}

	/**
	 * {@inheritDoc}
	 */
	private String info() {
		if (!LINE)
			return "";
		// go back up the stack trace until a non-Logger is found
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		int k = 1;
		while (trace[k].getClassName().equals(getClass().getCanonicalName()) || trace[k].getClassName().equals(PrintStream.class.getCanonicalName())) {
			k++;
		}
		StackTraceElement e = Thread.currentThread().getStackTrace()[k];
	    String fullClassName = e.getClassName();
	    String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
	    // remove inner classes, only outer class is needed
	    int dollar = className.indexOf('$');
	    if (dollar >= 0)
	    	className = className.substring(0, dollar);
	    int lineNumber = e.getLineNumber();
	    return "(" + className + ".java:" + lineNumber+")";
	}
	
}
