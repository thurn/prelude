/**
 *
 */
package ca.thurn.prelude;

import java.util.*;

class SkipLastIterator<A> implements Iterator<A> {
  private final Iterator<A> iterator;
  private A next;
  private boolean hasNext;

  SkipLastIterator(Iterator<A> iterator) {
    this.next = iterator.next();
    this.iterator = iterator;
    this.hasNext = iterator.hasNext();
  }

  @Override
  public boolean hasNext() {
    return hasNext;
  }

  @Override
  public A next() {
    if (!hasNext) throw new NoSuchElementException("No more elements!");
    A result = next;
    next = iterator.next();
    hasNext = iterator.hasNext();
    return result;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException(
        "Can't remove elements from the Iterable returned by init()");
  }
}