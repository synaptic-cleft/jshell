//print current process info
ProcessHandle p = ProcessHandle.current();
ProcessHandle.Info i = p.info();
System.out.println(i)

//print process commands of all running processes
//ProcessHandle.allProcesses().map(ProcessHandle::info).map(a -> a.command()).filter(x -> x.isPresent()).forEach(b -> System.out.println(b.get()))
ProcessHandle.allProcesses().forEach(b -> b.info().command().ifPresent(System.out::println))

//destroy a specific process, knowing its pid
//Optional<ProcessHandle> opP = ProcessHandle.of(14303);
//opP.get().destroy()