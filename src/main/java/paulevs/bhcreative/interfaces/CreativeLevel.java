package paulevs.bhcreative.interfaces;

public interface CreativeLevel {
	boolean creative_isCreative();
	void creative_setCreative(boolean creative);
	
	static CreativeLevel cast(Object obj) {
		return (CreativeLevel) obj;
	}
}
