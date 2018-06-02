package blatt_07_aufg_03;

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
			return Math.abs(akb % m);
		}

		// Die Zahlen a und b sind beliebige positive Zahlen aus dem Bereich [1, ..., p-1] 
		// bzw. [0, ..., p-1]. Die Hashfunktion generiert Werte aus dem Bereich [0, ..., m-1] mit m beliebig.
		private int a;
		private int b;
		private int m;
	}
	
	UniversalHash h0, h1;
	int [] t0,t1;
	int m;

	// Erzeugt eine Hashtabelle der Größe m.
	public Cuckoo(int m) {
		h0 = new UniversalHash(m/2);
		h1 = new UniversalHash(m/2);
		t0 = new int[m/2];
		t1 = new int[m/2];
		this.m = m;
	}

	// Testet, ob ein gegebener Schlüssel in der Hashtabelle ist.
	public boolean find(int key) {
		return t0[h0.compute(key)] == key || t1[h1.compute(key)] == key;
	}

	public void insert(int key) {
		if(find(key)) return;
		int i;
		for(i = 1; i <= m/2; i++) {
			if(t0[h0.compute(key)] == 0) t0[h0.compute(key)] = key;
			else if(t1[h1.compute(key)] == 0) t1[h1.compute(key)] = key;
			else {
				if(i % 2 == 0) {
					int tmp = t1[h1.compute(key)];
					t1[h1.compute(key)] = key;
					key = tmp;
				}else {
					int tmp = t0[h0.compute(key)];
					t0[h0.compute(key)] = key;
					key = tmp;
				}
			}
		}
		if(i > m/2) {
			rehash();
			i = 1;
			insert(key);
		}
	}

	public void remove(int key) {
		if(t0[h0.compute(key)] == key) t0[h0.compute(key)] = 0;
		if(t1[h1.compute(key)] == key) t1[h1.compute(key)] = 0;
	}

	private void rehash() {
		// Regeneriert die Hashtabelle mit zwei neuen Hashfunktionen.
		h0 = new UniversalHash(m/2);
		h1 = new UniversalHash(m/2);
		for(int i = 0; i < t0.length; i++) {
			if(t0[i] != 0 && t0[i] != t0[h0.compute(t0[i])]) {int toInsert = t0[i]; t0[i] = 0; insert(toInsert);}
			if(t1[i] != 0 && t1[i] != t1[h1.compute(t1[i])]) {int toInsert = t1[i]; t1[i] = 0; insert(toInsert);}
		}
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

