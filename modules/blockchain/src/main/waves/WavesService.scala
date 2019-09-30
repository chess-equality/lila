package lila.blockchain.waves

import scala.math.pow

import lila.blockchain.Env
import com.wavesplatform.wavesj._

object WavesService {

  val asset: AssetDetails = {
    Env.current.node.getAssetDetails(Env.current.assetId)
  }

  def createWallet: WavesWallet = {

    val (privateKeyAccount, seed) = createPrivateKeyAccount
    val receipt = giveStarterTokens(privateKeyAccount.getAddress)

    println(s">>>>>>>>>> seed = ${seed}")
    println(s">>>>>>>>>> address = ${privateKeyAccount.getAddress}")
    println(s">>>>>>>>>> receipt = ${receipt}")

    WavesWallet.make(
      address = privateKeyAccount.getAddress,
      seed = seed
    )
  }

  def createPrivateKeyAccount: (PrivateKeyAccount, String) = {

    val seed = WavesUtil.generatePassPhrase

    (PrivateKeyAccount.fromSeed(seed, 0, Env.current.nodeType), seed)
  }

  def giveStarterTokens(destinationAddress: String): String = {

    val sendTx = Transactions.makeTransferTx(
      Env.current.masterKeyAccount, destinationAddress,
      Env.current.starterAmount * Asset.TOKEN,
      Env.current.assetId, Env.current.fee, Env.current.assetId,
      ""
    )

    Env.current.node.send(sendTx)
  }

  def getBalance(address: String): String = {
    "%,.8f".format(
      (Env.current.node.getBalance(address).toDouble)./(pow(10, 8))
    )
  }

  def getAssetBalance(address: String): String = {
    val decimals = asset.getDecimals
    s"%,.${decimals}f".format(
      (Env.current.node.getBalance(address, Env.current.assetId).toDouble)./(pow(10, decimals.toDouble))
    )
  }
}
