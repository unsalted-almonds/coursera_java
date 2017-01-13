package capstone.graph;

public class DistanceNode<V> implements Comparable<DistanceNode<V>> {

	private V val;

	// this is for using in Dijkstra algorithm
	private Integer dist = Integer.MAX_VALUE;

	public DistanceNode(V val, Integer dist) {
		super();
		this.val = val;
		this.dist = dist;
	}

	public V getVal() {
		return val;
	}

	public void setVal(V val) {
		this.val = val;
	}

	public Integer getDist() {
		return dist;
	}

	public void setDist(Integer dist) {
		this.dist = dist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((val == null) ? 0 : val.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		DistanceNode<V> other = (DistanceNode<V>) obj;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}

	@Override
	public int compareTo(DistanceNode<V> o) {
		return Integer.compare(dist, o.getDist());
	}

}