package com.sksamuel.avro4s


import com.sksamuel.avro4s.SampleProtocol.SubPart1.InnerEnum
import org.scalatest._

object TopEnum extends Enumeration {
  type TopEnumVal = Value
  val Val1: TopEnumVal = Value("v1")
  val Val2: TopEnumVal = Value("v2")
}

case class WithTopEnum(topEnum: TopEnum.TopEnumVal)


object SampleProtocol {
  object SubPart1 {
    object InnerEnum extends Enumeration {
      type InnerEnum = Value
      val Val1: InnerEnum = Value("vv1")
      val Val2: InnerEnum = Value("vv2")
    }
  }
}
case class WithInnerEnum(ie: InnerEnum.InnerEnum)

class ScalaEnumTest extends FunSpec with Matchers {

  describe("SchemaFor : FromRecord : ToRecord") {
    describe("with top level scala Enumerations") {
      val withTopEnum = WithTopEnum(TopEnum.Val1)
      it("should be able to compile `FromRecord`") {
        FromRecord[WithTopEnum] shouldNot be (null)
      }
      it("should be able to compile `ToRecord`") {
        ToRecord[WithTopEnum] shouldNot be (null)
      }
      it("should be able to compile `SchemaFor`") {
        SchemaFor[WithTopEnum] shouldNot be (null)
      }
      it("should serialize-deserialize correctly") {
        val fr = FromRecord[WithTopEnum]
        val tr = ToRecord[WithTopEnum]
        fr(tr(withTopEnum)) should be (withTopEnum)
      }
    }

    describe("with non-top level scala Enumerations") {
      val withInnerEnum = WithInnerEnum(InnerEnum.Val1)
      it("should be able to compile `FromRecord`") {
        FromRecord[WithInnerEnum] shouldNot be (null)
      }
      it("should be able to compile `ToRecord`") {
        ToRecord[WithInnerEnum] shouldNot be (null)
      }
      it("should be able to compile `SchemaFor`") {
        SchemaFor[WithInnerEnum] shouldNot be (null)
      }
      it("should serialize-deserialize correctly") {
        val fr = FromRecord[WithInnerEnum]
        val tr = ToRecord[WithInnerEnum]
        fr(tr(withInnerEnum)) should be (withInnerEnum)
      }
    }
  }
}
