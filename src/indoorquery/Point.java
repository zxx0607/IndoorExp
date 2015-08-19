package indoorquery;

public class Point {
	public double x,y;
	public int z;
	
	public Point(double x,double y,int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point(Point p){
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getFloor();
	}
	
	public int getFloor(){
		return this.z;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void Output(){
		System.out.println(this.x+"	"+this.y+"	"+this.z+"\n");
	}
	
	public Point LeftUp(Point p2, Point p3, Point p4){
		Point plu;
		double x,y;
		int z;
		z = this.z;
		if(this.x < p2.x || this.x < p3.x || this.x < p4.x){
			x = this.x;
		}else if(p2.x < this.x || p2.x < p3.x ||p2.x < p4.x){
			x = p2.x;
		}else{
			x = p3.x;
		}
		if(this.y < p2.y || this.y < p3.y || this.y < p4.y){
			y = this.y;
		}else if(p2.y < this.y || p2.y < p3.y ||p2.y < p4.y){
			y = p2.y;
		}else{
			y = p3.y;
		}
		plu = new Point(x,y,z);
		
		return plu;
	}
	
	public Point RightDown(Point p2, Point p3, Point p4){
		Point prd;
		double x,y;
		int z;
		z = this.z;
		if(this.x > p2.x || this.x > p3.x || this.x > p4.x){
			x = this.x;
		}else if(p2.x > this.x || p2.x > p3.x ||p2.x > p4.x){
			x = p2.x;
		}else{
			x = p3.x;
		}
		if(this.y > p2.y || this.y > p3.y || this.y > p4.y){
			y = this.y;
		}else if(p2.y > this.y || p2.y > p3.y ||p2.y > p4.y){
			y = p2.y;
		}else{
			y = p3.y;
		}
		prd = new Point(x,y,z);
		return prd;
	}
	
	public boolean inRoom(Room r){
		if(this.x >= r.getLUPoint().x && this.x <= r.getRDPoint().x && this.y >=r.getLUPoint().y && this.y <= r.getRDPoint().y){
			return true;
		}else{
			return false;
		}
	}
	
	public int inRoomNo(Room[] rooms){
		int i;
		for(i=0; i<rooms.length; i++){
			if(this.inRoom(rooms[i])){
				return i;
			}
		}
		return -1;
	}
	
	public double distance_2d(Point p){
		return Math.sqrt((this.x-p.x)*(this.x-p.x)+(this.y-p.y)*(this.y-p.y));
	}
	
	public boolean inRegion_2d(Point p1, Point p2){
		if(this.x >= p1.x && this.y >= p1.y && this.x <= p2.x && this.y <= p2.y)
			return true;
		else
			return false;
	}
	
}
