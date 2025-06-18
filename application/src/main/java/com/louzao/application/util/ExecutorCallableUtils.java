package com.louzao.application.util;

import static com.louzao.application.util.Utils.setMdcContext;
import io.micrometer.context.ContextSnapshot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
public class ExecutorCallableUtils {

  public static final int DEFAULT_TIMEOUT = 60;
  private static final int DEFAULT_POOL_SIZE = 20;
  private static ExecutorService contextPropagatingExecutor;

  private static ExecutorService executorServiceCached = Executors.newCachedThreadPool();

  private static ExecutorService executorServiceFixed = Executors
      .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  private static ExecutorService executorServiceSingle = Executors.newSingleThreadExecutor();

  public static ExecutorService obtainExecutorSingle() {
    if (executorServiceSingle.isShutdown()) {
      executorServiceSingle = Executors.newSingleThreadExecutor();
    }

    return executorServiceSingle;
  }

  public static ExecutorService obtainExecutor(boolean fixedThreads) {
    ExecutorService executorService;

    if (fixedThreads) {
      executorService = executorServiceFixed;

      if (executorService.isShutdown()) {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorServiceFixed = executorService;
      }
    } else {
      executorService = executorServiceCached;

      if (executorService.isShutdown()) {
        executorService = Executors.newCachedThreadPool();
        executorServiceCached = executorService;
      }
    }
    return executorService;
  }

  public static <T> ExecutorService callExecutor(List<Callable<T>> listCallable,
      boolean invalidateAllCallableWithException, boolean fixedThreads)
      throws InterruptedException {
    ExecutorService executorService = obtainExecutor(fixedThreads);
    try {
      callExecutor(executorService, listCallable, invalidateAllCallableWithException);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      shutdownAndAwaitTermination(executorService);
      throw e;
    }

    return executorService;
  }

  private static <T> ExecutorService callExecutor(ExecutorService executorService, List<Callable<T>> listCallable,
      boolean invalidateAllCallableWithException)
      throws InterruptedException {
    try {
      executorService.invokeAll(getMdcCallable(executorService, listCallable, invalidateAllCallableWithException));

      if (executorService.isShutdown()) {
        throw new InterruptedException("Error ExecutorService");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      shutdownAndAwaitTermination(executorService);
      throw e;
    }

    return executorService;
  }

  public static ExecutorService callExecutor(Runnable runnable, boolean invalidateAllCallableWithException,
      boolean fixedThreads) {
    ExecutorService executorService = obtainExecutor(fixedThreads);

    try {
      executorService.execute(getMdcRunnable(executorService, runnable, invalidateAllCallableWithException));
    } catch (Exception e) {
      log.error("Error ExecutorService", e);
      shutdownAndAwaitTermination(executorService);
      throw e;
    }

    return executorService;
  }

  public static ExecutorService callExecutorSingle(Runnable runnable) {
    try {
      if (executorServiceSingle.isShutdown()) {
        executorServiceSingle = Executors.newSingleThreadExecutor();
      }

      executorServiceSingle.execute(getMdcRunnable(executorServiceSingle, runnable, false));
    } catch (Exception e) {
      log.error("Error ExecutorServiceSingle", e);
      shutdownAndAwaitTermination(executorServiceSingle);
      throw e;
    }

    return executorServiceSingle;
  }

  public static <T> List<Callable<T>> getMdcCallable(ExecutorService executorService, List<Callable<T>> listCallable,
      boolean invalidateAllCallableWithException) {
    List<Callable<T>> resultCallable = new ArrayList<>();

    Map<String, String> contextMap = MDC.getCopyOfContextMap();

    for (Callable<T> callable : listCallable) {
      Callable<T> item = () -> {
        setMdcContext(contextMap);

        try {
          if (invalidateAllCallableWithException) {
            try {
              return callable.call();
            } catch (Exception e) {
              log.error(e.getMessage(), e);
              shutdownAndAwaitTermination(executorService);
              throw e;
            }
          }

          return callable.call();
        } finally {
          // once the task is complete, clear MDC
          MDC.clear();
        }
      };

      resultCallable.add(item);
    }

    return resultCallable;
  }

  public static Runnable getMdcRunnable(ExecutorService executorService, Runnable runnable,
      boolean invalidateAllCallableWithException) {
    Map<String, String> contextMap = MDC.getCopyOfContextMap();

    return () -> {
      setMdcContext(contextMap);
      try {
        if (invalidateAllCallableWithException) {
          try {
            runnable.run();
          } catch (Exception e) {
            log.error(e.getMessage(), e);
            shutdownAndAwaitTermination(executorService);
            throw e;
          }
        }

        runnable.run();
      } finally {
        // once the task is complete, clear MDC
        MDC.clear();
      }
    };
  }

  public static ExecutorService obtainContextPropagatingExecutor(Integer poolSize) {
    if (contextPropagatingExecutor == null || contextPropagatingExecutor.isShutdown()) {

      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(poolSize != null ? poolSize : DEFAULT_POOL_SIZE);
      executor.setWaitForTasksToCompleteOnShutdown(true);
      executor.initialize();

      ExecutorService delegate = executor.getThreadPoolExecutor();

      contextPropagatingExecutor = new ExecutorService() {
        @Override
        public <T> Future<T> submit(Callable<T> task) {
          ContextSnapshot snapshot = ContextSnapshot.captureAll();
          return delegate.submit(() -> snapshot.wrap(task).call());
        }

        @Override
        public Future<?> submit(Runnable task) {
          ContextSnapshot snapshot = ContextSnapshot.captureAll();
          return delegate.submit(snapshot.wrap(task));
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
          ContextSnapshot snapshot = ContextSnapshot.captureAll();
          return delegate.submit(snapshot.wrap(task), result);
        }

        // Delegar los demás métodos directamente
        @Override
        public void shutdown() {
          delegate.shutdown();
        }

        @Override
        public List<Runnable> shutdownNow() {
          return delegate.shutdownNow();
        }

        @Override
        public boolean isShutdown() {
          return delegate.isShutdown();
        }

        @Override
        public boolean isTerminated() {
          return delegate.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
          return delegate.awaitTermination(timeout, unit);
        }

        @Override
        public void execute(Runnable command) {
          ContextSnapshot snapshot = ContextSnapshot.captureAll();
          delegate.execute(snapshot.wrap(command));
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
          return delegate.invokeAll(wrapAll(tasks));
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
          return delegate.invokeAll(wrapAll(tasks), timeout, unit);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
          return delegate.invokeAny(wrapAll(tasks));
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
          return delegate.invokeAny(wrapAll(tasks), timeout, unit);
        }

        private <T> Collection<? extends Callable<T>> wrapAll(Collection<? extends Callable<T>> tasks) {
          ContextSnapshot snapshot = ContextSnapshot.captureAll();
          List<Callable<T>> wrapped = new ArrayList<>();
          for (Callable<T> task : tasks) {
            wrapped.add(() -> snapshot.wrap(task).call());
          }
          return wrapped;
        }
      };
    }

    return contextPropagatingExecutor;
  }

  public static void callCompletionServiceExecutor(List<Callable<Void>> tasks, boolean fixedThreads)
      throws InterruptedException {
    ExecutorService executorService = obtainExecutor(fixedThreads);
    CompletionService<Void> completionService = new ExecutorCompletionService<>(executorService);

    try {
      tasks.forEach(completionService::submit);

      tasks.forEach(ftr -> {
        Future<Void> future = null;
        try {
          future = completionService.poll(60, TimeUnit.SECONDS);
          if (future != null) {
            future.get();
          } else {
            throw new InterruptedException("Timeout getting response from api.");
          }
        } catch (InterruptedException | ExecutionException e) {
          throw new RuntimeException(e);
        }
      });
    } catch (Exception e) {
      throw new InterruptedException(e.getMessage());
    } finally {
      shutdownAndAwaitTermination(executorService);
    }
  }


  public static void callCompletionServiceExecutorWithTraceId(List<Callable<Void>> tasks, Integer timeout,
      Integer poolSize)
      throws InterruptedException {
    ExecutorService executorService = obtainContextPropagatingExecutor(poolSize);
    CompletionService<Void> completionService = new ExecutorCompletionService<>(executorService);

    List<Future<Void>> futures = new ArrayList<>();

    try {
      for (Callable<Void> task : tasks) {
        ContextSnapshot snapshot = ContextSnapshot.captureAll();
        Callable<Void> contextAwareTask = () -> snapshot.wrap(task).call(); // wrap con contexto
        futures.add(completionService.submit(contextAwareTask));
      }

      for (int i = 0; i < futures.size(); i++) {
        Future<Void> future = completionService.poll(
            timeout != null ? timeout : DEFAULT_TIMEOUT,
            TimeUnit.SECONDS
        );
        if (future != null) {
          try {
            future.get();
          } catch (ExecutionException e) {
            throw new RuntimeException("Error ejecutando tarea", e.getCause());
          }
        } else {
          throw new InterruptedException("Timeout esperando respuesta de callable.");
        }
      }

    } finally {
      shutdownAndAwaitTermination(executorService, timeout);
    }
  }

  public static void shutdownAndAwaitTermination(Executor executor, Integer timeout) {
    if (executor instanceof ExecutorService) {
      ExecutorService executorService = (ExecutorService) executor;
      executorService.shutdown(); // Disable new tasks from being submitted

      try {
        // Wait a while for existing tasks to terminate
        if (!executorService.awaitTermination(timeout != null ? timeout : DEFAULT_TIMEOUT, TimeUnit.SECONDS)) {
          executorService.shutdownNow(); // Cancel currently executing tasks
          // Wait a while for tasks to respond to being cancelled
          if (!executorService.awaitTermination(timeout != null ? timeout : DEFAULT_TIMEOUT, TimeUnit.SECONDS)) {
            log.error("Executor Service did not terminate");
          }
        }
      } catch (InterruptedException ie) {
        // (Re-)Cancel if current thread also interrupted
        executorService.shutdownNow();
        // Preserve interrupt status
        Thread.currentThread().interrupt();
      }
    }
  }

  public static void shutdownAndAwaitTermination(ExecutorService executorService) {
    if (executorService != null) {
      executorService.shutdown(); // Disable new tasks from being submitted

      try {
        // Wait a while for existing tasks to terminate
        if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
          executorService.shutdownNow(); // Cancel currently executing tasks
          // Wait a while for tasks to respond to being cancelled
          if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
            log.error("Executor Service did not terminate");
          }
        }
      } catch (InterruptedException ie) {
        // (Re-)Cancel if current thread also interrupted
        executorService.shutdownNow();
        // Preserve interrupt status
        Thread.currentThread().interrupt();
      }
    }
  }
}
