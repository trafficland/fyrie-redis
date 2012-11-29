package net.fyrie.redis
package commands

import serialization._
import akka.util.ByteString
import concurrent.ExecutionContext

private[redis] trait Sets[Result[_]] {
  this: Commands[Result] ⇒
  import protocol.Constants._

  def sadd[K: Store, V: Store](key: K, value: V)(implicit executor: ExecutionContext): Result[Boolean] =
    send(SADD :: Store(key) :: Store(value) :: Nil)

  def scard[K: Store](key: K)(implicit executor: ExecutionContext): Result[Int] =
    send(SCARD :: Store(key) :: Nil)

  def sdiff[A: Store, B: Store](key: A, diffkeys: Iterable[B])(implicit executor: ExecutionContext): Result[Set[ByteString]] =
    send(SDIFF :: Store(key) :: (diffkeys.map(Store(_))(collection.breakOut): List[ByteString]))

  def sdiffstore[A: Store, B: Store, C: Store](dstkey: A, key: B, diffkeys: Iterable[C])(implicit executor: ExecutionContext): Result[Int] =
    send(SDIFFSTORE :: Store(dstkey) :: Store(key) :: (diffkeys.map(Store(_))(collection.breakOut): List[ByteString]))

  def sinter[K: Store](keys: Iterable[K])(implicit executor: ExecutionContext): Result[Set[ByteString]] =
    send(SINTER :: (keys.map(Store(_))(collection.breakOut): List[ByteString]))

  def sinterstore[A: Store, B: Store](dstkey: A, keys: Iterable[B])(implicit executor: ExecutionContext): Result[Int] =
    send(SINTERSTORE :: Store(dstkey) :: (keys.map(Store(_))(collection.breakOut): List[ByteString]))

  def sismember[K: Store, V: Store](key: K, value: V)(implicit executor: ExecutionContext): Result[Boolean] =
    send(SISMEMBER :: Store(key) :: Store(value) :: Nil)

  def smembers[K: Store](key: K)(implicit executor: ExecutionContext): Result[Set[ByteString]] =
    send(SMEMBERS :: Store(key) :: Nil)

  def smove[A: Store, B: Store, V: Store](srcKey: A, dstKey: B, value: V)(implicit executor: ExecutionContext): Result[Boolean] =
    send(SMOVE :: Store(srcKey) :: Store(dstKey) :: Store(value) :: Nil)

  def spop[K: Store](key: K)(implicit executor: ExecutionContext): Result[Option[ByteString]] =
    send(SPOP :: Store(key) :: Nil)

  def srandmember[K: Store](key: K)(implicit executor: ExecutionContext): Result[Option[ByteString]] =
    send(SRANDMEMBER :: Store(key) :: Nil)

  def srem[K: Store, V: Store](key: K, value: V)(implicit executor: ExecutionContext): Result[Boolean] =
    send(SREM :: Store(key) :: Store(value) :: Nil)

  def sunion[K: Store](keys: Iterable[K])(implicit executor: ExecutionContext): Result[Set[ByteString]] =
    send(SUNION :: (keys.map(Store(_))(collection.breakOut): List[ByteString]))

  def sunionstore[A: Store, B: Store](dstkey: A, keys: Iterable[B])(implicit executor: ExecutionContext): Result[Int] =
    send(SUNIONSTORE :: Store(dstkey) :: (keys.map(Store(_))(collection.breakOut): List[ByteString]))
}
