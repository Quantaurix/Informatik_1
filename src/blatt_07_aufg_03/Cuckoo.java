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
			int akbmodm = Math.abs(akb % m);
			return Math.abs(akb % m);
		}

		// Die Zahlen a und b sind beliebige positive Zahlen aus dem Bereich [1, ..., p-1] 
		// bzw. [0, ..., p-1]. Die Hashfunktion generiert Werte aus dem Bereich [0, ..., m-1] mit m beliebig.
		private int a;
		private int b;
		private int m;
	}
	
	int m;
	int[] t0, t1;
	UniversalHash uh,ah;

	// Erzeugt eine Hashtabelle der Größe m.
	public Cuckoo(int m) {
		this.m = m;
		t0 = new int[m/2];
		t1 = new int[m/2];
		for(int i = 0; i < m/2; i++) {
			t0[i] = -1;
			t1[i] = -1;
		}
		uh = new UniversalHash(m/2);
		ah = new UniversalHash(m/2);
	}

	// Testet, ob ein gegebener Schlüssel in der Hashtabelle ist.
	public boolean find(int key) {
		return t0[uh.compute(key)] == key || t1[ah.compute(key)] == key ;
	}

	public void insert(int key) {
		int i;
		for(i = 1; i <= m/2; i++) {
			if(t0[uh.compute(key)] == -1) {t0[uh.compute(key)] = key; return;}
			if(t1[ah.compute(key)] == -1) {t1[ah.compute(key)] = key; return;}
			if(i%2 == 0) {
				//1
				int newkey = t1[ah.compute(key)];
				t1[ah.compute(key)] = key;
				key = newkey;
			}else {
				//0
				int newkey = t0[uh.compute(key)];
				t0[uh.compute(key)] = key;
				key = newkey;	
			}
			
		}
		if(i > m/2) rehash();	
	}

	public void remove(int key) {
		if(t0[uh.compute(key)] == key ) t0[uh.compute(key)] = -1;
		if(t1[ah.compute(key)] == key ) t1[uh.compute(key)] = -1;
	}

	private void rehash() {
		uh = new UniversalHash(m/2);
		ah = new UniversalHash(m/2);
		int[] t0clone = t0.clone();
		int[] t1clone = t1.clone();
		t0 = new int[m/2];
		t1 = new int[m/2];
		for(int i = 0; i < m/2; i++) {
			t0[i] = -1;
			t1[i] = -1;
		}
		for(int i = 0; i < m/2;i++) {
			insert(t0clone[i]);
			insert(t1clone[i]);
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
			}else if(!shadow[i] && ht.find(i)) {
				System.out.println(i + " is in hash table but should NOT be");
				errors++;
			}
		}

		System.out.println("There were " + errors + " errors");
	}
}

