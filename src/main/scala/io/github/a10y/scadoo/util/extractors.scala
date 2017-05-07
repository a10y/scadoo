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

import java.util.regex.{ Pattern, PatternSyntaxException }

/**
  * Extractor implementation for a "host:port" pair.
  */
object HostPortPair {
  def unapply(pair: String): Option[(String, Long)] =
    try {
      val splitArgs = pair.split(":")
      for (host <- splitArgs.headOption;
           port <- splitArgs.tail.headOption) yield (host, port.toLong)
    } catch {
      case t: PatternSyntaxException => None
      case t: NumberFormatException  => None
    }
}

/**
  * Implementation of an extractor for RFC 5322-compliant email addresses.
  * See http://emailregex.com/ for more information.
  */
object RFC5322Address {
  private[this] val PATTERN: Pattern = Pattern.compile(
    "((?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\"))@((?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]))"
  )

  /**
    * Extract out an email address of the form (username)@(domain) into a (username, domain) tuple.
    * It only extracts if the email address is a valid RFC 5322 compliant address.
    * @param address The email address to extract on.
    * @return The tuple (username, domain) extracted from the email
    */
  def unapply(address: String): Option[(String, String)] = {
    val matcher = PATTERN.matcher(address)
    if (!matcher.matches()) {
      println("failed matching!!")
      None
    } else {
      Some(matcher.group(1), matcher.group(2))
    }
  }
}
