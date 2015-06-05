package com.wantedtech.common.xpresso.types.tuple;

public final class tuple1<T> extends AbstractTuple {

    public final T value;

    protected tuple1(T value) {
        this.value = value;
    }

    public static <T> tuple1<T> valueOf(T value) {
        return new tuple1<>(value);
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        tuple1<T> tuple1 = (tuple1<T>) o;

        if (value != null ? !value.equals(tuple1.value) : tuple1.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "(" + value + ')';
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
    
    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(tuple o) {
        return ((Comparable<T>)value).compareTo(((tuple1<T>)o).value);
    }
    
    @Override
    public int size() {
        return 1;
    }
    
    public tuple copy(){
    	return new tuple1<T>(this.value);
    }

}
