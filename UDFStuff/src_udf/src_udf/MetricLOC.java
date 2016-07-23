public class MetricLOC implements iMetrics{
	
	Boolean inComment = false;
	
	public MetricLOC(){
		 
	}
	
	public Double nextLine(String line){
		if (line.trim().startsWith("/*")){
			inComment = true;
			return 0.0;
		}
		if (inComment == true && line.trim().startsWith("*"))
			return 0.0;
	
		if (inComment == true && line.trim().startsWith("*/")){
			inComment = false;
			return 0.0;
		}
		
		if (line.trim().startsWith("//"))
			return 0.0;
			
		if(line.trim().length() > 1)
         	return 1.0;
		else
			return 0.0;
	}

	@Override
	public void resetAllValues() {
		inComment = false;
		
	}

}
