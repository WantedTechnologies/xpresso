package com.wantedtech.common.xpresso.types.tuple;

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

    @SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        tuple4<T0, T1, T2, T3> tuple4 = (tuple4<T0, T1, T2, T3>) o;

        if (value0 != null ? !value0.equals(tuple4.value0) : tuple4.value0 != null) return false;
        if (value1 != null ? !value1.equals(tuple4.value1) : tuple4.value1 != null) return false;
        if (value2 != null ? !value2.equals(tuple4.value2) : tuple4.value2 != null) return false;
        if (value3 != null ? !value3.equals(tuple4.value3) : tuple4.value3 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value0 != null ? value0.hashCode() : 0;
        result = 31 * result + (value1 != null ? value1.hashCode() : 0);
        result = 31 * result + (value2 != null ? value2.hashCode() : 0);
        result = 31 * result + (value3 != null ? value3.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + value0 + ',' + value1 + ',' + value2 + ',' + value3 + ')';
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
    
    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(tuple o) {
        int i = ((Comparable<T0>)value0).compareTo(((tuple4<T0,T1,T2,T3>)o).value0);
        if (i != 0) {return i;}
        i = ((Comparable<T1>)value1).compareTo(((tuple4<T0,T1,T2,T3>)o).value1);
        if (i != 0) {return i;}
        i = ((Comparable<T2>)value2).compareTo(((tuple4<T0,T1,T2,T3>)o).value2);
        if (i != 0) {return i;}
        return ((Comparable<T3>)value3).compareTo(((tuple4<T0,T1,T2,T3>)o).value3);
    }
    
    @Override
    public int size() {
        return 4;
    }
    
    public tuple copy(){
    	return new tuple4<T0,T1,T2,T3>(this.value0,this.value1,this.value2,this.value3);
    }
    
}
