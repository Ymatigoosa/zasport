package com.github.zasport.wallet.api

import java.time.Instant

import sttp.tapir._
import sttp.tapir.json.circe._
import Protocol._
import sttp.model.StatusCode

object Schema {

  private val authHeader = header[String]("X-Auth-Token").description("The token is 'secret'")

  //private val xb3TraceIdHeader      = header[String]("X-B3-TraceId")
  //private val xb3ParentSpanIdHeader = header[String]("X-B3-ParentSpanId")
  //private val xb3SpanIdHeader       = header[String]("X-B3-SpanId")
  //private val xb3SampledHeader      = header[String]("X-B3-Sampled")

  private val baseEndpoint =
    endpoint
      .in("api" / "v1")
      .errorOut(
        oneOf[ErrorInfo](
          statusMapping(StatusCode.NotFound, jsonBody[NotFound].description("not found")),
          statusMapping(StatusCode.Unauthorized, jsonBody[Unauthorized].description("unauthorized")),
          statusMapping(StatusCode.BadRequest, jsonBody[BadRequest].description("bad request")),
          statusMapping(StatusCode.InternalServerError, jsonBody[InternalError].description("unknown"))
        )
      )
      .in(authHeader)

  private val walletEndpoint = baseEndpoint.in("wallet")

  private val bodyWalletCard = jsonBody[WalletCard]
    .description("Card account")
    .example(
      WalletCard(
        new WalletId("1234"),
        BigDecimal("123"),
        BigDecimal("133"),
        BigDecimal("10"),
        new Currency("USD")
      )
    )
  private val bodyWalletDeposit = jsonBody[WalletDeposit]
    .description("Deposit account")
    .example(
      WalletDeposit(
        new WalletId("1234"),
        BigDecimal("123"),
        new Currency("USD"),
        Instant.ofEpochSecond(123)
      )
    )
  private val bodyWalletEmpty: EndpointIO.Body[WalletEmpty, CodecFormat.Json, _] = jsonBody[WalletEmpty]
    .description("Unknown account")
    .example(
      WalletEmpty(
        new WalletId("1234")
      )
    )

  val addFundsEndpoint = walletEndpoint.post
    .in("add-funds")
    .in(
      jsonBody[AddFunds]
        .description("Funds to be added")
        .example(AddFunds(new WalletId("1234"), BigDecimal("123"), Some(new Currency("USD"))))
    )
    .out(
      oneOf[WalletEntity](
        statusMapping(StatusCode.Ok, bodyWalletCard),
        statusMapping(StatusCode.Ok, bodyWalletDeposit),
        statusDefaultMapping(bodyWalletEmpty)
      )
    )

}
