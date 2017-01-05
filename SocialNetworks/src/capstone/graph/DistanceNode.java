package capstone.graph;


public class DistanceNode implements Comparable<DistanceNode> {
	
	private Integer val;
	//private List<Boolean> feat;
	// this is for using in Dijkstra algorithm 
	private Integer dist = Integer.MAX_VALUE;
	/*
	public CapNode (Integer nodeVal, Integer featSize){
		val = nodeVal;
		initFeatVector(featSize);
	}
	*/	
	public DistanceNode(Integer val, Integer dist) {
		super();
		this.val = val;
		this.dist = dist;
	}
	
	public Integer getVal() {
		return val;
	}
	public void setVal(Integer val) {
		this.val = val;
	}
//	public List<Boolean> getFeat() {
//		return feat;
//	}
//	public void setFeat(List<Boolean> feat) {
//		this.feat = feat;
//	}
	
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
		DistanceNode other = (DistanceNode) obj;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}

	@Override
	public int compareTo(DistanceNode o) {
		return Integer.compare(dist,o.getDist());
	}


	
}