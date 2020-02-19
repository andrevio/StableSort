package com.stablesort.util;

/**
 * 
 * @author Andre Violentyev
 *
 * @param <TYPEA>
 * @param <TYPEB>
 */
public class ComparablePair<TYPEA extends Comparable<TYPEA>, TYPEB> implements Comparable<ComparablePair<TYPEA, TYPEB>> {
	protected final TYPEA a;
	protected final TYPEB b;

	public ComparablePair(TYPEA key, TYPEB value) {
		a = key;
		b = value;
	}
	
	public TYPEA getA() {
		return a;
	}
	
	public TYPEB getB() {
		return b;
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append('(');
		buff.append(a);
		buff.append(", ");
		buff.append(b);
		buff.append(')');
		return buff.toString();
	}

	@Override
	public boolean equals(Object o) { 
		if (o instanceof ComparablePair) { 
			ComparablePair<?, ?> pair = (ComparablePair<?, ?>) o;

			boolean sameA = 
				(pair.a != null && pair.a.equals(this.a)) ||
				(pair.a == null && this.a == null);
			
			boolean sameB = 
				(pair.b != null && pair.b.equals(this.b)) ||
				(pair.b == null && this.b == null);

			return sameA && sameB;
		}
		return false;
	}

	@Override
	public int hashCode() { 
		int hashCode = 
			(a == null ? 0 : a.hashCode()) + 
			(31 * (b == null ? 0 : b.hashCode()));
		return hashCode;
	}

	/**
	 * comparison is done by the A argument only
	 */
	@Override
	public int compareTo(ComparablePair<TYPEA, TYPEB> p) {
		return getA().compareTo(p.getA());
	}
}
