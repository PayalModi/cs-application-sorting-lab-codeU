/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		int size = list.size();
        if (size == 1)
        	return list;

        List<T> left = mergeSort(new LinkedList<T>(list.subList(0, size/2)), comparator);
        List<T> right = mergeSort(new LinkedList<T>(list.subList(size/2, size)), comparator);

        return merge(left, right, comparator);
	}

	public List<T> merge(List<T> left, List<T> right, Comparator<T> comparator) {
		List<T> mergedList = new LinkedList<T>();

		while (left.size() > 0 && right.size() > 0) {
			T leftVal = left.get(0);
			T rightVal = right.get(0);
			int result = comparator.compare(leftVal, rightVal);

			if (result < 0) {
				mergedList.add(leftVal);
				left.remove(0);
			}
			else {
				mergedList.add(rightVal);
				right.remove(0);
			}
		}

		while (right.size() > 0) {
			mergedList.add(right.get(0));
			right.remove(0);
		}
		while (left.size() > 0) {
			mergedList.add(left.get(0));
			left.remove(0);
		}

		return mergedList;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
        while (list.size() > 0) {
        	heap.offer(list.get(0));
        	list.remove(0);
        }
        list.clear();
        while(heap.size() > 0) {
        	list.add(heap.poll());
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
        for (T element : list) {
        	if (heap.size() < k) {
        		heap.offer(element);
        	}
        	else if (comparator.compare(element, heap.peek()) > 0) {
        		heap.poll();
        		heap.offer(element);
        	}
        }
        List<T> topKElements = new ArrayList<T>();
        while (! heap.isEmpty()) {
        	topKElements.add(heap.poll());
        }
        return topKElements;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
