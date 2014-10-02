package MathHelp;



public class Vector {

	//Attributes
	private int numComponents;
	private double[] components;
	
	/**
	 * <p>Constructs a vector with a specified number of components.<br>
	 * All components will default to 0</p>
	 * @param nComps The number of components this vector should have
	 */
	public Vector(int nComps) {
		//Set attributes
		numComponents = nComps;
		//Initialize array
		components = new double[numComponents];
		
	}
	
	/**
	 * <p>Constructs a vector as a copy of another vector</p>
	 * @param v The vector you wish to copy
	 */
	public Vector(Vector v){
		numComponents = v.numComponents;
		
		components = new double[numComponents];
		
		for(int i = 0; i < numComponents; i++){
			components[i] = v.components[i];
		}
	}
	
	//Accessors
	/**
	 * Gets the component of this vector at a specified index
	 * @param index Index to retrieve
	 * @return A double containing the component at desired index.
	 */
	public double getComponent(int index){
		return components[index];
	}
	
	/**
	 * <p>Sets a specified component of this vector</p>
	 * @param index Index of component to set
	 * @param val Value to set to
	 */
	public void setComponent(int index, double val){
		components[index] = val;
	}
	
	
	//Methods
	/**
	 * Gets the magnitude of this vector
	 * @return Returns the magnitude of this vector
	 */
	public double getMag(){
		double sumSq = 0;
		for(int i = 0; i < numComponents; i++){
			sumSq += Math.pow(components[i], 2);
		}
		return Math.sqrt(sumSq);
	}
	
	/**
	 * Set the magnitude of this vector while maintaining direction.
	 * @param newMag Desired magnitude
	 */
	public void setMag(double newMag){
		normalize();
		
		//Multiply each component by the new mag
		for(int i = 0; i < numComponents; i++){
			components[i] *= newMag;
		}
	}
	
	/**
	 * Create a new vector with set magnitude in the direction of an existing vector
	 * @param v Vector with desired direction
	 * @param newMag Desired magnitude
	 * @return Returns a new vector in the direction of specified vector with a magnitude as specified.
	 */
	public static Vector setMag(Vector v, double newMag){
		Vector returnVector = normalize(v);
		returnVector.setMag(newMag);
		
		return returnVector;
	}
	
	/**
	 * Normalizes this vector.
	 * Maintains direction while giving this vector a magnitude of 1.
	 * Converts this vector to a unit vector.
	 * I'm not sure how else I can say this.
	 */
	public void normalize(){
		double mag = getMag();
		
		for(int i = 0; i < numComponents; i++){
			components[i] /= mag;
		}
	}
	
	/**
	 * <p>Creates a unit {@link Vec} in the direction of an  vector</p> 
	 * @param v Vector to normalize
	 * @return A vector with magnitude 1 in the direction of parameter (v)
	 */
	public static Vector normalize(Vector v){
		Vector returnVector = new Vector(v);
		returnVector.normalize();
		return returnVector;
	}
	
	/**
	 * <p>Increments this Vector by another Vector</p>
	 * @param v Vector to increment this vector by
	 */
	public void add(Vector v){
		for(int i = 0; i < numComponents; i++){
			components[i] += v.components[i];
		}
	}
	
	/**
	 * <p>Returns the sum of two vectors</p>
	 * @param v1 Initial vector
	 * @param v2 Vector to add to initial vector
	 * @return Vector containing the sum of the two parameters(v1 + v2)
	 */
	public static Vector add(Vector v1, Vector v2){
		Vector sum = new Vector(v1.numComponents);
		
		for(int i = 0; i < v1.numComponents; i++){
			sum.components[i] = v1.components[i] + v2.components[i];
		}
		
		return sum;
	}
	
	/**
	 * <p>Decrements this vector by vector v</p>
	 * @param v The vector to take away from this vector.
	 */
	public void subtract(Vector v){
		for(int i = 0; i < numComponents; i++){
			components[i] -= v.components[i];
		}
	}
	
	/**
	 * Subtracts Vector v2 from Vector v1
	 * @param v1 The initial vector
	 * @param v2 The vector to take away from v1
	 * @return A vector containing the difference of the two parameters (V1 - V2).
	 */
	public static Vector subtract(Vector v1, Vector v2){
		Vector difference = new Vector(v1.numComponents);
		
		for(int i = 0; i < v1.numComponents; i++){
			difference.components[i] = v1.components[i] - v2.components[i];
		}
		
		return difference;
	}
	
	/**
	 * <p>Calculates the dot product of this vector with another vector</p>
	 * @param v Vector to dot with
	 * @return A double containing the scalar(dot) product of the vectors
	 */
	public double dot(Vector v){
		double prod = 0.0;
		
		for(int i = 0; i < numComponents; i++){
			prod += components[i] * v.components[i];
		}
		
		return prod;
	}
	
	/**
	 * <p>Calculates the dot product of two vectors</p>
	 * @param v1 The first vector being dotted
	 * @param v2 The second vector being dotted
	 * @return A double containing the scalar(dot) product of the vectors.
	 */
	public static double dot(Vector v1, Vector v2){
		double prod = 0.0;
		
		for(int i = 0; i < v1.numComponents; i++){
			prod += v1.components[i] * v2.components[i];
		}
		
		return prod;
	}

}
