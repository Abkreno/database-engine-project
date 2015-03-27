/*
EduDB is made available under the OSI-approved MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package libs.data_structures.linearHashTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LinearHashTable implements Map<Integer, String>, Serializable {

	private float loadFactor;
	private int bucketSize;
	private int size;
	private int digits;
	private int hashSeed;
	private int numberOfItems;
	private ArrayList<Bucket> buckets;

	public LinearHashTable(float loadFactor, int bucketSize) {
		this.loadFactor = loadFactor;
		this.bucketSize = bucketSize;
		buckets = new ArrayList<Bucket>();
		init();
	}

	private void init() {
		size = 0;
		digits = 1;
		Bucket bucket = new Bucket(bucketSize);
		buckets.add(bucket);
		Random generator = new Random();
		hashSeed = generator.nextInt();
	}

	@Override
	public int size() {
		return numberOfItems;
	}

	@Override
	public boolean isEmpty() {
		return numberOfItems == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	private LHTEntry getEntry(Object key) {
		if (key instanceof Object) {
			int b = getBucket((Object) key);
			Bucket bucket = buckets.get(b);
			LHTEntry entry;
			entry = bucket.getEntry(key);
			return entry;
		}
		return null;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String get(Object key) {
		LHTEntry entry = getEntry(key);
		return null == entry ? null : entry.getValue().toString();
	}

	public int getBucket(Object key) {
		int hash = hash(key);
		int bits = hash & ((int) Math.pow(2, digits) - 1);
		if (bits <= size) {
			return bits;
		} else {
			bits = bits - (int) Math.pow(2, (digits - 1));
			return bits;
		}
	}

	@Override
	public String put(Integer key, String value) {
		int b = getBucket(key);
		Bucket bucket = buckets.get(b);
		int hash = hash(key);
		bucket.put(key, value, hash);
		numberOfItems++;
		if (numberOfItems / ((size + 1) * bucketSize) >= loadFactor) {
			resize();
		}
		return null;
	}

	private void resize() {
		size++;
		Bucket b = new Bucket(bucketSize);
		buckets.add(b);
		if (size == (int) Math.pow(2, digits)) {
			digits++;
		}
		int index = size - (int) Math.pow(2, digits - 1);
		Bucket bucket = buckets.get(index);
		bucket.scan();
	}

	public void downSize() {

	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	final int hash(Object k) {
		int h = hashSeed;
		h ^= k.hashCode();
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	@Override
	public String remove(Object key) {
		int b = getBucket((Object) key);
		Bucket bucket = buckets.get(b);
		LHTEntry entry = bucket.remove((Object) key);
		numberOfItems--;
		return entry.value.toString();
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends String> m) {
		// TODO Auto-generated method stub
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}

	@Override
	public Set<Integer> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<Integer, String>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	class Bucket implements Serializable {
		LHTEntry[] entries;
		int lastItem;
		LinkedList<LHTEntry> overflow;

		public Bucket(int bucketSize) {
			entries = new LHTEntry[bucketSize];
			lastItem = 0;
		}

		public LHTEntry remove(Object key) {
			LHTEntry r = null;
			for (int i = 0; i < lastItem; i++) {
				if (entries[i].getKey().equals(key)) {
					r = entries[i];
					for (int j = i; j < lastItem - 1; j++) {
						entries[j] = entries[j + 1];
					}
					if (overflow != null) {
						if (!overflow.isEmpty()) {
							LHTEntry entry = overflow.removeFirst();
							entries[entries.length - 1] = entry;
							lastItem++;
						}
					}
					lastItem--;
					return r;
				}
			}
			if (overflow != null) {
				Iterator<LHTEntry> itr = overflow.iterator();
				while (itr.hasNext()) {
					LHTEntry element = itr.next();
					if ((element.getKey()).equals(key)) {
						r = element;
						itr.remove();
						break;
					}
				}
			}
			return r;
		}

		public LHTEntry getEntry(Object key) {
			for (int i = 0; i < lastItem; i++) {
				Object dataKey = (Object) key;
				if (entries[i].getKey().equals(dataKey)) {
					return entries[i];
				}
			}
			return null;
		}

		public void put(int key, String value, int hash) {
			if (lastItem == entries.length) {
				overflow.add(new LHTEntry(key, value, hash));
			} else {
				entries[lastItem++] = new LHTEntry(key, value, hash);
				if (lastItem == entries.length) {
					overflow = new LinkedList<LHTEntry>();
				}
			}
		}

		public void scan() {
			for (int i = 0; i < lastItem; i++) {
				int bits = entries[i].hash & ((int) Math.pow(2, digits) - 1);
				if (bits > (int) Math.pow(2, digits - 1) - 1) {
					LHTEntry entry = entries[i];
					remove(entries[i].key);
					numberOfItems--;
					LinearHashTable.this.put(entry.getKey(), entry.getValue());
					i--;
				}
			}
			if (overflow != null) {
				Iterator<LHTEntry> itr = overflow.iterator();
				while (itr.hasNext()) {
					LHTEntry element;
					element = itr.next();
					int bits = element.hash & ((int) Math.pow(2, digits) - 1);
					if (bits > (int) Math.pow(2, digits - 1) - 1) {
						itr.remove();
						numberOfItems--;
						LinearHashTable.this.put(element.getKey(),
								element.getValue());
					}
				}
			}
		}
	}

	class LHTEntry implements Entry<Integer, String>, Serializable {
		private int key;
		private String value;
		private int hash;

		public LHTEntry(int key, String value, int hash) {
			this.key = key;
			this.value = value;
			this.hash = hash;
		}

		public String getValue() {
			return value;
		}

		@Override
		public Integer getKey() {
			return key;
		}

		@Override
		public String setValue(String value) {
			String old = this.value;
			this.value = value;
			return old;
		}
	}

}
