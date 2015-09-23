package indoorquery;

public class Door {
	private int id;
	private Point p;
	private int floor;
	//private Room a,b;
	private int room_id1,room_id2;
	public boolean flag = false;
	public int[] nbdoor;
	public double[] nbdis[];
//	public Door(int id,Point p, Room a, Room b){
//		this.id = id;
//		this.p = new Point(p);
//		this.floor = p.getFloor();
//		this.a = new Room(a);
//		this.b = new Room(b);
//	}
	public Door(int id, Point p, int r1, int r2){
		this.id = id;
		this.p = new Point(p);
		this.floor = p.getFloor();
		this.room_id1 = r1;
		this.room_id2 = r2;
	}
	
	public int getId(){
		return id;
	}
	
	public Point getDoorLoc(){
		return p;
	}
	
	public int getFloor(){
		return this.floor;
	}
	
	public int getRoom1(){
		return this.room_id1;
	}

	public int getRoom2(){
		return this.room_id2;
	}
//	public Room getRoom1(){
//		return this.a;
//	}
//	
//	public Room getRoom2(){
//		return this.b;
//	}
}
