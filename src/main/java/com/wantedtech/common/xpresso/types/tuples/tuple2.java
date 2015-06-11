package com.wantedtech.common.xpresso.types.tuples;

import com.wantedtech.common.xpresso.types.tuple;

public final class tuple2<T0, T1> extends AbstractTuple {

    public final T0 value0;
    public final T1 value1;

    protected tuple2(T0 value0, T1 value1) {
        this.value0 = value0;
        this.value1 = value1;
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

}
