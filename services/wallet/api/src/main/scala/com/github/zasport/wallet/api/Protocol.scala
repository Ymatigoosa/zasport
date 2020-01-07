package com.github.zasport.wallet.api

import java.time.Instant

import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax._

import scala.util.Try

object Protocol {
  implicit val encoderWalletId: Encoder[WalletId] = Encoder.encodeString.contramap(_.value)
  implicit val decoderWalletId: Decoder[WalletId] = Decoder.decodeString.map(s => new WalletId(s))

  implicit val encoderCurrency: Encoder[Currency] = Encoder.encodeString.contramap(_.value)
  implicit val decoderCurrency: Decoder[Currency] = Decoder.decodeString.map(s => new Currency(s))

  implicit val encoderInstant: Encoder[Instant] = Encoder.encodeString.contramap(_.toString)
  implicit val decoderInstant: Decoder[Instant] = Decoder.decodeString.emapTry(s => Try(Instant.parse(s)))

  implicit val codecAddFunds: Codec.AsObject[AddFunds] = deriveCodec
  implicit val codecRemoveFunds: Codec.AsObject[RemoveFunds] = deriveCodec

  implicit val codecWalletCard: Codec.AsObject[WalletCard]       = deriveCodec
  implicit val codecWalletDeposit: Codec.AsObject[WalletDeposit] = deriveCodec
  implicit val codecWalletEmpty: Codec.AsObject[WalletEmpty]     = deriveCodec

  implicit val codecWalletEntity: Codec.AsObject[WalletEntity] = new Codec.AsObject[WalletEntity] {
    final def encodeObject(a: WalletEntity): JsonObject = {
      a match {
        case x: WalletCard    => x.asJsonObject.add("type", Json.fromString("card"))
        case x: WalletDeposit => x.asJsonObject.add("type", Json.fromString("deposit"))
        case x: WalletEmpty   => x.asJsonObject.add("type", Json.fromString("empty"))
      }
    }

    final def apply(c: HCursor): Decoder.Result[WalletEntity] = {
      c.downField("type").as[String].flatMap {
        case "card"    => c.as[WalletCard]
        case "deposit" => c.as[WalletDeposit]
        case "empty"   => c.as[WalletEmpty]
      }
    }
  }

  implicit val codecNotFound: Codec.AsObject[NotFound] = deriveCodec
  implicit val codecBadRequest: Codec.AsObject[BadRequest] = deriveCodec
  implicit val codecUnauthorized: Codec.AsObject[Unauthorized] = deriveCodec
  implicit val codecInternalError: Codec.AsObject[InternalError] = deriveCodec

  implicit val codecErrorInfo: Codec.AsObject[ErrorInfo] = new Codec.AsObject[ErrorInfo] {
    final def encodeObject(a: ErrorInfo): JsonObject = {
      a match {
        case x: NotFound    => x.asJsonObject.add("code", Json.fromString("NOT_FOUND"))
        case x: BadRequest => x.asJsonObject.add("code", Json.fromString("BAD_REQUEST"))
        case x: Unauthorized   => x.asJsonObject.add("code", Json.fromString("UNAUTHORIZED"))
        case x: InternalError   => x.asJsonObject.add("code", Json.fromString("UNKNOWN"))
      }
    }

    final def apply(c: HCursor): Decoder.Result[ErrorInfo] = {
      c.downField("code").as[String].flatMap {
        case "NOT_FOUND"    => c.as[NotFound]
        case "BAD_REQUEST" => c.as[BadRequest]
        case "UNAUTHORIZED"   => c.as[Unauthorized]
        case "UNKNOWN"   => c.as[InternalError]
      }
    }
  }
}
