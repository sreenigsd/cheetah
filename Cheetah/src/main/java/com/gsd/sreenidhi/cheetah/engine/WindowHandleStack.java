package com.gsd.sreenidhi.cheetah.engine;

public class WindowHandleStack {
	 private int maxSize;
	   private String[] stackArray;
	   private int top;
	   
	   public WindowHandleStack(int s) {
	      maxSize = s;
	      stackArray = new String[maxSize];
	      top = -1;
	   }
	   public void push(String handle) {
	      stackArray[++top] = handle;
	   }
	   public String pop() {
	      return stackArray[top--];
	   }
	   public String peek() {
	      return stackArray[top];
	   }
	   public boolean isEmpty() {
	      return (top == -1);
	   }
	   public boolean isFull() {
	      return (top == maxSize - 1);
	   }
}
