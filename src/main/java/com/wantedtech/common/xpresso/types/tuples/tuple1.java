package com.wantedtech.common.xpresso.types.tuples;

import com.wantedtech.common.xpresso.types.tuple;

public final class tuple1<T> extends AbstractTuple {

    public final T value;

    protected tuple1(T value) {
        this.value = value;
    }

    public static <T> tuple1<T> valueOf(T value) {
        return new tuple1<>(value);
    }

    @Override
    public String toString() {
        return "(" + value + ")";
    }

    @SuppressWarnings("unchecked")
	@Override
    public <E> E get(int index, Class<E> elementClass) {
        switch (index) {
            case 0:
                return (E)value;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    public Object get(int index) {
        switch (index) {
        case 0:
            return value;
        default:
            throw new IndexOutOfBoundsException();
        }
    }  
        
    @Override
    public int size() {
        return 1;
    }
    
    public tuple copy(){
    	return new tuple1<T>(this.value);
    }

}
