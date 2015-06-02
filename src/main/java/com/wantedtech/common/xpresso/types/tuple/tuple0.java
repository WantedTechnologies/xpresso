package com.wantedtech.common.xpresso.types.tuple;

public final class tuple0 extends AbstractTuple{

    private static final tuple0 INSTANCE = new tuple0();

    protected tuple0() {
    }

    public static tuple0 valueOf() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "()";
    }

    @Override
    public <E> E get(int index, Class<E> elementClass) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public Object get(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int compareTo(tuple o) {
    	throw new IndexOutOfBoundsException();
    }
    
    @Override
    public int size() {
        return 0;
    }

}
