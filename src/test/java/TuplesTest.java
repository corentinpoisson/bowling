package tests;

import java.util.ArrayList;

import junit.framework.TestCase;
import tools.Tuple;

public class TuplesTest extends TestCase {
	
	public void testSommeTuplet() throws Exception {
		Tuple<Integer, Integer> t = new Tuple<>(7, 4);
		
		assertEquals(new Integer(11), sommeTuple(t));		
	}
	
	public void testTableauSommeTuplets() throws Exception {
		ArrayList<Tuple<Integer,Integer>> tabTuple = new ArrayList<Tuple<Integer,Integer>>(3);
		tabTuple.add(new Tuple<Integer, Integer>(1, 2));
		tabTuple.add(new Tuple<Integer, Integer>(2, 2));
		tabTuple.add(new Tuple<Integer, Integer>(0, 1));
		
		assertTrue(tabTuple.size() <= 10);
		
		Integer sommeDesSommes = 0;
		
		for(Tuple<Integer,Integer> t : tabTuple) {
			assertTrue(t.x >= 0);
			assertTrue(t.y >= 0);
			Integer somme = sommeTuple(t);
			assertTrue(somme <= 10);
			sommeDesSommes += somme; 
		}
		
		assertEquals(new Integer(8), sommeDesSommes);
		
	}

	private Integer sommeTuple(Tuple<Integer, Integer> t) {
		return t.x + t.y;
	}
	
	
}
