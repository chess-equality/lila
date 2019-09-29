package lila.blockchain.waves

case class WavesWallet(
    address: String,
    seed: String
) {}

object WavesWallet {

  def make(
    address: String,
    seed: String
  ): WavesWallet = WavesWallet(
    address = address,
    seed = seed
  )
}

