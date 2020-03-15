package knn;

import java.io.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class KNN {
	
	int noOfAttributes;
	public static int count=0;
	public static int total=0;
	ArrayList<Patient>patient;
	int numberOfNeighbours;

	public KNN (int k){
		patient=new ArrayList<Patient>();
		noOfAttributes=6;
		this.numberOfNeighbours=k;
	}
	
	
	
	public void knnAlgorithm(){
		
		readInputFile();
		readTestFile();
		
		System.out.println("\nAccuracy: "+ ((count*100)/total));
	}
	
	
	
	public void readInputFile()
	{
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("E:/My 5th/KNN-Acute-Inflammation/src/knn/diagnosisE.txt")));
			String str;
			while((str=br.readLine())!=null){
				String []att=str.split("\\t");
				Patient f=new Patient(Integer.parseInt(att[att.length-2]),Integer.parseInt(att[att.length-1]));
				for(int i=0;i<noOfAttributes;i++){
					f.attribute[i]=Double.valueOf(att[i]);
				}
				patient.add(f);

			}
			br.close();
		}catch(Exception e){

			e.printStackTrace();
		}
		
		System.out.println("Input size : "+ patient.size());
	}
	
	
	
	public void readTestFile(){
		
		Patient p;
		
		
		
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("E:/My 5th/KNN-Acute-Inflammation/src/knn/test.txt")));
			String str;
			while((str=br.readLine())!=null){
				String []att=str.split(" ");
				p=new Patient(Integer.parseInt(att[att.length-2]),Integer.parseInt(att[att.length-1]));
				System.out.println("Expected decision1: "+att[att.length-2]);
				System.out.println("Expected decision2: "+att[att.length-1]);
				
				for(int i=0;i<noOfAttributes;i++){
					p.attribute[i]=Double.valueOf(att[i]);
				}
				for(int j=0;j<patient.size();j++){
					patient.get(j).distance=calculateDistance(p.attribute,patient.get(j).attribute);
				}
				Collections.sort(patient, new Comparator<Patient>(){
					public int compare(Patient f1, Patient f2) {
						double dif=f1.distance-f2.distance;
						if(dif>0)
							return 1;
						else if(dif<0)
							return -1;
						else{
							return 0;
						}
					}
				});
				
				
				int decision1=getDecision1();
				if(decision1==p.decision1){
					count++;
				}
				System.out.println("Actual class: "+decision1);
				total++;
				int decision2=getDecision2();
				if(decision2==p.decision2){
					count++;
				}
				System.out.println("Actual class: "+decision2);
				total++;
				System.out.println();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	private double calculateDistance(double []d1,double[]d2)
	{
		double total=0;
		for(int i=0;i<noOfAttributes;i++){
			total+=(d1[i]-d2[i])*(d1[i]-d2[i]);
		}
		return (Math.sqrt(total));
	}

	private int getDecision1(){
		int positive=0;
		int negative=0;
		for(int i=0;i<numberOfNeighbours;i++){
			int className=patient.get(i).decision1;
			if(className==0){
				negative++;
			}
			else if(className==1){
				positive++;
			}
		}
		
		if(positive>negative){
			return 1;
		}
		else if(negative>positive){
			return 0;
		}
		return 0;
	}
	
	private int getDecision2(){
		int positive=0;
		int negative=0;
		for(int i=0;i<numberOfNeighbours;i++){
			int className=patient.get(i).decision2;
			if(className==0){
				negative++;
			}
			else if(className==1){
				positive++;
			}
		}
		
		if(positive>negative){
			return 1;
		}
		else if(negative>positive){
			return 0;
		}
		return 0;
	}
}
