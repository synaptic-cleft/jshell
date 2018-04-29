new Throwable().getStackTrace()
StackWalker.getInstance().forEach(System.out::println)
StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).forEach(s -> System.out.println(s.getClassName()))