package blatt_11_aufg_02;

import java.util.HashSet;

public class Mst {
	public static class Edge {
		Edge(Node t, int w) {
			target = t;
			weight = w;
		}

		Node target;
		int weight;
	}

	public static class Node {
		Node(int n) {
			name = n;
			dist = Integer.MAX_VALUE;
		}

		int name;
		Edge[] edges;
		int dist;
		boolean finished = false;
	}

	public static class DistPrioQueue {
		void insert(Node x) {
			elements.add(x);
		}

		boolean isEmpty() {
			return elements.isEmpty();
		}

		Node extractMin() {
			Node m = null;
			for(Node x : elements) {
				if(m == null || x.dist < m.dist)
					m = x;
			}
			if(m == null)
				throw new IllegalStateException("The priority queue is empty");
			elements.remove(m);
			return m;
		}

		void decreaseKey(Node x, int val) {
			elements.remove(x);
			x.dist = val;
			elements.add(x);
			
		}

		private HashSet<Node> elements = new HashSet<Node>();
	}

	public static void jarnikPrim(Node[] V) {
		// Implementieren Sie diese Methode. V ist dabei die Knotenmenge des Graphen.
		// Geben Sie als Ergebnis alle Kanten des MSTs auf die Konsole aus.
		// Nutzen Sie die Eigenschaft 'name' um die Knoten vernünftig auszugeben.
		DistPrioQueue q = new DistPrioQueue();
		int[] father = new int[V.length];
		father[0] = 0;
		V[0].dist = 0;
		V[0].finished = true;
		for(int i =0; i < V.length; i++) {
			q.insert(V[i]);
		}
		
		
		
		while(!q.isEmpty()) {
			Node u = q.extractMin();
			u.finished = true;
			//geh alle Edges von u durch
			for(int i = 0; i < V[u.name-1].edges.length; i++) {
				//current edge
				Edge v = V[u.name-1].edges[i];
				
				//aktualisiere Distanz zum Node, wenn edge weight < distance
				if(!v.target.finished && v.weight < v.target.dist){
				father[v.target.name-1] = u.name-1;
				q.decreaseKey(v.target, v.weight);
				//v.target.dist = v.weight;
				}
				
			}
		}
		
		for(int i = 1; i < father.length;i++) {
			System.out.println("( " + (father[i]+1) + " ) ----- ( " + (i+1) + " )");
		}
			

	}

	public static void main(String[] args) {
		Node[] V = new Node[] {
			new Node(1),
			new Node(2),
			new Node(3),
			new Node(4),
			new Node(5),
			new Node(6),
			new Node(7),
			new Node(8),
			new Node(9),
			new Node(10)
		};
		V[0].edges = new Edge[]{new Edge(V[3], 9), new Edge(V[5], 11)};
		V[1].edges = new Edge[]{new Edge(V[0], 12)};
		V[2].edges = new Edge[]{new Edge(V[3], 13)};
		V[3].edges = new Edge[]{new Edge(V[1], 1), new Edge(V[7], 11)};
		V[4].edges = new Edge[]{new Edge(V[2], 4), new Edge(V[3], 6)};
		V[5].edges = new Edge[]{new Edge(V[4], 10), new Edge(V[6], 2)};
		V[6].edges = new Edge[]{new Edge(V[7], 7)};
		V[7].edges = new Edge[]{new Edge(V[9], 12)};
		V[8].edges = new Edge[]{new Edge(V[7], 8)};
		V[9].edges = new Edge[]{new Edge(V[8], 5)};
		jarnikPrim(V);
	}
}

