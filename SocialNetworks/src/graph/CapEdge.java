package graph;

public class CapEdge {
	
	private CapNode from;
	private CapNode to;
	private Integer weight;
	
	public CapEdge(){}
	
	public CapEdge(CapNode from, CapNode to, Integer weight){
		this.from = from;
		this.to= to;
		this.weight = weight;
	}
	
	public CapNode getFrom() {
		return from;
	}
	public void setFrom(CapNode from) {
		this.from = from;
	}
	public CapNode getTo() {
		return to;
	}
	public void setTo(CapNode to) {
		this.to = to;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		CapEdge other = (CapEdge) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	
}
