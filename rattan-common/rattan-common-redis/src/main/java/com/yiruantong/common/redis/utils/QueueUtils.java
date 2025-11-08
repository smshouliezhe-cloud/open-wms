package com.yiruantong.common.redis.utils;

import com.yiruantong.common.core.utils.SpringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.redisson.api.*;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 分布式队列工具
 * 轻量级队列 重量级数据量 请使用 MQ
 * 要求 redis 5.X 以上
 * 在 Queue 中 poll()和 remove()有什么区别？
 * 答：poll()和remove()都将移除并且返回对头，但是在poll()在队列为空时返回null，而remove()会抛出NoSuchElementException异常。
 * 拓展：
 * 1、offer()和add()的区别
 * add()和offer()都是向队列中添加一个元素。但是如果想在一个满的队列中加入一个新元素，调用 add() 方法就会抛出一个 unchecked 异常，而调用 offer() 方法会返回 false。可以据此在程序中进行有效的判断！
 * 2、peek()和element()的区别
 * peek()和element()都将在不移除的情况下返回队头，但是peek()方法在队列为空时返回null，调用element()方法会抛出NoSuchElementException异常。
 * take():获取并移除队列的超时元素，如果没有则wait当前线程，直到有元素满足超时条件，返回结果。
 *
 * @author YiRuanTong
 * @version 3.6.0 新增
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueueUtils {

  private static final RedissonClient CLIENT = SpringUtils.getBean(RedissonClient.class);


  /**
   * 获取客户端实例
   */
  public static RedissonClient getClient() {
    return CLIENT;
  }

  /**
   * 添加普通队列数据
   *
   * @param queueName 队列名
   * @param data      数据
   */
  public static <T> boolean addQueueObject(String queueName, T data) {
    RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
    return queue.offer(data);
  }

  /**
   * 通用获取一个队列数据 没有数据返回 null(不支持延迟队列)
   *
   * @param queueName 队列名
   */
  public static <T> T getQueueObject(String queueName) {
    RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
    return queue.poll();
  }

  /**
   * 通用删除队列数据(不支持延迟队列)
   */
  public static <T> boolean removeQueueObject(String queueName, T data) {
    RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
    return queue.remove(data);
  }

  /**
   * 通用销毁队列 所有阻塞监听 报错(不支持延迟队列)
   */
  public static <T> boolean destroyQueue(String queueName) {
    RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
    return queue.delete();
  }

  /**
   * 添加延迟队列数据 默认毫秒
   *
   * @param queueName 队列名
   * @param data      数据
   * @param time      延迟时间
   */
  public static <T> void addDelayedQueueObject(String queueName, T data, long time) {
    addDelayedQueueObject(queueName, data, time, TimeUnit.MILLISECONDS);
  }

  /**
   * 添加延迟队列数据
   *
   * @param queueName 队列名
   * @param data      数据
   * @param time      延迟时间
   * @param timeUnit  单位
   */
  public static <T> void addDelayedQueueObject(String queueName, T data, long time, TimeUnit timeUnit) {
    RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
    RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
    delayedQueue.offer(data, time, timeUnit);
  }

  /**
   * 获取一个延迟队列数据 没有数据返回 null
   *
   * @param queueName 队列名
   */
  public static <T> T getDelayedQueueObject(String queueName) {
    RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
    RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
    return delayedQueue.poll();
  }

  /**
   * 删除延迟队列数据
   */
  public static <T> boolean removeDelayedQueueObject(String queueName, T data) {
    RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
    RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
    return delayedQueue.remove(data);
  }

  /**
   * 销毁延迟队列 所有阻塞监听 报错
   */
  public static <T> void destroyDelayedQueue(String queueName) {
    RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
    RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
    delayedQueue.destroy();
  }

  /**
   * 添加优先队列数据
   *
   * @param queueName 队列名
   * @param data      数据
   */
  public static <T> boolean addPriorityQueueObject(String queueName, T data) {
    RPriorityBlockingQueue<T> priorityBlockingQueue = CLIENT.getPriorityBlockingQueue(queueName);
    return priorityBlockingQueue.offer(data);
  }

  /**
   * 优先队列获取一个队列数据 没有数据返回 null(不支持延迟队列)
   *
   * @param queueName 队列名
   */
  public static <T> T getPriorityQueueObject(String queueName) {
    RPriorityBlockingQueue<T> queue = CLIENT.getPriorityBlockingQueue(queueName);
    return queue.poll();
  }

  /**
   * 优先队列删除队列数据(不支持延迟队列)
   */
  public static <T> boolean removePriorityQueueObject(String queueName, T data) {
    RPriorityBlockingQueue<T> queue = CLIENT.getPriorityBlockingQueue(queueName);
    return queue.remove(data);
  }

  /**
   * 优先队列销毁队列 所有阻塞监听 报错(不支持延迟队列)
   */
  public static <T> boolean destroyPriorityQueue(String queueName) {
    RPriorityBlockingQueue<T> queue = CLIENT.getPriorityBlockingQueue(queueName);
    return queue.delete();
  }

  /**
   * 尝试设置 有界队列 容量 用于限制数量
   *
   * @param queueName 队列名
   * @param capacity  容量
   */
  public static <T> boolean trySetBoundedQueueCapacity(String queueName, int capacity) {
    RBoundedBlockingQueue<T> boundedBlockingQueue = CLIENT.getBoundedBlockingQueue(queueName);
    return boundedBlockingQueue.trySetCapacity(capacity);
  }

  /**
   * 尝试设置 有界队列 容量 用于限制数量
   *
   * @param queueName 队列名
   * @param capacity  容量
   * @param destroy   已存在是否销毁
   */
  public static <T> boolean trySetBoundedQueueCapacity(String queueName, int capacity, boolean destroy) {
    RBoundedBlockingQueue<T> boundedBlockingQueue = CLIENT.getBoundedBlockingQueue(queueName);
    if (boundedBlockingQueue.isExists() && destroy) {
      destroyQueue(queueName);
    }
    return boundedBlockingQueue.trySetCapacity(capacity);
  }

  /**
   * 添加有界队列数据
   *
   * @param queueName 队列名
   * @param data      数据
   * @return 添加成功 true 已达到界限 false
   */
  public static <T> boolean addBoundedQueueObject(String queueName, T data) {
    RBoundedBlockingQueue<T> boundedBlockingQueue = CLIENT.getBoundedBlockingQueue(queueName);
    return boundedBlockingQueue.offer(data);
  }

  /**
   * 有界队列获取一个队列数据 没有数据返回 null(不支持延迟队列)
   *
   * @param queueName 队列名
   */
  public static <T> T getBoundedQueueObject(String queueName) {
    RBoundedBlockingQueue<T> queue = CLIENT.getBoundedBlockingQueue(queueName);
    return queue.poll();
  }

  /**
   * 有界队列删除队列数据(不支持延迟队列)
   */
  public static <T> boolean removeBoundedQueueObject(String queueName, T data) {
    RBoundedBlockingQueue<T> queue = CLIENT.getBoundedBlockingQueue(queueName);
    return queue.remove(data);
  }

  /**
   * 有界队列销毁队列 所有阻塞监听 报错(不支持延迟队列)
   */
  public static <T> boolean destroyBoundedQueue(String queueName) {
    RBoundedBlockingQueue<T> queue = CLIENT.getBoundedBlockingQueue(queueName);
    return queue.delete();
  }

  /**
   * 订阅阻塞队列(可订阅所有实现类 例如: 延迟 优先 有界 等)
   */
  public static <T> void subscribeBlockingQueue(String queueName, Consumer<T> consumer) {
    RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
    queue.subscribeOnElements(consumer);
  }

}
