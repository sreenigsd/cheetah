package com.gsd.sreenidhi.cheetah.actions;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FormationStringStack {
	 private String[] items;  // holds the items
	    private int n;           // number of items in stack

	    public FormationStringStack(int capacity) {
	        items = new String[capacity];
	    }

	    public boolean isEmpty() {
	        return n == 0; 
	    }

	    public boolean isFull() {
	        return n == items.length; 
	    }

	    public void push(String item) {
	        items[n++] = item;
	    }

	    public String pop() {
	        return items[--n];
	    }

	    public Iterator<String> iterator() {
	        return new ReverseArrayIterator();
	    }

	    // an iterator, doesn't implement remove() since it's optional
	    private class ReverseArrayIterator implements Iterator<String> {
	        private int i = n-1;
	        public boolean hasNext()  { return i >= 0;                              }
	        public void remove()      { throw new UnsupportedOperationException();  }

	        public String next() {
	            if (!hasNext()) throw new NoSuchElementException();
	            return items[i--];
	        }
	    }

}