package blatt_09_aufg_02;
import java.util.Random;

public class Btree2 {
	// Man beachte, dass wir Platz für 4 Schlüssel (bzw. 5 Links) reservieren,
	// obwohl jeder Knoten nur 3 Schlüssel (bzw. 4 Links) enthalten darf.
	// Sie können den zusätzlichen Platz nutzen, um temporär einen Schlüssel
	// "zu viel" in den Knoten einzufügen, bevor er geteilt wird.
	private static class Node {
		Node parent; // Vaterknoten.
		int[] keys = new int[4];
		Node[] links = new Node[5];
		boolean isLeaf; // Gibt an, ob der Knoten ein Blatt ist.
		int count; // Aktuelle Anzahl Schlüssel (nicht Links!).
	}

	// Überprüft, ob ein gegebener Schlüssel im Baum enthalten ist.
	public boolean contains(int key) {
		if(root == null)
			return false;

		Node nd = root;
		while(true) {
			int i;
			for(i = 0; i < nd.count; i++) {
				if(nd.keys[i] == key)
					return true;
				if(nd.keys[i] > key)
					break;
			}

			if(nd.isLeaf)
				return false;
			nd = nd.links[i];
		}
	}

	public void insert(int key) {
		// Implementieren Sie hier den Algorithmus zum Einfügen.
		// Beachten Sie, dass Sie ggf. eine neue Wurzel (Variable root) erzeugen
		// müssen und dass root auf null initialisiert wird.
		
		//tree empty
		if(root == null) {root = new Node(); root.keys[0] = key;root.count++;root.isLeaf=true;return;}
		
		//search insert node
		Node insert = root;
		while(true) {
			int i;
			for(i = 0; i < insert.count; i++) {				
				if(insert.keys[i] > key)
					break;
			}

			if(insert.isLeaf) break;
			insert = insert.links[i];
		}
		
		//insert in node
		int insertPos = 0;
		for(insertPos = 0; insertPos < insert.keys.length;insertPos++) {
			if(insert.keys[insertPos] >= key) break;
		}
		for(int j = insert.keys.length-2 ;j >= insertPos ;insertPos++) {
			insert.keys[j] = insert.keys[j+1];
		}
		insert.keys[insertPos] = key;
		insert.count++;
		
		//check if insert possible
		if(insert.count <= 3) return;
		
		//node full, split nodes
		Node bro = new Node();bro.isLeaf=true;bro.parent=insert.parent;
		int medianIndex = insertPos <= 1 ? 2:1;
		int median = insert.keys[medianIndex];insert.keys[medianIndex] = 0;insert.count--;
		for(int j = medianIndex+1,k=0; j < insert.keys.length; j++,k++) {
			bro.keys[k] = insert.keys[j];
			insert.keys[j] = 0;
			bro.count++;
			insert.count--;
		}
		
		//insert median in father
		Node father = insert.parent;
		boolean deficient = true;
		while(deficient) {
			int insPosMed = 0;
			for(insPosMed = 0; insPosMed < father.keys.length; insPosMed++) {
				
			}
		
		}
		
	}

	public static void main(String[] args) {
		Random rnd = new Random();
		boolean[] set = new boolean[10000];
		int[] list = new int[1000];
		int n = 0;
		while(n < list.length) {
			int k = rnd.nextInt(set.length);
			if(set[k])
				continue;
			list[n++] = k;
			set[k] = true;
		}

		Btree2 tree = new Btree2();
		for(int i = 0; i < list.length; i++) {
			System.out.println("Inserting " + list[i]);
			tree.insert(list[i]);
		}
		
		int errors = 0;
		for(int i = 0; i < set.length; i++) {
			boolean found = tree.contains(i);
			if(set[i] && !found) {
				System.out.println("Tree does not contain " + i + " even though it should");
				errors++;
			}
			if(!set[i] && found) {
				System.out.println("Tree contains " + i + " even though it should not");
				errors++;
			}
		}
		System.out.println("There were " + errors + " errors");
	}

	private Node root;
}

