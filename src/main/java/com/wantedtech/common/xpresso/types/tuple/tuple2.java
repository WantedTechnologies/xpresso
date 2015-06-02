package com.wantedtech.common.xpresso.types.tuple;

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

    @SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        tuple2<T0, T1> tuple2 = (tuple2<T0, T1>) o;

        if (value0 != null ? !value0.equals(tuple2.value0) : tuple2.value0 != null) return false;
        if (value1 != null ? !value1.equals(tuple2.value1) : tuple2.value1 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value0 != null ? value0.hashCode() : 0;
        result = 31 * result + (value1 != null ? value1.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + value0 + ',' + value1 + ')';
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
    
    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(tuple o) {
        int i = ((Comparable<T0>)value0).compareTo(((tuple2<T0,T1>)o).value0);
        if (i != 0) {return i;}
        return ((Comparable<T1>)value1).compareTo(((tuple2<T0,T1>)o).value1);
    }

    @Override
    public int size() {
        return 2;
    }

}
