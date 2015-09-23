package indoorquery;

import java.lang.management.ThreadInfo;
import java.util.ArrayList;

public class Room {
//	private double x1,y1,x2,y2;
	private int id;
	private int floor;
	private Point p1,p2;
	public boolean isStair = false;
	public boolean isCorridor = false;
	public boolean isLift = false;
	public double dpr = 0;
	public double avedis = 0;
	//public double area;
	
	public Room(int id, Point p1,Point p2,int type){
		this.id = id;
		this.p1 = new Point(p1);
		this.p2 = new Point(p2);

		this.floor = p1.getFloor();
		this.isCorridor = false;
		this.isStair = false;
		switch(type){
		case 0: break;
		case 1:	this.isCorridor = true;	break;
		case 2:	this.isStair = true;	break;
		case 3:	this.isLift = true;		break;
		}
	}
	
	public Room(Room r){
		this.id = r.id;
		this.p1 = new Point(r.getLUPoint());
		this.p2 = new Point(r.getRDPoint());
		this.floor = r.getFloor();
		this.isCorridor = r.isCorridor;
		this.isStair = r.isStair;
		this.isLift = r.isLift;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getFloor(){
		return this.floor;
	}
	
	public Point getLUPoint(){
		return this.p1;
	}
	
	public Point getRDPoint(){
		return this.p2;
	}
		
	public boolean inRegion_2d(Point p1, Point p2){
		if(this.p1.x >= p1.x && this.p1.y >= p1.y && this.p2.x <= p2.x && this.p2.y <= p2.y)
			return true;
		else
			return false;
	}
	
	public double getArea(){
		return (Math.abs(this.p1.x - this.p2.x) * Math.abs(this.p1.y - this.p2.y)/(1000*1000));
	}
	
	public int doornum(Door[] d){
		int count = 0;
		for (int i = 0; i < d.length; i++) {
			if(d[i].getRoom1() == this.id || d[i].getRoom2() == this.id)
				count++;
		}
		return count;
	}
	
	public int[] getDoorID(Door[] d){
		ArrayList al = new ArrayList();
		for (int i = 0; i < d.length; i++) {
			if(d[i].getRoom1() == this.id || d[i].getRoom2() == this.id){
				al.add(i);
			}
		}
		if(al.size() == 0 || al.size() == 1){return null;}
		else{
			int[] dr = new int[al.size()];
			for (int i = 0; i < al.size(); i++) {
				dr[i] = (Integer)al.get(i);
			}
			return dr;
		}
		
	}
	
	
	public double aveDisInRoom(Door[] d){
		ArrayList al = new ArrayList();
		for (int i = 0; i < d.length; i++) {
			if(d[i].getRoom1() == this.id || d[i].getRoom2() == this.id){
				al.add(d[i]);
			}
		}
		if(al.size() == 0 || al.size() == 1){return 0;}
		else{
			Door[] dr = new Door[al.size()];
			for (int i = 0; i < al.size(); i++) {
				dr[i] = (Door)al.get(i);
			}
			double sum = 0;
			for (int i = 0; i < dr.length-1; i++) {
				for (int j = i+1; j < dr.length; j++) {
					if(dr[i].getFloor() != dr[j].getFloor()){
						sum += 2500;
					}
					else{
						sum += dr[i].getDoorLoc().distance_2d(dr[j].getDoorLoc());
					}
				}
			}
			this.avedis = sum/(dr.length*1000);
			return this.avedis;
		}
		
	}
}
