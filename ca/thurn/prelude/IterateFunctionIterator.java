package ca.thurn.prelude;

import java.util.Iterator;

import com.google.common.base.Function;

class IterateFunctionIterator<A> implements Iterator<A> {


  private A next;
  private Function<A, A> fn;

  public IterateFunctionIterator(Function<A, A> fn, A a) {
    this.next = a;
    this.fn = fn;
  }

  @Override
  public boolean hasNext() {
    return true;
  }

  @Override
  public A next() {
    A result = next;
    next = fn.apply(next);
    return result;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException(
        "Remove is not supported on the iterable returned from iterate()");
  }

}
