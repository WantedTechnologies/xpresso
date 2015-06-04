package com.wantedtech.common.xpresso.lambda;

import com.wantedtech.common.xpresso.Lengthful;
import com.wantedtech.common.xpresso.x;

public class Value implements Lengthful {
	public Object value;
	public Value(Object value){
		this.value = value;
	}
	public Value(Value value){
		this.value = value.value;
	}
	public Value or(Value value){
		if(value.value instanceof Boolean){
			if(this.value instanceof Boolean){
				this.value	= ((Boolean)this.value) || (Boolean)(value.value);
				return this;
			}
		}
		return new Value(false);
	}
	public Value not(){
		if(this.value instanceof Boolean && (Boolean)this.value == false){
			this.value = true;
			return this;
		}
		return new Value(false);
	}
	public Value and(Value value){
		if(value.value instanceof Boolean){
			if(this.value instanceof Boolean){
				this.value	= ((Boolean)this.value) && (Boolean)(value.value);
				return this;
			}
		}
		return new Value(false);
	}
	public <T> Value less(Value value){
		if(value.value instanceof Comparable){
			if(this.value instanceof Comparable){
				int result	= ((Comparable<T>)this.value).compareTo((T)value);
				if (result < 0){
					return new Value(true);
				}
			}
		}
		return new Value(false);
	}
	public <T> Value lessorequals(Value value){
		if(value.value instanceof Comparable){
			if(this.value instanceof Comparable){
				int result	= ((Comparable<T>)this.value).compareTo((T)value.value);
				if (result <= 0){
					return new Value(true);
				}
			}
		}
		return new Value(false);
	}
	public Value plus(Value value){
		if(value.value instanceof Integer){
			if(this.value instanceof Integer){
				this.value	= ((Integer)this.value) + (Integer)(value.value);
			}else if(this.value instanceof Double){
				this.value	= ((Double)this.value) + (Double)(value.value);
			}
		}else if(value.value instanceof Double){
			if(this.value instanceof Integer){
				this.value	= ((Integer)this.value) + (Double)(value.value);
			}else if(this.value instanceof Double){
				this.value	= ((Double)this.value) + (Double)(value.value);
			}
		}else if(value.value instanceof String){
			this.value	= ((String)this.value) + (String)(value.value);
		}
		return this;
	}
	public Value minus(Value value){
		if(value.value instanceof Integer){
			if(this.value instanceof Integer){
				this.value	= ((Integer)this.value) - (Integer)(value.value);
			}else if(this.value instanceof Double){
				this.value	= ((Double)this.value) - (Double)(value.value);
			}
		}else if(value.value instanceof Double){
			if(this.value instanceof Integer){
				this.value	= ((Integer)this.value) - (Double)(value.value);
			}else if(this.value instanceof Double){
				this.value	= ((Double)this.value) - (Double)(value.value);
			}
		}
		return this;
	}
	public Value multiplyBy(Value value){
		if(value.value instanceof Integer){
			if(this.value instanceof Integer){
				this.value	= ((Integer)this.value) * (Integer)(value.value);
			}else if(this.value instanceof Double){
				this.value	= ((Double)this.value) * (Double)(value.value);
			}else if(this.value instanceof String){
				String addition = (String)this.value;
				for(int i = 0;i<((Integer)value.value).intValue()-1;i++){
					this.value = (String)(this.value) + addition;	
				}
			}
		}else if(value.value instanceof Double){
			if(this.value instanceof Integer){
				this.value	= ((Integer)this.value) * (Double)(value.value);
			}else if(this.value instanceof Double){
				this.value	= ((Double)this.value) * (Double)(value.value);
			}else if(this.value instanceof String){
				String addition = (String)this.value;
				for(int i = 0;i<((Double)value.value).doubleValue()-1;i++){
					this.value = (String)(this.value) + addition;	
				}
			}
		}
		return this;
	}
	public Value diviseBy(Value value){
		if(value.value instanceof Integer){
			if(this.value instanceof Integer){
				this.value	= ((Integer)this.value) / (Integer)(value.value);
			}else if(this.value instanceof Double){
				this.value	= ((Double)this.value) / (Double)(value.value);
			}
		}else if(value.value instanceof Double){
			if(this.value instanceof Integer){
				this.value	= ((Integer)this.value) / (Double)(value.value);
			}else if(this.value instanceof Double){
				this.value	= ((Double)this.value) / (Double)(value.value);
			}
		}
		return this;
	}
	@Override
	public String toString(){
		return this.value.toString();
	}
	@Override
	public boolean equals(Object anotherValue){
		if(!(anotherValue instanceof Value)){
			return false;
		}
		return this.value.equals(((Value)anotherValue).value);
	}
	@Override
	public int len(){
		if(this.value instanceof Iterable<?>){
			return x.len(((Iterable<?>)this.value));
		}
		return 0;
	}
}
