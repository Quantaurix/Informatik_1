
import java.util.Random;

public class Cuckoo {
	private static Random rnd = new Random();

	private static class UniversalHash {
		// p muss eine Primzahl sein, die größer als jeder mögliche Schlüssel ist.
		// Glücklicherweise ist Integer.MAX_VALUE eine Mersenne-Primzahl.
		final private int p = Integer.MAX_VALUE; 

		public UniversalHash(int m) {
			this.a = Math.abs(rnd.nextInt() % (Integer.MAX_VALUE - 2)) + 1;
			this.b = Math.abs(rnd.nextInt() % (Integer.MAX_VALUE - 1));
			this.m = m;
		}

		public int compute(int k) {
			int ak = (a * k) % p;
			int akb = (ak + b) % p;
			return akb % m;
		}

		// Die Zahlen a und b sind beliebige positive Zahlen aus dem Bereich [1, ..., p-1] 
		// bzw. [0, ..., p-1]. Die Hashfunktion generiert Werte aus dem Bereich [0, ..., m-1] mit m beliebig.
		private int a;
		private int b;
		private int m;
	}

	// Erzeugt eine Hashtabelle der Größe m.
	public Cuckoo(int m) {

	}

	// Testet, ob ein gegebener Schlüssel in der Hashtabelle ist.
	public boolean find(int key) {
		return false;
	}

	public void insert(int key) {

	}

	public void remove(int key) {

	}

	private void rehash() {
		// Regeneriert die Hashtabelle mit zwei neuen Hashfunktionen.
	}

	public static void main(String[] args) {
		Cuckoo ht = new Cuckoo(64);

		int n = 0;
		int m = 0;
		boolean[] shadow = new boolean[32 * 10];
		while(n < 32 && m < shadow.length) {
			m += rnd.nextInt(9) + 1;
			n++;

			shadow[m] = true;
			ht.insert(m);
		}

		int errors = 0;
		for(int i = 0; i < shadow.length; i++) {
			if(shadow[i] && !ht.find(i)) {
				System.out.println(i + " is NOT in hash table but should be");
				errors++;
			}else if(shadow[i] && !ht.find(i)) {
				System.out.println(i + " is in hash table but should NOT be");
				errors++;
			}
		}

		System.out.println("There were " + errors + " errors");
	}
}

