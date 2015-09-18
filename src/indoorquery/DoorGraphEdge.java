package indoorquery;

public class DoorGraphEdge {
	public Door d1,d2;
	private double length;
	public DoorGraphEdge(Door d1, Door d2){
		this.d1 = d1;
		this.d2 = d2;
		this.length = d1.getDoorLoc().distance_2d(d2.getDoorLoc()); 
	}
	public DoorGraphEdge(Door d1, Door d2, Room[] rms) {
		this.d1 = d1;
		this.d2 = d2;
		int rid;
		if(d1.getRoom1() == d2.getRoom1() || d1.getRoom1() == d2.getRoom2())	//find same room id
			rid = d1.getRoom1();
		else rid = d1.getRoom2();
		if(rid != -1){ 	//!outdoor
			if(rms[rid].isLift || rms[rid].isStair){	
				this.length = 2500;		//set floor distance 2.5m
			}
			else{
				this.length = d1.getDoorLoc().distance_2d(d2.getDoorLoc());
			}
		}
		else {
			//this.length = -1; System.out.println("d1: "+d1.getId()+" d2: "+d2.getId()+" rid: "+rid);
		}	
	}
	public double getlength(){
		return this.length;
	}
}
