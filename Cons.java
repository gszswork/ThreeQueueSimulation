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
		            // ��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�   
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
		//�����еķ��񷽷�
		while(!producer.queue.isEmpty()) {
			if(currentTime >= producer.queue.get(0).comeTime) {
				pack temp = producer.queue.get(0);
				temp.CalLeaf(currentTime);
				temp.servTime = -Math.log(Math.random())/Mu;
				currentTime += temp.servTime;
				//���������Ϣ
			}
			else {
				currentTime += 0.01;
			}
		}
	}
	public int  CalMeanQueueLength(Prod producer) {
		//����ƽ�����г���
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
		/*�˺������������ģ��
		 * ����ѡ��Prod�������ɴ�������Ȼ������Cons��������ʱ��Ƭ�������д���
		 * ��ײ����(�ؼ�˼��)�����ڲ��ϲ���������һ������·���ϴ����ʱ��delt,delt����Ϊ�㶨��ֵ����Ϊ����ʱ����Ҫ�봫��·����أ�������·������
		 * �ǲ����
		 * Ȼ����һ�����ڴ���Ĺ����У�Ϊ�˷�ֹ�ѷ��͵İ������ڷ��͵İ���ͻ��Ҫ���͵İ���������
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
				//�������Ϣ�ļ�
				QueueLength = CalMeanQueueLength(producer);
				write(str,producer.index+"  "+temp.index+"  "+temp.waitTime+"  "+QueueLength + "  "+ producer.collision+"\r\n");
				System.out.print("����"+producer.index+" ��: "+temp.index+"   " + "waittime: "+temp.waitTime);
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
