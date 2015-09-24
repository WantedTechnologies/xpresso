package com.wantedtech.common.xpresso.types.tuples;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;

public final class tuple2<T0, T1> extends AbstractTuple {

    public final T0 value0;
    public final T1 value1;
    
    /**
     * A synonym for value0
     */
    public final T0 key;
    public final T0 left;
    
    /**
     * A synonym for value1
     */
    public final T1 value;
    public final T1 right;
    
    /**
     * A synonym for value0
     */
    public final T0 index;

    protected tuple2(T0 value0, T1 value1) {
        this.value0 = value0;
        this.value1 = value1;
        
        this.key = value0;
        this.index = value0;
        this.value = value1;
        
        this.left = value0;
        this.right = value1;
    }

    public static <T0, T1> tuple2<T0, T1> valueOf(T0 value0, T1 value1) {
        return new tuple2<>(value0, value1);
    }

    @Override
    public String toString() {
        return "(" + value0 + ", " + value1 + ")";
    }

    @SuppressWarnings("unchecked")
	@Override
    public <E> E get(int index, Class<E> elementClass) {
        switch (index) {
            case 0:
                return (E)value0;
            case 1:
                return (E)value1;
            default:
                throw new IndexOutOfBoundsException();
        }
    }
    
    public Object get(int index) {
        switch (index) {
        case 0:
            return value0;
        case 1:
            return value1;
        default:
            throw new IndexOutOfBoundsException();
        }
    }
    
    @Override
    public int size() {
        return 2;
    }
    
    public tuple copy(){
    	return new tuple2<T0,T1>(this.value0,this.value1);
    }
    
	@Override
	public list<Object> toList() {
		return x.list(get(0),get(1));
	}

}
