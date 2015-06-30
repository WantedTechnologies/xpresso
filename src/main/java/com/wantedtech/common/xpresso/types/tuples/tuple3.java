package com.wantedtech.common.xpresso.types.tuples;

import com.wantedtech.common.xpresso.types.tuple;

public final class tuple3<T0, T1, T2> extends AbstractTuple {

    public final T0 value0;
    public final T1 value1;
    public final T2 value2;
    
    /*
     * Synonyms for value0, value1, value2
     */
    public final T0 left;
    public final T1 middle;
    public final T2 right;

    protected tuple3(T0 value0, T1 value1, T2 value2) {
        this.value0 = value0;
        this.value1 = value1;
        this.value2 = value2;
        
        this.left = value0;
        this.middle = value1;
        this.right = value2;
    }

    public static <T0, T1, T2> tuple3<T0, T1, T2> valueOf(T0 value0, T1 value1, T2 value2) {
        return new tuple3<>(value0, value1, value2);
    }

    @Override
    public String toString() {
        return "(" + value0 + ", " + value1 + ", " + value2 + ")";
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
        default:
            throw new IndexOutOfBoundsException();
        }
    }
    
    @Override
    public int size() {
        return 3;
    }
    
    public tuple copy(){
    	return new tuple3<T0,T1,T2>(this.value0,this.value1,this.value2);
    }
}
