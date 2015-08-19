package indoorquery;

import java.applet.*;
import java.awt.*;

public class Draw extends Applet{
	
	int floordis = 600; //绘图时楼层间距离
	
	private Room[] r;
	private Door[] d;
	private Point[] points;
	private Point[] pin1, pin2;
	int range; 
	public Draw(Room[] r, Door[] d ,Point[] points, int range,Point[] p01, Point[] p02){
		int i;
		this.range = range;
		this.r = new Room[r.length];
		//System.out.println(r.length);
		for(i=0;i<r.length ;i++){
			this.r[i] = r[i];
		}
		this.d = new Door[d.length];
		for(i=0;i<d.length ;i++){
			this.d[i] = d[i];
		}
		this.points = new Point[points.length];
		for(i=0; i<points.length; i++){		
			this.points[i] = new Point(points[i]);
		}
		this.pin1 = new Point[p01.length];
		this.pin2 = new Point[p02.length];	
		for(i=0; i<p01.length; i++){
			this.pin1[i] = new Point(p01[i]);
			this.pin2[i] = new Point(p02[i]);
		}
	}
	
	public void paint(Graphics g){
	//	g.setColor(Color.red);
		int i;
		int r_size = 5;
		g.setColor(Color.gray);
		for(i=0; i<points.length; i++){//绘制随机生成的数据
		//	g.drawOval(points[i].x-range+(points[i].getFloor()-1)*floordis, points[i].y-range, range*2, range*2);
			g.drawOval((int)points[i].x-range, (int)points[i].y-range, range*2, range*2);
			
		}
		for(i=0; i<r.length; i++){
			if(r[i].isCorridor == true){//绘制走廊
				g.setColor(Color.blue);
			//	g.drawRoundRect(r[i].getLUPoint().getX()+(r[i].getFloor()-1)*floordis,r[i].getLUPoint().getY(),Math.abs(r[i].getRDPoint().getX()-r[i].getLUPoint().getX()),Math.abs(r[i].getLUPoint().getY()-r[i].getRDPoint().getY()),20,20);
				g.drawRoundRect((int)r[i].getLUPoint().getX(),(int)r[i].getLUPoint().getY(),(int)Math.abs(r[i].getRDPoint().getX()-r[i].getLUPoint().getX()),(int)Math.abs(r[i].getLUPoint().getY()-r[i].getRDPoint().getY()),20,20);
				
			}else if(r[i].isStair == true){//绘制楼梯
				g.setColor(Color.GREEN);
				//g.drawOval(265-r_size,276-r_size , r_size*2, r_size*2);
				//g.drawOval(r[i].getLUPoint().getX()-r_size+(r[i].getFloor()-1)*floordis,r[i].getLUPoint().getY()-r_size , r_size*2, r_size*2);
				g.drawRoundRect((int)r[i].getLUPoint().getX(),(int)r[i].getLUPoint().getY(),(int)Math.abs(r[i].getRDPoint().getX()-r[i].getLUPoint().getX()),(int)Math.abs(r[i].getLUPoint().getY()-r[i].getRDPoint().getY()),20,20);
				
			}else if(r[i].isLift == true){
				g.setColor(Color.ORANGE);
				g.drawRoundRect((int)r[i].getLUPoint().getX(),(int)r[i].getLUPoint().getY(),(int)Math.abs(r[i].getRDPoint().getX()-r[i].getLUPoint().getX()),(int)Math.abs(r[i].getLUPoint().getY()-r[i].getRDPoint().getY()),20,20);
				
			}
			else{//绘制一般房间
				g.setColor(Color.BLACK);
			//	g.drawRect(r[i].getLUPoint().getX()+(r[i].getFloor()-1)*floordis,r[i].getLUPoint().getY(),Math.abs(r[i].getRDPoint().getX()-r[i].getLUPoint().getX()),Math.abs(r[i].getLUPoint().getY()-r[i].getRDPoint().getY()));
				g.drawRect((int)r[i].getLUPoint().getX(),(int)r[i].getLUPoint().getY(),(int)Math.abs(r[i].getRDPoint().getX()-r[i].getLUPoint().getX()),(int)Math.abs(r[i].getLUPoint().getY()-r[i].getRDPoint().getY()));
				
			}
			
			//	System.out.println(r[i].getLUPoint().getX()+"\t"+r[i].getLUPoint().getY()+"\t"+r[i].getRDPoint().getX()+"\t"+r[i].getRDPoint().getY()+"\n");
		
		//g.setColor(Color.red);
		//g.drawLine(r[i].getLUPoint().getX(), r[i].getLUPoint().getY(), r[i].getRDPoint().getX(), r[i].getRDPoint().getY());
		//g.drawLine(300, 300, 400, 400);
		}
		g.setColor(Color.red);
		//int r_size=5; +(d[i].getFloor()-1)*floordis
		for(i=0; i<d.length; i++){//绘制门
			//if(i==22) break;
		//	g.drawOval(d[i].getDoorLoc().x-r_size+(d[i].getFloor()-1)*floordis, d[i].getDoorLoc().y-r_size, r_size*2, r_size*2);
			g.drawOval((int)d[i].getDoorLoc().x-r_size, (int)d[i].getDoorLoc().y-r_size, r_size*2, r_size*2);
			
		}
		g.setColor(Color.RED);
		for(i=0; i<pin1.length; i++){
			g.drawRoundRect((int)pin1[i].x, (int)pin1[i].y, (int)(pin2[i].x - pin1[i].x), (int)(pin2[i].y - pin1[i].y),40,40);
		}
	}
}
