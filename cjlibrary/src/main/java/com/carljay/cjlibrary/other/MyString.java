package com.carljay.cjlibrary.other;

public class MyString {

	private byte[][] mBytes = new byte[15][];;

	private int curr = 0;

	private int count = 15;

	private int byteCount = 0;

	public MyString() {
	}

	public MyString(int len) {

	}

	public MyString(byte[] b) {
		mBytes[curr++] = b;
	}

	public void cliean() {
		mBytes = new byte[15][];
		curr = 0;
		count = 15;
		byteCount = 0;
	}

	public void append(byte[] b) {
		if (mBytes == null) {
			mBytes = new byte[15][];
		}
		if (curr >= count) {
			byte[][] temp = mBytes;
			count += 15;
			mBytes = new byte[count][];
			for (int i = 0; i < temp.length; i++) {
				mBytes[i] = temp[i];
			}
		}
		mBytes[curr++] = b;
		byteCount += b.length;
	}

	public void append(byte[] b, int start, int end) {
		if (start == 0 && end == b.length) {
			append(b.clone());
			return;
		}
		byte[] by = new byte[end - start];
		for (int i = start; i < end; i++) {
			by[i - start] = b[i];
		}
		append(by);
	}

	public String getString() {
		byte[] b = new byte[byteCount];
		int cu = 0;
		for (int i = 0; i < curr; i++) {
			byte[] ds = mBytes[i];
			for (byte c : ds) {
				b[cu++] = c;
			}
		}
		return new String(b);
	}

	@Override
	public String toString() {
		return getString();
	}

	public static void main(String[] args) {
		MyString str = new MyString();
		byte[] bytes = "123���".getBytes();
		str.append("123".getBytes(), 0, 3);
		str.append(bytes, bytes.length - 4, bytes.length);
		System.out.println(str);
	}
}
