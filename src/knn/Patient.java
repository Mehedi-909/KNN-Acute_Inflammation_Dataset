package knn;

public class Patient{
	double[] attribute;
	String className;
	double distance;
	int decision1,decision2;
	public Patient(int a,int b) {
		this.attribute = new double[6];
		this.distance = 0;
		decision1=a;
		decision2=b;
	} 	
}
