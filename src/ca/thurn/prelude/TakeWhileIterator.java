package ca.thurn.prelude;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

public class TakeWhileIterator<A> implements Iterator<A> {

  private Function<A, Boolean> fn;
  private PeekingIterator<A> iterator;

  public TakeWhileIterator(Function<A, Boolean> fn, Iterator<A> iterator) {
    this.fn = fn;
    this.iterator = Iterators.peekingIterator(iterator);
  }

  @Override
  public boolean hasNext() {
    if (!iterator.hasNext()) return false;
    else if (fn.apply(iterator.peek())) return true;
    else return false;
  }

  @Override
  public A next() {
    if (!hasNext()) {
      throw new NoSuchElementException("No more elements in iterable returned from takeWhile().");
    }
    return iterator.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("The iterable returned from takeWhile() does not " +
        "support remove.");
  }

}
