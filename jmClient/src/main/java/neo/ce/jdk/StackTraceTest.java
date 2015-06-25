package neo.ce.jdk;

public class StackTraceTest {

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			testTrace();
		}

	}

	private static void testTrace() {
		Throwable tb = new Throwable();
		StackTraceElement[] traceElements = tb.getStackTrace();
		for (StackTraceElement stackTraceElement : traceElements) {
			System.out.println(stackTraceElement);
		}
	}
}
