package model

import scala.util.Random

/**
  * A random generator of chars.
  */
object RandomGenerator extends ( () => Char ) {

  val dictionary: String = "aabcdeefghiijklmnoopqrstuuvwxyz"
  val random: Random = new Random()

  override def apply(): Char = dictionary.charAt(random.nextInt(dictionary.length()))
}
