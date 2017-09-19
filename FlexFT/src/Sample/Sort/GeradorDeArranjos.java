package Sample.Sort;

public abstract class GeradorDeArranjos {

	public static final int MAX_VALUE = 100;

	private static int valorAleatorio() {
		return valorAleatorio(MAX_VALUE);
	}

	private static int valorAleatorio(int max) {
		return (int) (max * java.lang.Math.random());
	}

	public static Integer[] geraArranjoCrescente(int tam) {
		Integer[] v = new Integer[tam];
		for (int i = 0; i < tam; i++) {
			v[i] = i + 1;
		}
		return v;
	}

	public static Integer[] geraArranjoAleatorio(int tam) {
		Integer[] v = new Integer[tam];
		for (int i = 0; i < tam; i++) {
			v[i] = valorAleatorio();
		}
		return v;
	}

	public static Integer[] geraArranjoDecrescente(int tam) {
		Integer[] v = new Integer[tam];
		for (int i = 0; i < tam; i++) {
			v[i] = tam - i;
		}
		return v;
	}

	public static void imprimeArranjo(Integer[] v) {
		int i;
		for (i = 0; i < v.length; ++i) {
			System.out.print(v[i] + " ");
		}
		System.out.println("");
	}
}