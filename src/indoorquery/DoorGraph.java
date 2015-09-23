package indoorquery;

import java.util.ArrayList;

public class DoorGraph {
	//double MAX_NUM = 1024*1024*1024;
	private Door[] ds;
	public DoorGraphEdge[] dges;
	public double ave_dis;
	public double[][] matrix;
	
	public DoorGraph(Door[] drs, Room[] rms){
		ds = drs;
		int dnum = drs.length;
		int i,j;
		int count = 0;
		ArrayList al = new ArrayList();
		for(i=0; i<dnum-1; i++)	//find door pairs have same room id
		{
			for(j=i+1; j<dnum; j++)
			{
				if((drs[i].getRoom1() == drs[j].getRoom1())||(drs[i].getRoom1() == drs[j].getRoom2())||(drs[i].getRoom2() == drs[j].getRoom1()||(drs[i].getRoom2() == drs[j].getRoom2())))
				{
					DoorGraphEdge dge = new DoorGraphEdge(drs[i],drs[j],rms);
					if(dge.getlength() != -1)
					{
						al.add(dge);
						count++;
					}
					//System.out.println();
				}
			}
		//	System.out.println(i);
		}
		this.dges = new DoorGraphEdge[count];
		for(i=0; i<count ;i++){
			this.dges[i] = (DoorGraphEdge)al.get(i);
		}
		//System.out.println(this.dges.length);
		//ave_dis = this.computeAveDis();
		//System.out.println(ave_dis);
//		for(i=0; i<drs.length; i++)
//			System.out.println(drs[i].getId());
//		System.out.println(dges.length);
//		for(i=0; i<dges.length;i++)
//			{
//				System.out.println("id1 "+dges[i].d1.getId()+" r11 "+dges[i].d1.getRoom1()+" r12 "+dges[i].d1.getRoom2()+" id2 "+dges[i].d2.getId()+" r21 "+dges[i].d2.getRoom1()+" r22 "+dges[i].d2.getRoom2()+" length "+dges[i].getlength());
//			}
	}
	
	public double GetAveDis(){
		return this.computeAveDis();
	}
	
	public double[][] GetShortMatrix(){
		double[][] m = this.ShortDisMatrix();
		
		
//		for (int i = 60; i < 70; i++) {
//			for (int j = 235; j < 245; j++) {
//				System.out.print(m[i][j]+"\t");
//			}
//			System.out.println();
//		}
		
		
		return m;
	}
	
	
	public DoorGraph(Door[] drs){
		ds = drs;
		int dnum = drs.length;
		int i,j,k;
		k = 0;
		ArrayList al = new ArrayList();
		for(i=0; i<dnum-1; i++)
		{
			for(j=i+1; j<dnum; j++)
			{
				if((drs[i].getRoom1() == drs[j].getRoom1())||(drs[i].getRoom1() == drs[j].getRoom2())||(drs[i].getRoom2() == drs[j].getRoom1()||(drs[i].getRoom2() == drs[j].getRoom2())))
				{
					DoorGraphEdge dge = new DoorGraphEdge(drs[i],drs[j]);
					al.add(dge);
					k++;
					//System.out.println();
				}
			}
		//	System.out.println(i);
		}
		this.dges = new DoorGraphEdge[k];
		for(i=0; i<k ;i++){
			this.dges[i] = (DoorGraphEdge)al.get(i);
		}
//		for(i=0; i<dges.length;i++)
//		{
//			System.out.println(dges[i].getlength());
//		}
	//	ave_dis = this.computeAveDis();
	//	System.out.println(k);
	//	System.out.println(k);
	}
	
	
	public Door[] getDoors(){
		return this.ds;
	}
	
	public DoorGraphEdge[] getEdges(){
		return this.dges;
	}
	
	public double computeAveDis(){	
		double avedis =0.0;
		double[][] m = new double[ds.length][ds.length];
		//System.out.println(ds.length);
		int i,j,k;
		for(i=0; i<ds.length; i++)
			for(j=0; j<ds.length; j++)
			{
				m[i][j]=-1;
			}
		for(i=0; i<ds.length; i++)
			m[i][i]=0;
		
		for(i=0; i<this.dges.length; i++){
			m[this.findDoorID(dges[i].d1)][this.findDoorID(dges[i].d2)] = dges[i].getlength();
			m[this.findDoorID(dges[i].d2)][this.findDoorID(dges[i].d1)] = dges[i].getlength();
		}
		
//		for(i=0; i<ds.length; i++)
//		{	
//			for(j=0; j<ds.length; j++)
//			{
//				System.out.print(m[i][j]+"\t");
//			}
//			System.out.println();
//		}
//		System.out.println();
		
		
		
		double total = 0;
		int count = 0;
		double tempdis=0;
		
		for(i=0; i<ds.length-1; i++)
			for(j=i+1; j<ds.length; j++)
			{
				tempdis = computeShortestDis(ds[i], ds[j], m);
				if(tempdis != -1){
					total += tempdis;
					count++;
				}
			//	System.out.println(j);
			}
		//System.out.println(total+" "+count);
		//for(i=1; i<ds.length;i++)
		//System.out.println(computeShortestDis(ds[0], ds[8], m));
		//System.out.println(computeShortestDis(ds[8], ds[0], m));
		avedis = total/count;
		return avedis;
	}
	
	protected int findDoorID(Door d){
		int id = d.getId();
		int i;
		for(i=0; i<ds.length; i++)
		{
			if(ds[i].getId() == id)
				break;
		}
		return i;
	}
	
	protected double[][] ShortDisMatrix(){
		int i,j,k;
		double[][] m = new double[ds.length][ds.length];
		//System.out.println(ds.length);
		for(i=0; i<ds.length; i++)
			for(j=0; j<ds.length; j++)
			{
				m[i][j]=-1;
			}
		for(i=0; i<ds.length; i++)
			m[i][i]=0;
		
		for(i=0; i<this.dges.length; i++){
			m[this.findDoorID(dges[i].d1)][this.findDoorID(dges[i].d2)] = dges[i].getlength();
			m[this.findDoorID(dges[i].d2)][this.findDoorID(dges[i].d1)] = dges[i].getlength();
		}
		matrix = new double[ds.length][ds.length];
		//for(i=0; i<ds.length; i++)
		//	matrix[i] = new double[ds.length];
		
		for(i=0; i<ds.length; i++){
			matrix[i] = computeShortestDis(ds[i], m);
		}
		
		return matrix;
	}
	
	protected double[] computeShortestDis(Door d1, double[][]m){
		
		int i,j,k;
		double min;
		int did1 = this.findDoorID(d1);
		int[] temp = new int[ds.length];
		for(i=0; i<ds.length; i++){
			temp[i] = 0;
		}
		temp[did1] = 1;
		int end = did1;
		int count = 0;
		double[] dis1 = new double[ds.length];
		dis1 = m[did1];
		
		while(count != ds.length){
			min = Double.POSITIVE_INFINITY;
			k = -1;
			for(i=0; i<ds.length; i++){
				if(temp[i] != 1 && dis1[i] >0 && dis1[i] <min){
					min = dis1[i];
					k = i;
				}
			}
			//System.out.println(min);
			if(k == -1)
				break;
			else{
				end = k;
				temp[k] = 1;
				for(i=0; i<ds.length; i++)
				{
					if(temp[i] != 1)
					{
						if(m[end][i] != -1 && (dis1[i] > dis1[end] + m[end][i] || dis1[i] == -1))
							dis1[i] = dis1[end] + m[end][i];
					}
				}
				//for(i=0; i<ds.length; i++)
					//System.out.println(dis1[i]);
			}
			count++;
		}
		return dis1;
	}
	
	
	protected double computeShortestDis(Door d1, Door d2, double[][] m){    //Dijkstra
		
		int i,j,k;
		double min;
		int did1 = this.findDoorID(d1);
		int did2 = this.findDoorID(d2);
		int[] temp = new int[ds.length];
		for(i=0; i<ds.length; i++){
			temp[i] = 0;
		}
		temp[did1] = 1;
		int end = did1;
		int count = 0;
		double[] dis1 = new double[ds.length];
		dis1 = m[did1];
		//for(i=0; i<dis1.length; i++)System.out.println(dis1[i]);
		
		while(end != did2 && count != ds.length){
			min = Double.POSITIVE_INFINITY;
			k = -1;
			for(i=0; i<ds.length; i++){
				if(temp[i] != 1 && dis1[i] >0 && dis1[i] <min){
					min = dis1[i];
					k = i;
				}
			}
			//System.out.println(min);
			if(k == -1)
			{
				return -1;
			}
			else
			{
				end = k;
				temp[k] = 1;
				for(i=0; i<ds.length; i++)
				{
					if(temp[i] != 1)
					{
						if(m[end][i] != -1 && (dis1[i] > dis1[end] + m[end][i] || dis1[i] == -1))
							dis1[i] = dis1[end] + m[end][i];
					}
				}
				//for(i=0; i<ds.length; i++)
					//System.out.println(dis1[i]);
			}
			count++;
		}
		if(count == ds.length) { return -1;}
		else return dis1[end];
	}
}
