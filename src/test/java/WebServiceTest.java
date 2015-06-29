import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.helpers.inject.ExposeAs;
import com.wantedtech.common.xpresso.types.dict;

class WebServiceTest {
	public int getInt(@ExposeAs("i") int i) {
		return i;
	}
	public int[] getArrayInt(@ExposeAs("i") int[] i) {
		return i;
	}
	public Double[] getArrayDouble(@ExposeAs("i") Double[] i) {
		return i;
	}
	
	public dict<Integer> getSum(@ExposeAs("x") int i, @ExposeAs("y") int j) {
		return x.dict(x.tuple("result",i + j));
	}
}
