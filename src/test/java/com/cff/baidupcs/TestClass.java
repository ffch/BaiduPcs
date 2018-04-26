package com.cff.baidupcs;

public class TestClass {
	
	{
		System.out.print("非静态代码块！-->");
	}

	// 静态代码块
	static {
		System.out.print("静态代码块！-->");
	}

	public static void test() {
		{
			System.out.println("普通方法中的代码块！");
		}
	}
	
	public static void main(String[] args) {
//		TestClass.test();
		TestClass c1 = new TestClass();
//		c1.test();
	}
}
