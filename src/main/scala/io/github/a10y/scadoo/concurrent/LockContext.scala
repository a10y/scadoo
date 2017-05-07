/*
 * Copyright (c) 2017 Andrew Duffy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.a10y.scadoo.concurrent

import java.util.concurrent.locks.Lock

/**
  * A LockContext serves to wrap the operations of a lock, similar to the std::lock_guard in C++.
  * The function f is executed with the lock held, the result is computed and then the lock is released.
  * @param lock The wrapped lock
  */
final class LockContext(lock: Lock) {

  def apply[A](f: => A): A = {
    lock.lock()
    try {
      f
    } finally {
      lock.unlock()
    }
  }
}

object LockContext {

  object implicits {

    implicit def lockContext(lock: Lock): LockContext = new LockContext(lock)

  }

}
