package indoorquery;

public class MovingObj {
	private Point loc;
	private int range;
	private int room_id;
	
	public MovingObj(Point p, int r, int id){
		this.loc = new Point(p);
		this.range = r;
		this.room_id = id;
	}
	
	public MovingObj(Point p, int r, Room[] rooms){
		this.loc = new Point(p);
		this.range = r;
		this.room_id = p.inRoomNo(rooms);
	}
	
	public Point getObjLoc(){
		return loc;
	}
	
	public int getRange(){
		return range;
	}
	
	public int getRoomId(){
		return room_id;
	}
}
