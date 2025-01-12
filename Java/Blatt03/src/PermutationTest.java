import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PermutationTest {
	PermutationVariation p1;
	PermutationVariation p2;
	public int n1;
	public int n2;
	int cases = 0;

	void initialize() {
		n1 = 4;
		n2 = 6;
		Cases c = new Cases();
		p1 = c.switchforTesting(cases, n1);
		p2 = c.switchforTesting(cases, n2);
	}


	@Test
	void testPermutation() {
		initialize();

		if ((p1.allDerangements == null) || (p2.allDerangements == null) || (p1.original == null) || (p2.original == null)) {

			fail();

		}




		assertEquals(n1, Arrays.stream(p1.original).distinct().toArray().length);
		assertEquals(n2, Arrays.stream(p2.original).distinct().toArray().length);

		assertTrue(p1.allDerangements.isEmpty());
		assertTrue(p2.allDerangements.isEmpty());




		// TODO
	}

	@Test
	void testDerangements() {

		initialize();
		fixConstructor();
		p1.derangements();
		p2.derangements();


		if (Fixpunkt(p1.original.length) != p1.allDerangements.toArray().length) {

			fail();

		}
		if (Fixpunkt(p2.original.length) != p2.allDerangements.toArray().length) {

			fail();

		}

		assertTrue(areDerangements(p1.allDerangements, p1.original));
		assertTrue(areDerangements(p2.allDerangements, p2.original));




		// TODO
	}

	@Test
	void testsameElements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		p1.derangements();
		p2.derangements();


		if ((p1.allDerangements == null) || (p2.allDerangements == null) || (p1.original == null) || (p2.original == null)) {

			fail();

		}
		if ((p1.allDerangements.isEmpty()) || (p2.allDerangements.isEmpty())) {

			fail();

		}

		assertTrue(arePermutations(p1.allDerangements, p1.original));
		assertTrue(arePermutations(p2.allDerangements, p2.original));
		// TODO




	}

	void setCases(int c) {
		this.cases = c;
	}

	public void fixConstructor() {
		//in case there is something wrong with the constructor
		p1.allDerangements = new LinkedList<int[]>();
		for (int i = 0; i < n1; i++)
			p1.original[i] = 2 * i + 1;

		p2.allDerangements = new LinkedList<int[]>();
		for (int i = 0; i < n2; i++)
			p2.original[i] = i + 1;
	}

	public int Fixpunkt(int n) {

		if (n == 0) {

			return 0;
		}
		 return Fixpunkt2(n);



	}
	public int Fixpunkt2(int n) {

		if (n == 0) {
			return 1;
		}
		if (n == 1) {
			return 0;
		}

		return ((n - 1) * (Fixpunkt2(n - 1) + Fixpunkt2(n - 2))); //Formel kommt von Wikipedia
	}

	public boolean isDerangement(int[] permutation, int[] original) {


		for (int i = 0; i < permutation.length; i++) {
			if (permutation[i] == original[i]) {
				return false;
			}
		}
		return true;
	}

	public boolean areDerangements(LinkedList<int[]> derangements, int[] original) {



		for (int[] permutation : derangements) {
			if (!isDerangement(permutation, original)) {
				return false;
			}
		}
		return true;
	}

	public boolean isPermutation(int[] permutation, int[] original) {


		int [] a = new int[permutation.length];
		a = permutation.clone();
		Arrays.sort(a);

		int [] b = new int[original.length];
		b = original.clone();
		Arrays.sort(b);

		for (int i = 0; i < a.length; i++) {

			if (a[i] != b[i]) {
				return false;

			}
		}
		return true;


		}

	private boolean arePermutations(LinkedList<int[]> permutations, int[] original) {

		for (int[] permutation : permutations) {
			if (!isPermutation(permutation, original)) {
				return false;
			}
		}
		return true;
	}
	}



