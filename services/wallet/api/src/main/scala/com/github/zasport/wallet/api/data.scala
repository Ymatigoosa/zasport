package com.github.zasport.wallet.api

import java.time.Instant

sealed trait ErrorInfo
case class NotFound(msg: String)                    extends ErrorInfo
case class BadRequest(msg: String)                  extends ErrorInfo
case class Unauthorized(msg: String)                extends ErrorInfo
case class InternalError(code: String, msg: String) extends ErrorInfo

class WalletId(val value: String) extends AnyVal
class Currency(val value: String) extends AnyVal

case class AddFunds(walletId: WalletId, amount: BigDecimal, currency: Option[Currency])
case class RemoveFunds(walletId: WalletId, amount: BigDecimal, currency: Option[Currency])

sealed trait WalletEntity

case class WalletCard(
  walletId: WalletId,
  available: BigDecimal,
  fullAmount: BigDecimal,
  blocked: BigDecimal,
  currency: Currency
) extends WalletEntity
case class WalletDeposit(walletId: WalletId, amount: BigDecimal, currency: Currency, until: Instant)
    extends WalletEntity
case class WalletEmpty(walletId: WalletId) extends WalletEntity
