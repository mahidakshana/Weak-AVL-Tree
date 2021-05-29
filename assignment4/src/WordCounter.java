
public class WordCounter {

	public WordCounter() {
		// write your code here
	}

	public int count(String str, String word) {
		// write your code here
		HashMap<Integer> h = new HashMap<Integer>(str.length());
		int n = word.length();
		for (int i = 0; i <= str.length() - n; i++) {
			String x = str.substring(i, i + n);

			if (h.contains(x)) {
				int num = h.get(x);
				h.put(x, num + 1);
			} else {
				h.put(x, 1);
			}
		}
		if (h.get(word) == null)
			return 0;

		return h.get(word);

	}

	public static void main(String[] args) {
		WordCounter w = new WordCounter();
		int p = w.count("eeeeg", "egf");

		System.out.println(p);
	}
}