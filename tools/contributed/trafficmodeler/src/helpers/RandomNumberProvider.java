package helpers;

import java.util.Date;
import java.util.Random;

public class RandomNumberProvider {
	private static Random r = null;

	public static Random getRandom() {
		if (r == null) {
			// TODO seed
			r = new Random((new Date()).getTime());
		}

		return r;
	}
}
