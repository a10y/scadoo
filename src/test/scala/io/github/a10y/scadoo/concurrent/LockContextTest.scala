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

import java.util.concurrent.locks.ReentrantLock

import org.scalatest._

class LockContextTest extends FlatSpec with Matchers {

  behavior of "LockContext"

  it should "should be locked in the body and unlocked at the end" in {
    import LockContext.implicits._

    val lock = new ReentrantLock()
    val s: String = lock {
      lock.isLocked shouldBe true
      "hello world"
    }
    lock.isLocked shouldBe false
    s should be("hello world")
  }

  it should "unlock even if an exception is thrown" in {
    import LockContext.implicits._

    val lock = new ReentrantLock()

    intercept[RuntimeException] {
      val s: String = lock {
        lock.isLocked shouldBe true
        throw new RuntimeException("lock should still be unlocked")
      }
    }
    lock.isLocked shouldBe false
  }

}
