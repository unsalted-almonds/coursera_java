package graph;

public class CapEdge {
	
	private Integer from;
	private Integer to;
	private Integer weight;
	private Integer traffic;
	
	public CapEdge(){}
	
	public CapEdge(Integer from, Integer to, Integer weight){
		this.from = from;
		this.to= to;
		this.weight = weight;
	}
		
	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getTraffic() {
		return traffic;
	}

	public void setTraffic(Integer traffic) {
		this.traffic = traffic;
	}



	
}
