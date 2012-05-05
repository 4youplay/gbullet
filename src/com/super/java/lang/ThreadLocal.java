package java.lang;

public class ThreadLocal<T> {
  private T value;
  private boolean valueSet;
  
  public T get() {
    if (!valueSet) {
      set(initialValue());
    }
    return value;
  }

  public void set(T v) {
    this.value = v;
    valueSet = true;
  }
  
  public void remove() {
    valueSet = false;
    value = null;
  }
  
  protected T initialValue() {
    return null;
  }
}