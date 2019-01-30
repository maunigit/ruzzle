package model

import scala.util.Random

object RandomGenerator extends Function0[Char] {

  val dictionary: String = "abcdefghijklmnopqrstuvwxyz"
  val random: Random = new Random()

  override def apply(): Char = dictionary.charAt(random.nextInt(dictionary.length()))
}
