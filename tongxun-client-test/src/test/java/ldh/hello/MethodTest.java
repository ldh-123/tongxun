package ldh.hello;

import ldh.test.Hello;

public class MethodTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Class<?> clazz = Hello.class;
		System.out.println(clazz);
		System.out.println(Object.class);
		System.out.println(clazz.getDeclaringClass());
		while (clazz != null && clazz != Object.class) {
			System.out.println(clazz);
			clazz = clazz.getDeclaringClass();
		}

	}

}
