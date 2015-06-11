package com.wantedtech.common.xpresso.types.tuples;

import com.wantedtech.common.xpresso.types.tuple;

public final class tuple4<T0, T1, T2, T3> extends AbstractTuple {

	public final T0 value0;
	public final T1 value1;
	public final T2 value2;
	public final T3 value3;

    protected tuple4(T0 value0, T1 value1, T2 value2, T3 value3) {
        this.value0 = value0;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public static <T0, T1, T2, T3> tuple4<T0, T1, T2, T3> valueOf(T0 value0, T1 value1, T2 value2, T3 value3) {
        return new tuple4<>(value0, value1, value2, value3);
    }

    @Override
    public String toString() {
        return "(" + value0 + ", " + value1 + ", " + value2 + ", " + value3 + ")";
    }

    @SuppressWarnings("unchecked")
	@Override
    public <E> E get(int index, Class<E> elementClass) {
        switch (index) {
            case 0:
                return (E)value0;
            case 1:
                return (E)value1;
            case 2:
                return (E)value2;
            case 3:
                return (E)value3;
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
        case 2:
            return value2;
        case 3:
            return value3;
        default:
            throw new IndexOutOfBoundsException();
        }
    }
    
    @Override
    public int size() {
        return 4;
    }
    
    public tuple copy(){
    	return new tuple4<T0,T1,T2,T3>(this.value0,this.value1,this.value2,this.value3);
    }
    
}
