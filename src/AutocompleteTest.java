import static org.junit.Assert.*;

import org.junit.Test;

public class AutocompleteTest {

	@Test
	public void testAutocomplete() {
		Autocomplete au = new Autocomplete();
		assertNotNull(au);
	}
	
	@Test
	public void testAddword() {
		Autocomplete au = new Autocomplete();
		au.addWord("test", 100);
		assertEquals(au.countPrefixes(""), 1);
		assertEquals(au.countPrefixes("t"), 1);
		assertEquals(au.countPrefixes("te"), 1);
		assertEquals(au.countPrefixes("tes"), 1);
		assertEquals(au.countPrefixes("test"), 1);
		au.addWord("test1", 100);
		assertEquals(au.countPrefixes("test"), 1);
		au.addWord("testing", 100);
		assertEquals(au.countPrefixes("testing"), 1);
		au.addWord("testin", 99);
		assertEquals(au.countPrefixes("testin"), 2);
		au.addWord("testi", 99);
		assertEquals(au.countPrefixes("testing"), 1);


	}
	
	@Test
	public void testsubTrie() {
		Autocomplete au = new Autocomplete();
		assertEquals(au.countPrefixes(null), 0);
		assertNull(au.getSubTrie(null));
		assertNull(au.getSubTrie("a"));
		au.addWord("test", 100);
		au.addWord("testa", 99);
		au.addWord("testbn", 99);
		au.addWord("testcng", 100);
		assertNotNull(au.getSubTrie("test"));
		assertNotNull(au.getSubTrie("test").getReferences()[0]);
		assertNotNull(au.getSubTrie("test").getReferences()[1]);
		assertNotNull(au.getSubTrie("test").getReferences()[2]);
		
	}
	
	@Test
	public void testcountPrefix() {
		Autocomplete au = new Autocomplete();
		assertEquals(au.countPrefixes(""), 0);
		au.addWord("test", 100);
		assertEquals(au.countPrefixes(""), 1);
		assertEquals(au.countPrefixes("t"), 1);
		assertEquals(au.countPrefixes("te"), 1);
		assertEquals(au.countPrefixes("tes"), 1);
		assertEquals(au.countPrefixes("test"), 1);
		au.addWord("test1", 100);
		assertEquals(au.countPrefixes(""), 1);
		assertEquals(au.countPrefixes("t"), 1);
		assertEquals(au.countPrefixes("te"), 1);
		assertEquals(au.countPrefixes("tes"), 1);
		assertEquals(au.countPrefixes("test"), 1);
		au.addWord("testing", 100);
		assertEquals(au.countPrefixes(""), 2);
		assertEquals(au.countPrefixes("t"), 2);
		assertEquals(au.countPrefixes("te"), 2);
		assertEquals(au.countPrefixes("tes"), 2);
		assertEquals(au.countPrefixes("test"), 2);
		assertEquals(au.countPrefixes("testi"), 1);
		assertEquals(au.countPrefixes("testin"), 1);
		assertEquals(au.countPrefixes("testing"), 1);
		au.addWord("testin", 99);
		assertEquals(au.countPrefixes(""), 3);
		assertEquals(au.countPrefixes("t"), 3);
		assertEquals(au.countPrefixes("te"), 3);
		assertEquals(au.countPrefixes("tes"), 3);
		assertEquals(au.countPrefixes("test"), 3);
		assertEquals(au.countPrefixes("testi"), 2);
		assertEquals(au.countPrefixes("testin"), 2);
		assertEquals(au.countPrefixes("testing"), 1);
		au.addWord("testi", 99);
		assertEquals(au.countPrefixes(""), 4);
		assertEquals(au.countPrefixes("t"), 4);
		assertEquals(au.countPrefixes("te"), 4);
		assertEquals(au.countPrefixes("tes"), 4);
		assertEquals(au.countPrefixes("test"), 4);
		assertEquals(au.countPrefixes("testi"), 3);
		assertEquals(au.countPrefixes("testin"), 2);
		assertEquals(au.countPrefixes("testing"), 1);
	}
	
	@Test
	public void testgetSuggestion() {
		Autocomplete au = new Autocomplete();
		assertEquals(au.countPrefixes(""), 0);
		au.addWord("test", 100);
		au.addWord("testing", 100);
		au.addWord("testin", 99);
		au.addWord("testi", 99);
		assertEquals(au.getSuggestions("testa").size(), 0);
		assertEquals(au.getSuggestions("test").size(), 4);
		assertTrue(((Term)au.getSuggestions("test").get(3)).getTerm().equals("testing"));
		assertTrue(((Term)au.getSuggestions("test").get(2)).getTerm().equals("testin"));
		assertTrue(((Term)au.getSuggestions("test").get(1)).getTerm().equals("testi"));
		assertTrue(((Term)au.getSuggestions("test").get(0)).getTerm().equals("test"));
	}
	
	@Test
	public void testBuildTrie() {
		String fp = "./src/testdict.txt"; 
		Autocomplete au = new Autocomplete();
		au.buildTrie(fp, 5);
		assertEquals(au.numberSuggestions(), 5);
		assertEquals(au.countPrefixes(""), 6);
		assertEquals(au.countPrefixes("a"), 6);
		assertEquals(au.getSuggestions("apple").size(), 6);
		assertEquals(au.countPrefixes("apple"), 6);

		assertTrue(((Term)au.getSuggestions("apple").get(0)).getTerm().equals("applea"));
		assertTrue(((Term)au.getSuggestions("apple").get(1)).getTerm().equals("appleab"));
		assertTrue(((Term)au.getSuggestions("apple").get(2)).getTerm().equals("appleabc"));
		assertTrue(((Term)au.getSuggestions("apple").get(3)).getTerm().equals("appleabcd"));
		assertTrue(((Term)au.getSuggestions("apple").get(4)).getTerm().equals("applebade"));
		assertTrue(((Term)au.getSuggestions("apple").get(5)).getTerm().equals("applecdab"));

		assertEquals(au.countPrefixes("appleab"), 3);

		assertTrue(((Term)au.getSuggestions("appleab").get(0)).getTerm().equals("appleab"));
		assertTrue(((Term)au.getSuggestions("appleab").get(1)).getTerm().equals("appleabc"));
		assertTrue(((Term)au.getSuggestions("appleab").get(2)).getTerm().equals("appleabcd"));

	}
	
	
	@Test
	public void testTerm() {
		Term nt = new Term("a", 100);
		assertNotNull(nt);
		assertTrue(nt.getTerm().equals("a"));
		assertEquals(nt.getWeight(), 100);
	}
	
	@Test
	public void testsetTerm() {
		Term nt = new Term("a", 100);
		nt.setTerm("b");
		assertTrue(nt.getTerm().equals("b"));
	}
	
	@Test
	public void testsetweight() {
		Term nt = new Term("a", 100);
		nt.setWeight(200);
		assertEquals(nt.getWeight(), 200);
	}
	
	@Test
	public void testTermtoString() {
		Term nt = new Term("a", 100);
		assertTrue(nt.toString().equals("100\ta"));
	}
	
	@Test
	public void testTermcompare() {
		Term nt = new Term("a", 100);
		Term nt2 = new Term("a", 200);

		assertEquals(nt.compareTo(nt2), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateTerm() {
		Term nt = new Term(null, 100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateNode() {
		Node nt = new Node(null, 100);
	}


}
