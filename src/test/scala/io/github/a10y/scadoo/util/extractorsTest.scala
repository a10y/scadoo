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

package io.github.a10y.scadoo.util

import org.scalatest.{ FlatSpec, Matchers }

class ExtractorsTest extends FlatSpec with Matchers {
  behavior of "HostPortPair"
  it should "extract well-formatted pair" in {
    val pair                     = "localhost:1234"
    val HostPortPair(host, port) = pair
    host should be("localhost")
    port should be(1234)
  }
  it should "fail for poorly formatted host:port pair" in {
    val pair = "localhost:hocalroast"
    intercept[MatchError] {
      val HostPortPair(host, port) = pair
    }
  }

  behavior of "RFC5322Address"
  it should "extract correctly formatted email address" in {
    val addr                         = "my_big_address!@test.edu"
    val RFC5322Address(name, domain) = addr
    name should be("my_big_address!")
    domain should be("test.edu")
  }
  it should "fail for totally wrong address" in {
    val fakeAddr = "omg you guys are SO unfair"
    intercept[MatchError] {
      val RFC5322Address(_, _) = fakeAddr
    }
  }
  it should "fail for subtly wrong address" in {
    val badAddr = "Agd@my.domain.edu" // uppercase is disallowed in RFC 5322
    intercept[MatchError] {
      val RFC5322Address(_, _) = badAddr
    }
  }
}
