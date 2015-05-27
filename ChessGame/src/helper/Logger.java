package helper;

public final class Logger {
	public static void info(final String s) {
		log("INFO: " + s);
	}

	public static void warn(final String s) {
		log("WARN: " + s);
	}

	public static void error(final String s) {
		log("ERROR: " + s);
	}

	private static void log(final String s) {
		System.out.println(s);
	}
}
