package lila.user.waves

import lila.db.BSON
import ornicar.scalalib.Random
import reactivemongo.bson.BSONDocument

case class WavesWallet(
    _id: String,
    address: String,
    seed: String
) {}

object WavesWallet {

  val idSize = 16

  def make(
    address: String,
    seed: String
  ): WavesWallet = WavesWallet(
    _id = Random nextString idSize,
    address = address,
    seed = seed
  )

  private[user] val wavesWalletBSONHandler = new BSON[WavesWallet] {

    def reads(r: BSON.Reader): WavesWallet = WavesWallet(
      _id = r str "_id",
      address = r str "address",
      seed = r str "seed"
    )

    def writes(w: BSON.Writer, o: WavesWallet) = BSONDocument(
      "_id" -> w.str(o._id),
      "address" -> w.str(o.address),
      "seed" -> w.str(o.seed)
    )
  }

  val default = WavesWallet("", "", "")
}
