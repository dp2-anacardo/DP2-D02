
package sample;

import java.util.HashMap;
import java.util.Map;

import domain.Priority;

public class TrashTest {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		final Priority p1 = new Priority();
		final Priority p2 = new Priority();

		final Map<String, String> mP1 = new HashMap<String, String>();
		mP1.put("ES", "TEST");
		mP1.put("EN", "TESTE");

		final Map<String, String> mP2 = new HashMap<String, String>();
		mP2.put("ES", "TEST");
		mP2.put("EN", "TESTE");

		p1.setId(456);
		p1.setName(mP1);

		p2.setId(123);
		p2.setName(mP2);

		System.out.println(p1.equals(p2));

	}
}
