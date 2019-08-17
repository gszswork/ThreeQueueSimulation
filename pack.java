
public class pack {
	public int index;
	public double comeTime;
	public double leafTime;
	public double servTime;
	public double waitTime;
	public pack(int index) {
		this.index = index;
	}
	public void CalLeaf(double tt) {
		waitTime = (tt - comeTime);			//暂定为这样把，实际应该传入
	}
}
