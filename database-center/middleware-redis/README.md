# Redis分布式锁

## 前言

分布式锁一般有三种实现方式：
1. 基于RDB乐观锁，DB 的唯一索引。；
2. 基于Redis的分布式锁，Redis 的 NX EX 参数。；
3. 基于ZooKeeper的分布式锁，ZK 的临时有序节点。

## 可靠性

首先，为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下四个条件：

互斥性。在任意时刻，只有一个客户端能持有锁。

不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。

具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。

解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。

## 特性

高性能(加、解锁时高性能)

可以使用阻塞锁与非阻塞锁。

不能出现死锁。

可用性(不能出现节点 down 掉后加锁失败)。


## 参考文章

1. 代码大部分基于这个写的：[Redis分布式锁的正确实现方式（Java版）](https://wudashan.cn/2017/10/23/Redis-Distributed-Lock-Implement/)

2. 阻塞锁和非阻塞锁：[基于 Redis 的分布式锁](https://crossoverjie.top/2018/03/29/distributed-lock/distributed-lock-redis/)

3. 通过TheadLocal实现加锁和解锁是同一个客户端：[「分布式」实现分布式锁的正确姿势](https://mp.weixin.qq.com/s?__biz=MzA3ODQ0Mzg2OA==&mid=2649048309&idx=1&sn=e7a55ba98bc0078e07c99d8ee1f37dc5&chksm=875340c6b024c9d04612bf41b93ece089235a2fc870c6b233fa89c127251065df4b3bfed100a&mpshare=1&scene=1&srcid=0925UVLdxv04BlVlxqsiaZgt#)
 
4. 通过注解进一步封装，提供了很好的思路：[基于redis的分布式共享锁，使用注解的方式对方法加锁](https://gitee.com/lsongiu/redis-shared-lock)