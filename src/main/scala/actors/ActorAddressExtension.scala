package actors

import akka.actor.{ActorSystem, Address, ExtendedActorSystem, Extension, ExtensionId}

/**
  * The extension class required for akka system.
  * @param system
  */
class AddressExtension(system: ExtendedActorSystem) extends Extension {
  val address: Address = system.provider.getDefaultAddress
}

/**
  * The object that extends the actor system in order to get host and port of an actor.
  */
object AddressExtension extends ExtensionId[AddressExtension] {

  override def createExtension(system: ExtendedActorSystem): AddressExtension = new AddressExtension(system)

  /**
    * Get the host of the actor.
    * @param system
    * @return
    */
  def hostOf(system: ActorSystem): String = AddressExtension(system).address.host.getOrElse("")

  /**
    * Get the port of the actor.
    * @param system
    * @return
    */
  def portOf(system: ActorSystem): Int    = AddressExtension(system).address.port.getOrElse(0)

}
