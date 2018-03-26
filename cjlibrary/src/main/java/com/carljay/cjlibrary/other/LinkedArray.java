package com.carljay.cjlibrary.other;

public class LinkedArray<T> {
	private Node first;
	private Node last;
	
	private int count;

	public synchronized T getFirst() {
		if (first == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		count--;
		Node node = first;
		first = first.last;
		if (last == node) {
			last = null;
		}
		return node.obj;
	}

	public synchronized T getFirstNoWait() {
		if (first == null) {
			return null;
		} else {
			count--;
			Node node = first;
			first = first.last;
			if (last == node) {
				last = null;
			}
			return node.obj;
		}
	}

	public synchronized void add(T t) {
		Node node = new Node();
		node.obj = t;
		if (last == null) {
			first = node;
			last = node;
		} else {
			last.last = node;
			last = node;
		}
		count++;
		notify();
	}

	public synchronized void clear(){
		first = null;
		last = null;
		notify();
	}
	class Node {
		Node last;
		T obj;
	}

	public synchronized void addFirst(T removeFirst) {
		Node node = new Node();
		node.obj = removeFirst;
		first = node;
		first.last = first;
		count++;
		notify();
	}

}
