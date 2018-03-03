package com.sksamuel.avro4s
import reflect.runtime.universe._

object ScalaEnumHelper {

  def shortName(enumClass: Class[_]):String = {
    val parts = enumClass.getName.split('$')
    parts.last
  }

  def enumClassName(enumObjectSymbol: Symbol, addDollarToEnd: Boolean = true): String = {
    def params(o: Symbol) = Seq(o.isType, o.isPackage, o.isClass, o.isModuleClass)

    def needsDollarInName(o: Symbol) = params(o) == Seq(true, false, true, true)

    def recNamesPrint(nameSymbol: Symbol): List[String] = {
      nameSymbol match {
        case `NoSymbol` => Nil
        case o if needsDollarInName(o) => s"$$${o.name}" :: recNamesPrint(o.owner)
        case o => s"${o.name}." :: recNamesPrint(o.owner)
      }
    }

    val name = recNamesPrint(enumObjectSymbol).reverse.tail.mkString("").replace(".$", ".")
    if (addDollarToEnd) name + "$" else name
  }
}
