package lila.blockchain

import com.typesafe.config.Config
import com.wavesplatform.wavesj._

final class Env(
    config: Config
) {

  val Mainnet = "mainnet"
  val Testnet = "testnet"

  private val settings = new {
    val WavesNodeNetwork = config getString "waves.node.network"
    val WavesNodeUrl = config getString "waves.node.url"
    val WavesFee = config getInt "waves.fee"
    val WavesMasterAssetId = config getString "waves.master.assetId"
    val WavesMasterSeed = config getString "waves.master.seed"
    val WavesStarterAmount = config getInt "waves.starter.amount"
  }

  import settings._

  val assetId = WavesMasterAssetId
  val fee = WavesFee
  val starterAmount = WavesStarterAmount

  def getMasterKeyAccount(): PrivateKeyAccount = {
    PrivateKeyAccount.fromSeed(WavesMasterSeed, 0, getNodeType)
  }

  def getNode(): Node = {
    val nodeType = getNodeType
    new Node(WavesNodeUrl, nodeType)
  }

  def getNodeType(): Byte = {
    if (WavesNodeNetwork.toLowerCase == Mainnet) {
      Account.MAINNET
    } else {
      Account.TESTNET
    }
  }
}

object Env {
  lazy val current = "blockchain" boot new Env(
    config = lila.common.PlayApp loadConfig "blockchain"
  )
}
