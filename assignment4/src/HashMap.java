
import java.util.ArrayList;
import java.util.Vector;

public class HashMap<V> {
	int size;
	ArrayList<Node> arr;

	public HashMap(int size) {
		// write your code here
		this.size = size;
		arr = new ArrayList<Node>(size);
		for (int i = 0; i < size; i++) {
			arr.add(null);
		}
	}

	public class Node {
		String key;
		V value;

		Node(String key, V value) {
			this.key = key;
			this.value = value;

		}
	}

	public int hashfn(String key) {
		int num = key.length();
		int num1 = 0;
		for (int i = num - 1; i > 0; i--) {
			int p = num1 % size;
			int q = key.charAt(i) % size;
			int r = 41 % size;
			num1 = p + q;
			num1 *= r;
		}
		num1 += key.charAt(0);
		return num1;
	}

	public V put(String key, V value) {
		// write your code here
		Node n = new Node(key, value);
		int num = hashfn(key) % size;

		if (arr.get(num) == null) {
			arr.set(num, n);
			return null;
		} else {

			while (arr.get(num) != null) {
				if (arr.get(num).key.equals(key)) {
					V num1 = arr.get(num).value;
					arr.get(num).value = value;
					return num1;
				}
				num = (num + 1) % size;

			}

			arr.set(num, n);
			return null;
		}
	}

	public V get(String key) {
		int num = hashfn(key) % size;
		while (arr.get(num) != null) {
			if (arr.get(num).key.equals(key)) {
				return arr.get(num).value;
			} else {
				num = (num + 1) % size;
			}
		}
		return null;
	}

	public boolean remove(String key) {
		// write your code here
		int num = hashfn(key) % size;
		int check = num;
		if (arr.get(num) == null) {
			return false;
		} else {
			while (arr.get(num) != null) {
				if (arr.get(num).key.equals(key)) {
					arr.set(num, null);
					balance(num);
					return true;
				} else {
					num = (num + 1) % size;
					if (check == num)
						break;
				}
			}
			return false;
		}
	}

	public void balance(int num) {
		int x = 1;
		while (arr.get((num + x) % size) != null) {
			int check = hashfn(arr.get((num + x) % size).key) % size;
			if ((check > num + x) || (check <= num)) {
				arr.set(num, arr.get((num + x) % size));
				arr.set((num + x) % size, null);

				num = (num + x) % size;
				x = 1;
			} else
				x += 1;
		}
	}

	public boolean contains(String key) {
		// write your code here
		int num = hashfn(key) % size;
		// System.out.println("con"+num);
		// System.out.println(arr.size());
		while (arr.get(num) != null) {
			if (arr.get(num).key.equals(key))
				return true;
			num = (num + 1) % size;
		}
		return false;
	}

	public Vector<String> getKeysInOrder() {
		// write your code here
		Vector<String> s = new Vector<String>();
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i) != null)
				s.add(arr.get(i).key);
		}
		return s;
	}

	public static void main(String[] args) {
		HashMap<Integer> h = new HashMap<Integer>(5);
		System.out.println(h.hashfn("k") % 5);

		h.put("k", 7);
		// h.put("m", 6);
		// h.put("b", 0);
		h.put("a", 1);

		System.out.println(h.put("k", 1));
		System.out.println(h.get("k"));

		// System.out.println(h.remove("m"));
		// h.put("f", 1);
		// System.out.println(h.put("k", 5));

	}
}
