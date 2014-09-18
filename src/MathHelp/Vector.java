package MathHelp;



public class Vector {

	//Attributes
	private int numComponents;
	private double[] components;
	
	public Vector(int nComps) {
		//Set attributes
		numComponents = nComps;
		//Initialize array
		components = new double[numComponents];
		
	}
	
	//Builds a vector as a copy of another
	public Vector(Vector v){
		numComponents = v.numComponents;
		
		components = new double[numComponents];
		
		for(int i = 0; i < numComponents; i++){
			components[i] = v.components[i];
		}
	}
	
	//Accessors
	public double getComponent(int index){
		return components[index];
	}
	
	public void setComponent(int index, double val){
		components[index] = val;
	}
	
	
	//Methods
	public double getMag(){
		double sumSq = 0;
		for(int i = 0; i < numComponents; i++){
			sumSq += Math.pow(components[i], 2);
		}
		return Math.sqrt(sumSq);
	}
	
	//Set the magnitude of vector while maintaining direction
	public void setMag(double newMag){
		normalize();
		
		//Multiply each component by the new mag
		for(int i = 0; i < numComponents; i++){
			components[i] *= newMag;
		}
	}
	
	//Returns a new vector as a scaled version of another vector
	public static Vector setMag(Vector v, double newMag){
		Vector returnVector = normalize(v);
		returnVector.setMag(newMag);
		
		return returnVector;
	}
	
	//Turns this vector into a unit vector
	public void normalize(){
		double mag = getMag();
		
		for(int i = 0; i < numComponents; i++){
			components[i] /= mag;
		}
	}
	
	//Returns a new copy of a vector as a unit vector 
	public static Vector normalize(Vector v){
		Vector returnVector = new Vector(v);
		returnVector.normalize();
		return returnVector;
	}
	
	//Increments this vector by another vector
	public void add(Vector v){
		for(int i = 0; i < numComponents; i++){
			components[i] += v.components[i];
		}
	}
	
	//Returns the sum vector of two vectors
	public static Vector add(Vector v1, Vector v2){
		Vector sum = new Vector(v1.numComponents);
		
		for(int i = 0; i < v1.numComponents; i++){
			sum.components[i] = v1.components[i] + v2.components[i];
		}
		
		return sum;
	}
	
	//Decrements this vector by another vector
	public void subtract(Vector v){
		for(int i = 0; i < numComponents; i++){
			components[i] -= v.components[i];
		}
	}
	
	//Returns the difference vector of the two vectors
	public static Vector subtract(Vector v1, Vector v2){
		Vector difference = new Vector(v1.numComponents);
		
		for(int i = 0; i < v1.numComponents; i++){
			difference.components[i] = v1.components[i] - v2.components[i];
		}
		
		return difference;
	}
	
	//Dots this vector with another vector
	public double dot(Vector v){
		double prod = 0.0;
		
		for(int i = 0; i < numComponents; i++){
			prod += components[i] * v.components[i];
		}
		
		return prod;
	}
	
	//Dots two vectors
	public static double dot(Vector v1, Vector v2){
		double prod = 0.0;
		
		for(int i = 0; i < v1.numComponents; i++){
			prod += v1.components[i] * v2.components[i];
		}
		
		return prod;
	}

}
