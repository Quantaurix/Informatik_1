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

	public void insert2(int key) {
		// Implementieren Sie hier den Algorithmus zum Einfügen.
		// Beachten Sie, dass Sie ggf. eine neue Wurzel (Variable root) erzeugen
		// müssen und dass root auf null initialisiert wird.
		
		//tree empty
		if(root == null) {root = new Node(); root.keys[0] = key;root.count = 1;root.isLeaf=true;return;}
		
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
		Node bro = null,bak=null;
		boolean insertNode = true;
		while(true) {
			//create new root
			if(insert == null) {
				Node newRoot = new Node(); root = newRoot;root.count = 1;root.isLeaf = false;
				root.keys[0] = key;
				root.links[0] = bak;
				root.links[1] = bro;
				bak.parent = root;
				bro.parent = root;
				return;
			}
			
		//find insert pos
		int insertPos = 0;
		for(insertPos = 0; insertPos < insert.count;insertPos++) {
			if(insert.keys[insertPos] > key) break;
		}
		//move keys and childs to right
		for(int j = insert.count-1 ;j >= insertPos ;j--) {
			insert.keys[j+1] = insert.keys[j];
			insert.links[j+2] = insert.links[j+1];

		}
		insert.keys[insertPos] = key;
		if(bro != null) insert.links[insertPos+1] = bro;
		insert.count++;
		
		//check if insert possible
		if(insert.count <= 3) return;
		
		//node full, split nodes
		bro = new Node();bro.isLeaf=true;bro.parent=insert.parent;bro.count=0;
		int medianIndex = insertPos <= 1 ? 2:1;
		int median = insert.keys[medianIndex];insert.keys[medianIndex] = 0;insert.count--;
		for(int j = medianIndex+1,k=0; j < insert.keys.length; j++,k++) {
			bro.keys[k] = insert.keys[j];
			insert.keys[j] = 0;
			
			bro.count++;
			insert.count--;
		}
		for(int j = medianIndex+1,k=0; j < insert.links.length; j++,k++) {
			
			
			bro.links[k] = insert.links[j];
			insert.links[j] = null;
			
		}
		bak = insert;
		insert = insert.parent;
		key = median;
		/*for(int i = 0; i < bro.links.length;i++) {
			if(bro.links[i] != null) {bro.isLeaf = false;break;}
		}*/
		//if(insertNode) {bro.isLeaf = true;}
		
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

