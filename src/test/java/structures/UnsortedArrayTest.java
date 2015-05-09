package structures;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.Random;

public class UnsortedArrayTest {
	private static final Random RND = new Random();

	@Test
	public void testAddWithoutArrayEnlargement() {
		int expectedArraySize = 10;
		UnsortedArray<Integer> array = new UnsortedArray<Integer>(expectedArraySize);
		fillArrayWithConsecutiveElements(array, 0, expectedArraySize);
		assertEquals("Wrong array size", expectedArraySize, array.size());
	}

	@Test
	public void testAddWithArrayEnlargement() {
		int expectedArraySize = 10;
		UnsortedArray<Integer> array = new UnsortedArray<Integer>(expectedArraySize);
		fillArrayWithConsecutiveElements(array, 0, expectedArraySize);
		assertEquals("Wrong array size", expectedArraySize, array.size());
		fillArrayWithConsecutiveElements(array, 11, expectedArraySize);
		assertEquals("Wrong array size", 2 * expectedArraySize, array.size());
		fillArrayWithConsecutiveElements(array, 21, expectedArraySize);
		assertEquals("Wrong array size", 4 * expectedArraySize, array.size());
	}

	@Test
	public void testGet() {
		int arraySize = 20;
		UnsortedArray<Integer> array = new UnsortedArray<Integer>(arraySize);
		fillArrayWithConsecutiveElements(array, 0, arraySize);

		for (int idx = 0; idx < arraySize; idx++) {
			assertEquals("Wrong element at index " + idx, idx, (int)array.get(idx));
		}
	}

	@Test
	public void testSearch() {
		//fill array so that only 3/4 of the array is occupied
		int arraySize = 15;
		UnsortedArray<Integer> array = new UnsortedArray<Integer>(10);
		fillArrayWithConsecutiveElements(array, 0, arraySize);

		for (int idx = 0; idx < arraySize; idx++) {
			assertEquals("Wrong element position", idx, array.search(idx));
		}
	}

	@Test
	public void testSearchElementNotFound() {
		//fill array so that only 3/4 of the array is occupied
		int arraySize = 15;
		UnsortedArray<Integer> array = new UnsortedArray<Integer>(10);
		fillArrayWithConsecutiveElements(array, 0, arraySize);

		assertEquals("Element found", -1, array.search(1000));
	}

	@Test
	public void testDelete() {
		UnsortedArray<Integer> array = new UnsortedArray<Integer>(10);
		//fill array so that only 3/4 of the array is occupied
		fillArrayWithConsecutiveElements(array, 0, 15);
		assertEquals("Wrong array size", 20, array.size());

		for (int idx = 0; idx < 15; idx++) {
			array.delete(idx);
			assertEquals("Element not removed", -1, array.search(idx));
		}

		//array should be have been shinked once during the deletion
		assertEquals("Wrong allocation size", 2, array.size());
	}

	@Test
	public void testIterator() {
		UnsortedArray<Integer> array = new UnsortedArray<Integer>(10);
		//fill array so that only 3/4 of the array is occupied
		int noElements = 15;
		fillArrayWithConsecutiveElements(array, 0, noElements);
		int idx = 0;
		for (Integer element : array) {
			assertEquals("Wrong element", idx++, (int)element);
		}
		assertEquals("Wrong iteration count", noElements, idx);
	}

	private void fillArrayWithConsecutiveElements(UnsortedArray array, int first, int noElements) {
		int last = first + noElements;
		for (int idx = first; idx < last; idx++) {
			array.add(idx);
		}
	}
}