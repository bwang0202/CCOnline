package extvisitor.test;
import junit.framework.TestCase;
import extvisitor.AExtVisitor;
import extvisitor.AExtVisitorHost;
import extvisitor.IExtVisitor;
import extvisitor.IExtVisitorCmd;
import extvisitor.IExtVisitorHost;


public class IExtVisitor_Test extends TestCase {



	public void test_HostStr() {
		HostStr hostA = new HostStr("a");

		HostStrVis  algo = new HostStrVis(); 

		algo.setCmd("a", new IExtVisitorCmd<Integer, String, Integer, HostStr>() {
			@Override
			public Integer apply(String index, HostStr host, Integer... params) {
				return 42;
			}
		});

		algo.setCmd("q", new IExtVisitorCmd<Integer, String, Integer, HostStr>() {
			@Override
			public Integer apply(String index, HostStr host, Integer... params) {
				return 314;
			}
		});

		int i = hostA.execute(algo);
		assertEquals("hostA.execute(algo)", 42, i);

		HostStr hostQ = new HostStr("q");
		i = hostQ.execute(algo);
		assertEquals("hostQ.execute(algo)", 314, i);

		HostStr hostB = new HostStr("b");
		i = hostB.execute(algo);
		assertEquals("hostB.execute(algo)", -1, i);

		algo.setCmd("b", new IExtVisitorCmd<Integer, String, Integer, HostStr>() {
			@Override
			public Integer apply(String index, HostStr host, Integer... params) {
				return -99;
			}
		});

		i = hostB.execute(algo);
		assertEquals("hostB.execute(algo)", -99, i);
	}



	public void test_IHostStr() {
		IHostStr host1 = new HostStr1();

		HostStrVis2  algo = new HostStrVis2(); 

		algo.setCmd("HostStr1", new IExtVisitorCmd<Integer, String, Integer, IHostStr>() {
			@Override
			public Integer apply(String index, IHostStr host, Integer... params) {
				return 1234;
			}
		});

		algo.setCmd("HostStr2", new IExtVisitorCmd<Integer, String, Integer, IHostStr>() {
			@Override
			public Integer apply(String index, IHostStr host, Integer... params) {
				return 5678;
			}
		});

		int i = host1.execute(algo);
		assertEquals("host1.execute(algo)", 1234, i);

		HostStr2 host2 = new HostStr2();
		i = host2.execute(algo);
		assertEquals("host2.execute(algo)", 5678, i);

		HostStr3 host3 = new HostStr3();
		i = host3.execute(algo);
		assertEquals("host3.execute(algo)", -99, i);

		algo.setCmd("HostStr3", new IExtVisitorCmd<Integer, String, Integer, IHostStr>() {
			@Override
			public Integer apply(String index, IHostStr host, Integer... params) {
				return -987;
			}
		});

		i = host3.execute(algo);
		assertEquals("hostB.execute(algo)", -987, i);
	}



	public void test_HostInt() {
		HostInt host42 = new HostInt(42);
		HostInt host99 = new HostInt(99);

		HostIntVis1  algo = new HostIntVis1(); 

		algo.setCmd(42, new IExtVisitorCmd<String, Integer, Integer, AExtVisitorHost<Integer>>() {
			@Override
			public String apply(Integer index, AExtVisitorHost<Integer> host, Integer... params) {
				return "Hello";
			}
		});

		assertEquals("host42.execute(algo)", "Hello", host42.execute(algo));
		assertEquals("host99.execute(algo)", "No cmd found!", host99.execute(algo));

		algo.setCmd(99, new IExtVisitorCmd<String, Integer, Integer, AExtVisitorHost<Integer>>() {
			@Override
			public String apply(Integer index, AExtVisitorHost<Integer> host, Integer... params) {
				return "Bye";
			}
		});

		assertEquals("host99.execute(algo)", "Bye", host99.execute(algo));

	}
}
class HostStr implements IExtVisitorHost<String, HostStr> {
	private String s;

	public HostStr(String s) {
		this.s = s;
	}

	@Override
	public <R, P> R execute(IExtVisitor<R, String, P, HostStr> algo,  @SuppressWarnings("unchecked") P... params) {
		return algo.caseAt(s, this, params);
	}  
}

interface IHostStr extends IExtVisitorHost<String, IHostStr> {
}

class HostStr1 implements IHostStr {
	@Override
	public <R, P> R execute(IExtVisitor<R, String, P, IHostStr> algo, @SuppressWarnings("unchecked") P... params) {
		return algo.caseAt("HostStr1", this, params);
	}  
}

class HostStr2 implements IHostStr {
	@Override
	public <R, P> R execute(IExtVisitor<R, String, P, IHostStr> algo, @SuppressWarnings("unchecked") P... params) {
		return algo.caseAt("HostStr2", this, params);
	}  
}

class HostStr3 implements IHostStr {
	@Override
	public <R, P> R execute(IExtVisitor<R, String, P, IHostStr> algo, @SuppressWarnings("unchecked") P... params) {
		return algo.caseAt("HostStr3", this, params);
	}  
}

class HostStrVis extends AExtVisitor<Integer, String, Integer, HostStr> {
	public HostStrVis() {
		super(-1);
	} 
}

class HostStrVis2 extends AExtVisitor<Integer, String, Integer, IHostStr> {
	public HostStrVis2() {
		super(-99);
	} 
}

class HostInt extends AExtVisitorHost<Integer> {
	public HostInt(int i) {
		super(i);
	}
}

class HostIntVis1 extends AExtVisitor<String, Integer, Integer, AExtVisitorHost<Integer>> {
	public HostIntVis1() {
		super("No cmd found!");
	} 
}
