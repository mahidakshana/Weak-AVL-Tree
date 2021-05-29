import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Map<V> {

	public Map() {
		// write your code here

	}

	public static void main(String[] args) {
		Map<Integer> m = new Map<Integer>();
		m.eval("map_input", "map_output");
	}

	public void eval(String inputFileName, String outputFileName) {
		// write your code here
		FileReader reader = null;
		try {
			reader = new FileReader(inputFileName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedReader bufferedReader = new BufferedReader(reader);

		long wat1 = 0, wat2 = 0, hmt1 = 0, hmt2 = 0, start, end;

		String line1 = null;
		try {
			line1 = bufferedReader.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int n = Integer.parseInt(line1);

		WeakAVLMap<String, V> m = new WeakAVLMap<String, V>();
		HashMap<V> h = new HashMap<V>(n);
		String line;
		try {
			while ((line = bufferedReader.readLine()) != null) {

				String s = line;
				String[] arr = s.split(" ");
				if (arr.length == 3) {
					String k = arr[1].substring(0, arr[1].length() - 1);
					V v = (V) arr[2];
					start = System.currentTimeMillis();
					h.put(k, v);
					end = System.currentTimeMillis();
					wat1 += (end - start);
					start = System.currentTimeMillis();
					m.put(k, v);
					end = System.currentTimeMillis();
					hmt1 += (end - start);
				} else {
					String k = arr[1];
					start = System.currentTimeMillis();
					h.remove(k);
					end = System.currentTimeMillis();
					wat2 += (end - start);
					start = System.currentTimeMillis();
					m.remove(k);
					end = System.currentTimeMillis();
					hmt2 += (end - start);

				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			reader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			FileWriter writer = new FileWriter(outputFileName);
			writer.write("Operations WAVL HashMap");
			writer.write("\r\n");
			writer.write("Insertions " + wat1 + " " + hmt1);
			writer.write("\r\n");
			writer.write("Deletions " + wat2 + " " + hmt2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
