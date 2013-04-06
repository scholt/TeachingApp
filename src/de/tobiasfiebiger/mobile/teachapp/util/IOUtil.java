package de.tobiasfiebiger.mobile.teachapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {

  private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

  public static void copyStream(InputStream inStr, OutputStream outStr, int bufferSize) throws IOException {
	byte[] buf = new byte[bufferSize];
	for (;;) {
	  int got = inStr.read(buf, 0, bufferSize);
	  if (got < 0) {
		break;
	  } else if (got > 0) {
		outStr.write(buf, 0, got);
	  }
	}
	inStr.close();
  }

  public static int copy(InputStream input, OutputStream output) throws IOException {
	byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	int count = 0;
	int n = 0;
	while (-1 != (n = input.read(buffer))) {
	  output.write(buffer, 0, n);
	  count += n;
	}
	return count;
  }

}
