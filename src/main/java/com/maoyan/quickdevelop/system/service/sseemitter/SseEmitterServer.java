package com.maoyan.quickdevelop.system.service.sseemitter;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SseEmitterServer {
  /**
   * 当前连接数
   */
  private static AtomicInteger count = new AtomicInteger(0);

  /**
   * 使用map对象，便于根据userId来获取对应的SseEmitter，或者放redis里面
   */
  private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();


  public static SseEmitter connect(String userId) {
    SseEmitter sseEmitter = new SseEmitter(0L);
    sseEmitter.onCompletion(completionCallBack(userId));
    sseEmitterMap.put(userId, sseEmitter);
    count.getAndIncrement();
    System.out.println("创建新的sse连接，当前用户: "+userId);
    return sseEmitter;
  }

  private static Runnable completionCallBack(String userId) {
    return () -> {
      System.out.println("连接结束" + userId);
    };
  }

  public static void sendMessage(String userId){
    if (sseEmitterMap.containsKey(userId)){
      try {
        sseEmitterMap.get(userId).send("hello");
      } catch (IOException e) {
        System.out.println("------------------异常-------------------");
        e.printStackTrace();
      }
    }
  }

}
