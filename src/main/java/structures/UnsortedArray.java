package structures;

import java.util.Arrays;
import java.lang.Iterable;
import java.util.Iterator;

/**
 * Weak typing unsorted array.<br>
 * Implmementation properties:<br>
 * <ul>
 * <li>elemets order is not preserved during elements deletion</li>
 * <li>if newly added element doesn't fit into currently allocated memory, size of the array is doubled</li>
 * <li>after element is removed from the array and if there is less than 1/4 
 * buckets occupied in the array, array is shrinked by half</li>
 * <li>{@code UnsortedArray} {@code Iterator} doesn't support remove operation
 * since removing element from an array doesn't preserve elements order</li>
 * </ul>
 * @author Łukasz Koniecki
 * @version 1.0.0
 */
public class UnsortedArray<V> implements Iterable<V> {
	//element not found in the array
	public static final int ELEMENT_NOT_FOUND = -1;
	//array elements
	private Object[] elements;
	//last element in the array pointer
	private int pointer = 0;

	/**
	 * Creates array of type V and given size {@code size}
	 * @param size size of the array
	 * @throws IllegalArgumentException if given size is less than 1
	 * @since 1.0.0
	 */
	public UnsortedArray(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Array size must be grater than 0");
		}
		elements = new Object[size];
	}

	/**
	 * Adds element to the end of the array
	 * @param element element to add
	 * @throws IllegalArgumentException if element is null
	 * @since 1.0.0
	 */
	public void add(V element) {
		if (element == null) {
			throw new IllegalArgumentException("Element can not be null");
		}

		if (pointer < elements.length) {
			elements[pointer++] = element;
		} else {
			elements = Arrays.copyOf(elements, elements.length * 2);
			elements[pointer++] = element;
		}
	}

	/**
	 * Gets element at {@code index} in the array
	 * @param index of element to return
	 * @return element at possition {@code index}
	 * @since 1.0.0
	 */
	public V get(int index) {
		return (V) elements[index];
	}

	/**
	 * Gets length of the array
	 * @return length of the array
	 * @since 1.0.0
	 */
	public int length() {
		return pointer+1;
	}

	/**
	 * Returns size of the array in the memory (in number of allocated elements)
	 * @return size of the array (in number of allocated elements)
	 * @since 1.0.0
	 */
	public int size() {
		return elements.length;
	}

	/**
	 * Returns position of the first element equal {@code element} in the array
	 * @param element element to search
	 * @return element position if the array if element was found in the array, -1 otherwise
	 * @throws IllegalArgumentException if element is null
	 * @since 1.0.0
	 */
	public int search(V element) {
		if (element == null) {
			throw new IllegalArgumentException("Element can not be null");
		}

		//if this is an empty array
		if (pointer == 0) {
			return ELEMENT_NOT_FOUND;
		}

		for (int idx = 0; idx < pointer; idx++) {
			if (elements[idx] == element) {
				return idx;
			}
		}

		return ELEMENT_NOT_FOUND;
	}

	/**
	 * Removes first element of value {@code element} from an array.
	 * Array elements order may not be preserved.
	 * @param element element to remove
	 * @since 1.0.0
	 */
	public void delete(V element) {
		//find first element of given value in the array
		int idx = search(element);
		//if element does not exist in the array
		if (idx == ELEMENT_NOT_FOUND) {
			//just return
			return;
		}

		//if this is the last element in the array
		if (pointer == 1) {
			//just set it to null
			elements[idx] = null;
			//decrease pointer
			pointer--;
		} else {
			//decrease pointer
			pointer--;
			//copy last elemnt from the array to a position of element being removed
			elements[idx] = elements[pointer];
			//set last element in the array to null
			elements[pointer] = null;
		}

		//if less than 1/4 of available buckets in the array is available shrink the array
		if (pointer < elements.length/4) {
			//new array will be half size of the old array
			elements = Arrays.copyOf(elements, elements.length / 2);
		}
	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("[");
		for (int idx = 0; idx < pointer; idx++) {
			b.append(elements[idx] + ",");
		}
		b.append("]");
		return b.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<V> iterator() {
		return new Iterator<V>() {
			private int idx = 0;

			@Override
			public boolean hasNext() {
				if (idx < pointer) {
					return true;
				}
				return false;
			}

			@Override
			public V next() {
				return (V)elements[idx++];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Not implemented");
			}
		};
	}
}