package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CapNode {
	
	private Integer val;
	private List<Boolean> feat;
	
	public CapNode (Integer nodeVal, Integer featSize){
		val = nodeVal;
		initFeatVector(featSize);
	}
	
	//init vector to the actual size and set it to all false;
	private void initFeatVector(Integer featSize){
		feat = new ArrayList<Boolean>(Collections.nCopies(featSize, false));		
	}
	
	public Integer getVal() {
		return val;
	}
	public void setVal(Integer val) {
		this.val = val;
	}
	public List<Boolean> getFeat() {
		return feat;
	}
	public void setFeat(List<Boolean> feat) {
		this.feat = feat;
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
		CapNode other = (CapNode) obj;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}
	
}
