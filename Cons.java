import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Cons {
	public double currentTime;
	public double Mu;
	public double timePiece;
	public static double extra;
	public Prod producer1,producer2,producer3,producer4,producer5;
	public int collision = 0;
	public String str = "E://Data4.txt";
	
	public static void write(String fileName, String content) { 
		    FileWriter writer = null;
		        try {   
		            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件   
		            writer = new FileWriter(fileName, true);   
		            writer.write(content);     
		        } catch (IOException e) {   
		            e.printStackTrace();   
		        } finally {   
		            try {   
		            if(writer != null){
		            writer.close();   
		            }
		            } catch (IOException e) {   
		                e.printStackTrace();   
		            }   
		        } 
		    }   
	public Cons(double Mu) {
		this.Mu = Mu;
	}
	
	public Cons(double Mu,double timePiece) {
		producer1 = new Prod(1,6);
		producer2 = new Prod(2,8);
		producer3 = new Prod(3,10);
		producer4 = new Prod(4,12);
		producer5 = new Prod(5,14);
		this.Mu = Mu;
		this.timePiece = timePiece;
		producer1.Run(30000);
		producer2.Run(30000);
		producer3.Run(30000);
		producer4.Run(30000);
		producer5.Run(30000);
	}
	
	public void Consume(Prod producer) {
		//单队列的服务方法
		while(!producer.queue.isEmpty()) {
			if(currentTime >= producer.queue.get(0).comeTime) {
				pack temp = producer.queue.get(0);
				temp.CalLeaf(currentTime);
				temp.servTime = -Math.log(Math.random())/Mu;
				currentTime += temp.servTime;
				//输出包的信息
			}
			else {
				currentTime += 0.01;
			}
		}
	}
	public int  CalMeanQueueLength(Prod producer) {
		//计算平均队列长度
		int index = 0;
		while(!producer.queue.isEmpty()&&index<producer.queue.size()&&producer.queue.get(index).comeTime<=currentTime) {
				index++;
		}
		producer.lengthDis.add(index+1);
		return index+1;
	}

	public double min(double a,double b) {
		return (a<b)?a:b;
	}
	public double min3(double a,double b,double c) {
		return (min(a,b)>c)?(min(a,b)):c;
	}
	
	public void Consume5() throws IOException {
		/*此函数用于五队列模拟
		 * 依旧选择Prod队列生成大量包，然后再用Cons对象运用时间片方法进行处理
		 * 碰撞机制(关键思想)：包在不断产生，设置一个包在路径上传输的时间delt,delt设置为恒定的值，因为传输时间主要与传输路径相关，而传输路径假设
		 * 是不变的
		 * 然后在一个包在传输的过程中，为了防止已发送的包与正在发送的包冲突，要发送的包都被回退
		 */
		
		int n = 1;
		while(!producer1.queue.isEmpty()||!producer2.queue.isEmpty()||!producer3.queue.isEmpty()) {
			if(n==1)      ConsPiece(producer1,producer2,producer3,producer4,producer5); 
			else if(n==2) ConsPiece(producer2,producer1,producer3,producer4,producer5);
			else if(n==3) ConsPiece(producer3,producer1,producer2,producer4,producer5);
			else if(n==4) ConsPiece(producer4,producer1,producer2,producer3,producer5);
			else 		  ConsPiece(producer5,producer1,producer2,producer3,producer4);
				if(n==1) n=2;
				else if(n==2) n=3;
				else if(n==3) n=4;
				else if(n==4) n=5;
				else n = 1;
		}
	}
		
	
	public void ConsPiece(Prod producer,Prod producer1,Prod producer2,Prod producer3,Prod producer4) throws IOException{
		double serve = 0;
		int QueueLength = 0;
		while((!producer.queue.isEmpty())&&serve<timePiece) {
			if(producer.queue.get(0).comeTime<=currentTime) {

				pack temp = producer.queue.get(0);
				temp.CalLeaf(currentTime);
				temp.servTime = -Math.log(Math.random())/Mu;
				tensCollision(producer,producer1,producer2,producer3, producer4,temp.servTime);
				currentTime += temp.servTime;
				serve += temp.servTime;
				//输出包信息文件
				QueueLength = CalMeanQueueLength(producer);
				write(str,producer.index+"  "+temp.index+"  "+temp.waitTime+"  "+QueueLength + "  "+ producer.collision+"\r\n");
				System.out.print("队列"+producer.index+" 包: "+temp.index+"   " + "waittime: "+temp.waitTime);
				producer.queue.remove(0);
				System.out.println();
			}else {
				currentTime += 0.01;
				break;
			}
		}
	}
	
	public void tensCollision(Prod prod1,Prod prod2,Prod prod3,Prod prod4,Prod prod5,double time) {
		boolean ret;
		Prod[] p = {prod2,prod3,prod4,prod5};
		double [] time1 = {-1,-1,-1,-1};
		double sta = prod1.queue.get(0).comeTime;
		double end = sta + time;
		if(prod2.queue.get(0).comeTime >sta && prod2.queue.get(0).comeTime < end) {
			time1[0] = 7*Math.random();
			prod2.collision ++;
		}
		
		if(prod3.queue.get(0).comeTime >sta && prod3.queue.get(0).comeTime < end) {
			time1[1] = 7*Math.random();
			prod3.collision ++;
		}
		
		if(prod4.queue.get(0).comeTime >sta && prod4.queue.get(0).comeTime < end) {
			time1[2] = 7*Math.random();
			prod4.collision ++;
		}
		
		if(prod5.queue.get(0).comeTime >sta && prod5.queue.get(0).comeTime < end) {
			time1[3] = 7*Math.random();
			prod5.collision ++;
		}
		for(int i =0;i<=3;i++) {
			if(time1[i] != -1) {
				p[i].back(time1[i]);
			}
		}
		
	}	
	
	
}
