package blatt_09_aufg_02;

import java.util.Random;

public class Btree {
	// Man beachte, dass wir Platz für 4 Schlüssel (bzw. 5 Links) reservieren,
	// obwohl jeder Knoten nur 3 Schlüssel (bzw. 4 Links) enthalten darf.
	// Sie können den zusätzlichen Platz nutzen, um temporär einen Schlüssel
	// "zu viel" in den Knoten einzufügen, bevor er geteilt wird.
	private static class Node {
		Node parent = null; // Vaterknoten.
		int[] keys = new int[4];
		Node[] links = new Node[5];
		boolean isLeaf = false; // Gibt an, ob der Knoten ein Blatt ist.
		int count=0; // Aktuelle Anzahl Schlüssel (nicht Links!).
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
		if(root == null) {root = new Node();
		root.count=1;
		root.keys[0] = key;
		root.isLeaf = true;
		return;}
		
		//find insert node;
		Node current = root;
		while(true) {
			int i = 0;
			for(i = 0; i <= current.count-1;i++) {
				if(current.keys[i] > key) break;
			}
			if(current.isLeaf)break;
			current = current.links[i];
			
		}
		
		//current = leaf to be inserted in
		Node bro = null, child = null;
		while(true) {
			
			//new root
			if(current == null) {
				root = new Node();
				bro.parent= root;
				child.parent = root;
				root.keys[0]= key;
				root.count++;
				root.links[0] = child;
				root.links[1] = bro;
				//update parent pointer of bro's childs to "bro"
				if(bro.isLeaf==false)
				{for(int i = 0; i <= bro.count;i++) {
					bro.links[i].parent = bro;
				}
					}
				return;
			}
			
			//find pos to insert
			int insertPos;
			for(insertPos = 0; insertPos <= current.count-1;insertPos++) {
				if(current.keys[insertPos] > key) break;
			}
			int median = current.keys[1];
			
			//verschiebe keys und links nach rechts
			for(int i = current.keys.length-2; i >= insertPos; i--) {
				current.keys[i+1] = current.keys[i];
			}
			for(int i = current.links.length-2; i > insertPos; i--) {
				current.links[i+1] = current.links[i];
			}
			
			//insert key and link
			current.keys[insertPos] = key;
			current.count++;
			
			if (bro != null) {
				current.links[insertPos+1] = bro;
				
				//update parent for all nodes of bro to "bro"
				if(bro.isLeaf == false) {
					for(int i = 0; i <= bro.count;i++) {
					bro.links[i].parent = bro;
					}
				}
			}

			
			
			
			//check if node overflows
			if(current.count <= 3) return;
			
			//node overflows
			bro = new Node();bro.parent = current.parent;
			
			//extract median
			int medianIndex = median > key ? 2 : 1;
			key = median;
			
			//move keys to bro
			current.keys[medianIndex] = 0; current.count--;
			for(int i = medianIndex+1; i < current.keys.length;i++) {
				bro.keys[i-(medianIndex+1)] = current.keys[i];
				current.keys[i] = 0;
				current.count--;bro.count++;
				
			}
			
			//move links to bro
			for(int i = medianIndex+1; i < current.links.length; i++) {
				bro.links[i-(medianIndex+1)] = current.links[i];
				current.links[i] = null;
			}
			
			if(bro.links[0] == null) {bro.isLeaf = true;}
			child = current;
			current = current.parent;
			
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

		Btree tree = new Btree();
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

