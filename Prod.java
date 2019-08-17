import java.util.ArrayList;
public class Prod {
	
	int collision = 0;
	int index;
	double  lamda;
	ArrayList<pack> queue;
	int length;
	double currentTime;     //用于记录当前时间线
	public ArrayList<Integer> lengthDis;
	public Prod(double lamda) {
		this.lamda = lamda;
		queue = new ArrayList<pack>();
		lengthDis = new ArrayList<Integer>();
	}   
	public Prod(int index,double lamda) {
		this.index = index;
		this.lamda = lamda;
		queue = new ArrayList<pack>();
		lengthDis = new ArrayList<Integer>();
	}
	public void getLength() {
		length = queue.size();
	}
	
	public void Run(int count) {
		for(int i=0;i<count;i++) { 
			//_______用于调试
			System.out.println("队列"+index+" 包"+i);
			//-------------
			pack temppack = new pack(i);				//生成新的包
			temppack.comeTime =  currentTime;			//设置包的到来时间为当前时间
			double comeInter = -Math.log(Math.random())/lamda;								//测试阶段设置整形
			currentTime += comeInter;						//生产者计时器应该
			queue.add(temppack); 							//向队列中添加
		}
	}
	public void back(double binaryTime) {
		int id = 0;
		pack p = queue.remove(0);
		p.comeTime += binaryTime;
		for(id = 0; id<queue.size();id++) {
			if(p.comeTime<=queue.get(id).comeTime) {
				break;
			}
		}
		id = id ;
		queue.add(id, p);
	}
	
	public static void main(String args[]) {
		Prod p = new Prod(1,3);
		p.Run(10);
		for(int i = 0;i<10;i++) {
			System.out.print(p.queue.get(i).comeTime+ "  ");
		}
		System.out.println();
		p.back(9);
		for(int i = 0;i<10;i++) {
			System.out.print(p.queue.get(i).comeTime+ "  ");
		}
	}
}
