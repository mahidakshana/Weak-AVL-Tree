import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class WeakAVLMap<K extends Comparable, V> {
	Node root;
	int rotation = 0;

	// node class
	public class Node {
		K key;
		V value;
		Node left, right, parent;
		int rank = 0;

		Node(K key, V value) {
			this.key = key;
			this.value = value;
			this.left = null;
			this.right = null;
			parent = null;
		}
	}

	public WeakAVLMap() {
		// write your code here

	}

	// insert particular key and value in WAVL
	public V put(K key, V value) {
		// write your code her
		Node n = new Node(key, value);
		if (root == null) {
			root = n;
			root.rank = 1;
		} else {
			Node curr = root;
			Node curr_par = null;
			while (curr != null) {
				curr_par = curr;
				if (key.compareTo(curr.key) == 0) {
					V val1 = curr.value;
					curr.value = value;
					return val1;
				} else if (key.compareTo(curr.key) < 0)
					curr = curr.left;
				else
					curr = curr.right;
			}

			if (key.compareTo(curr_par.key) < 0)
				curr_par.left = n;
			else
				curr_par.right = n;

			n.parent = curr_par;
			n.rank = 1;
		}

		if (rankdiff(n) == 0)
			balance(n);
		return null;
	}
	// remove particular key from WAVL

	public V remove(K key) {
		Node curr = root;
		// v store node which have to delete
		while (curr != null && !curr.key.equals(key)) // search node which have to delete
		{
			if (key.compareTo(curr.key) < 0)
				curr = curr.left;
			else
				curr = curr.right;
		}

		if (curr == null)
			return null;// if node doesnt exist then return null
		V ret = curr.value;
		delete(curr);
		return ret;
	}

	public void delete(Node v)
	// if node has both child not null then find inorder predecessor
	{
		if (v.left != null && v.right != null) {
			Node n1 = v.right;
			while (n1.left != null) {
				n1 = n1.left;
			}

			v.key = n1.key;
			v.value = n1.value;
			v = n1;
		}
		Node p = v.parent;
		Node q = (v.left != null ? v.left : v.right);
		Node s = null;
		int q_rn = 0;
		int s_rn = 0;
		if (q != null) {
			if (p == null) {
				root = q;
				return;
			} else if (v == p.left) {
				p.left = q;
				q.parent = p;
				s = p.right;
			} else {
				p.right = q;
				q.parent = p;
				s = p.left;
			}
			q_rn = rankdiff(q);
			if (s == null)
				s_rn = p.rank;
			else
				s_rn = rankdiff(s);
		} else {

			if (p == null) {
				root = q;
				return;
			} else if (v == p.left) {
				p.left = null;
				s = p.right;
			} else {
				p.right = null;
				s = p.left;
			}
			q_rn = rank(p);
			if (s == null)
				s_rn = p.rank;
			else
				s_rn = rankdiff(s);

		}

		if (q_rn == 2 && q == null && s == null) {
			p.rank--;
			if (p != root) {
				if (p.parent.left == p)
					s = p.parent.right;
				else
					s = p.parent.left;
				if (s == null)
					s_rn = rank(p);
				else
					s_rn = rankdiff(s);
				restructure1(p, p.parent, s, rankdiff(p), s_rn);
			}
		}

		else
			restructure1(q, p, s, q_rn, s_rn);
	}

	// return value corresponding to given key
	public V get(K key) {
		// write your code her
		Node n = root;
		while (n != null && n.key.compareTo(key) != 0) {
			if (n.key.compareTo(key) < 0)
				n = n.right;
			else
				n = n.left;
		}
		if (n == null)
			return null;
		else
			return n.value;
	}

	// return vector of elements between given search range
	public Vector<V> searchRange(K key1, K key2) {
		// write your code her
		Vector<V> v = new Vector<V>();
		rangeq(key1, key2, root, v);

		return v;

	}

	// gives total no of rotation at an instant
	public int rotateCount() {
		// write your code her
		return rotation;
	}

	// getheight of WAVL tree
	public int getHeight() {

		return height(root);
	}

	public int max(int a, int b) {
		if (a >= b)
			return a;
		else
			return b;
	}

	public int height(Node n) {
		if (n == null)
			return 0;

		return max(height(n.left), height(n.right)) + 1;

	}

	// BFS traversal
	public Vector<K> BFS() {
		// write your code her
		Vector<K> v = new Vector<>();
		if (root == null)
			return v;
		Queue<Node> q = new LinkedList<Node>();
		q.add(root);
		while (!q.isEmpty()) {
			Node temp = q.poll();
			v.add(temp.key);
			if (temp.left != null)
				q.add(temp.left);
			if (temp.right != null)
				q.add(temp.right);
		}

		return v;

	}

	public int rank(Node n) {
		if (n == null)
			return 0;

		return n.rank;
	}

	public Node sibling(Node n) {
		Node n1 = n.parent;
		if (n1.left == n)
			return n1.right;
		else
			return n1.left;
	}

	public int rankdiff(Node n) {
		return rank(n.parent) - rank(n);
	}

//balance after insertion
	public void balance(Node n) {
		if (n == root || rankdiff(n) == 1) {
			return;
		}

		if (rankdiff(n) == 0) {
			int num = n.parent.rank;
			if (sibling(n) != null)
				num = rankdiff(sibling(n));
			if (num == 1) {
				// System.out.println("k"+n.key);
				n.parent.rank++;
				balance(n.parent);
			} else if (num == 2) { // System.out.println("key"+n.key);
				Node t = null;
				if (n.left != null && rankdiff(n.left) == 1)
					t = n.left;
				else
					t = n.right;
				Node p = n.parent;
				int r = p.rank;
				Node z = restructure(t);
				z.rank = r;
				z.left.rank = r - 1;
				z.right.rank = r - 1;
			}
		}
	}

	// restructure after insertion
	public Node restructure(Node x) {
		Node y = x.parent;
		Node z = y.parent;
		boolean check1 = false;
		if (z == root)
			check1 = true;
		Node p1 = z.parent;
		boolean check = true;
		if (p1 != null && p1.right == z) {
			check = false;
		}
		Node num;
		if (z.left == y) {

			if (y.left == x) {
				num = rr(z);
			} else {
				Node p = lr(z.left);
				z.left = p;
				p.parent = z;
				num = rr(z);
			}

		} else {

			if (y.right == x) {
				num = lr(z);
			} else {
				Node p = rr(z.right);
				z.right = p;
				p.parent = z;
				num = lr(z);
			}

		}

		if (check1) {
			root = num;
		} else {
			if (check) {
				p1.left = num;
				num.parent = p1;
			} else {
				p1.right = num;
				num.parent = p1;
			}
		}

		return num;

	}

	// left-left case
	public Node rr(Node n) {
		rotation++;
		Node x = n.left;
		Node y = x.right;

		x.right = n;
		n.parent = x;
		x.parent = null;
		n.left = y;
		if (y != null)
			y.parent = n;
		return x;
	}

	// right-right case
	public Node lr(Node n) {
		rotation++;
		Node x = n.right;
		Node y = x.left;

		x.left = n;
		n.parent = x;
		x.parent = null;
		n.right = y;
		if (y != null)
			y.parent = n;
		// System.out.println("yes"+x.right.key);
		return x;
	}

	// it return the node which we have to delete

	// restructure after deletion
	public void restructure1(Node q, Node p, Node s, int q_rn, int s_rn) {
		if (q_rn <= 2)
			return;

		// case 1- if sibling has rankdiff 2

		if (s_rn == 2) {
			p.rank--;
			if (p == root)
				return;

			if (p.parent.left == p)
				s = p.parent.right;
			else
				s = p.parent.left;
			if (s == null)
				s_rn = p.parent.rank;
			else
				s_rn = rankdiff(s);
			restructure1(p, p.parent, s, rankdiff(p), s_rn);
		}
		// case2-if sib has rankdiff 1

		else if (s_rn == 1) {
			Node n1 = s.left;
			Node n2 = s.right;
			int num1 = 0;
			int num2 = 0;
			if (n1 == null)
				num1 = rank(s);
			else
				num1 = rankdiff(n1);
			if (n2 == null)
				num2 = rank(s);
			else
				num2 = rankdiff(n2);

			// case2.1
			if (num1 == 2 && num2 == 2) {
				s.rank--;
				p.rank--;
				if (p == root)
					return;

				if (p.parent.left == p)
					s = p.parent.right;
				else
					s = p.parent.left;
				if (s == null)
					s_rn = p.parent.rank;
				else
					s_rn = rankdiff(s);
				restructure1(p, p.parent, s, rankdiff(p), s_rn);
			}

			// case2.2 restructure
			else if (num1 == 1 || num2 == 1) {
				Node t = null;
				if (num1 == 1 && num2 == 1) {
					if (p.left == s)
						t = n1;
					else
						t = n2;
				} else {
					if (num1 == 1)
						t = n1;
					else
						t = n2;
				}
				int r = p.rank;
				Node z = restructure(t);
				z.rank = r;
				if (z.left == t) {
					z.left.rank = r - 2;
					z.right.rank = r - 1;
				} else if (z.right == t) {
					z.left.rank = r - 1;
					z.right.rank = r - 2;
				} else {
					z.left.rank = r - 2;
					z.right.rank = r - 2;
				}

			}

		}
	}

	public void rangeq(K key1, K key2, Node n, Vector<V> v) {
		if (n == null)
			return;

		if ((key1.compareTo(n.key) <= 0) && (n.key.compareTo(key2) <= 0)) {

			rangeq(key1, key2, n.left, v);
			v.add(n.value);
			rangeq(key1, key2, n.right, v);

		} else if (n.key.compareTo(key1) <= 0) {
			rangeq(key1, key2, n.right, v);
		} else if (n.key.compareTo(key2) > 0) {
			rangeq(key1, key2, n.left, v);
		}
	}

	public static void main(String[] args) {
		WeakAVLMap<Integer, Integer> w = new WeakAVLMap<Integer, Integer>();

		System.out.println(w.put(2, 41));

		System.out.println(w.put(69, 72));
		System.out.println(w.put(83, 55));

		System.out.println(w.remove(83));
		// System.out.println(w.root.left.rank);

		System.out.println(w.put(5, 100));

		System.out.println(w.put(109, 14));

		System.out.println(w.put(3245, 1435));

		System.out.println(w.put(345, 643));

		System.out.println(w.put(13, 12));

		System.out.println(w.remove(2));

		System.out.println(w.remove(3245));

		System.out.println(w.remove(109));

		System.out.println(w.remove(13));

		System.out.println(w.remove(5));

		System.out.println(w.remove(96));

		System.out.println(w.remove(345));

		System.out.println(w.remove(69));
		System.out.println(w.put(2, 41));
		System.out.println(w.put(69, 72));
		System.out.println(w.put(83, 55));
		System.out.println(w.remove(83));
		System.out.println(w.put(5, 100));
		System.out.println(w.put(109, 14));
		System.out.println(w.put(3245, 1435));
		System.out.println(w.put(345, 643));
		System.out.println(w.put(13, 12));
		System.out.println(w.put(1, 1044));
		System.out.println(w.remove(3245));

		Vector<Integer> v = w.BFS();
		for (int i = 0; i < v.size(); i++) {
			System.out.print(v.elementAt(i) + " ");
		}
		System.out.println();
		System.out.println(w.getHeight());
		System.out.println(w.rotation);

		// System.out.println(w.root.rank);
	}

}
