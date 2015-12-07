package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class that acts as a consumer of an input stream. It continuously reads lines from the stream until the stream is empty. This class is used to retrieve the output from external processes such as DUARouter or NetConvert.
 */
public class StreamGobbler extends Thread {
	/**
	 * The input stream from which theg gobbler will read data.
	 */
	private InputStream is;

	/**
	 * List containing the lines read by the stream.
	 */
	private List<String> readLines = new ArrayList<String>();

	/**
	 * Default constructor
	 * @param is The stream from which the gobbler will read.
	 */
	public StreamGobbler(InputStream is) {
		this.is = is;
	}

	/**
	 * Starts the gobbler.
	 */
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line = null;

			while ((line = br.readLine()) != null)
				if (!readLines.contains(line)) {
					readLines.add(line);
				}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Returns the lines read from the stream.
	 * @return
	 * @uml.property  name="readLines"
	 */
	public List<String> getReadLines() {
		return readLines;
	}
}
