package lila.blockchain.waves

import lila.blockchain.Env
import com.wavesplatform.wavesj._

object WavesService {

  def createWallet: WavesWallet = {

    val (privateKeyAccount, seed) = createPrivateKeyAccount
    val receipt = giveStarterTokens(privateKeyAccount.getAddress)

    println(s">>>>>>>>>> seed = ${seed}")
    println(s">>>>>>>>>> address = ${privateKeyAccount.getAddress}")
    println(s">>>>>>>>>> receipt = ${receipt}")

    val wavesWallet = WavesWallet.make(
      address = privateKeyAccount.getAddress,
      seed = seed
    )
    wavesWallet
  }

  def createPrivateKeyAccount: (PrivateKeyAccount, String) = {

    val seed = WavesUtil.generatePassPhrase

    val walletTuple = (PrivateKeyAccount.fromSeed(seed, 0, Env.current.getNodeType), seed)
    walletTuple
  }

  def giveStarterTokens(destinationAddress: String): String = {

    val sendTx = Transactions.makeTransferTx(
      Env.current.getMasterKeyAccount, destinationAddress,
      Env.current.starterAmount * Asset.TOKEN,
      Env.current.assetId, Env.current.fee, Env.current.assetId,
      ""
    )

    val receipt = Env.current.getNode.send(sendTx)
    receipt
  }
}
