
public class MetricNCOM implements iMetrics{
	
	Boolean inComment = false;
	
	public MetricNCOM(){
		 
	}
	
	public Double nextLine(String line){
		if (line.trim().startsWith("/*")){
			inComment = true;
		}
			
		if (inComment == true && line.trim().startsWith("*/")){
			inComment = false;
			return 1.0;
		}
		
		if (line.trim().startsWith("//"))
			return 1.0;
			
		return 0.0;
	}

	@Override
	public void resetAllValues() {
		inComment = false;
		
	}

}
