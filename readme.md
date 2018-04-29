# JShell workshop

## Prerequisites
[Download Java 10](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html)

## Basic JShell commands
* /help

This is basically all you need. The help menu is really well done!
* /exit
* /list –all
* /help /l
* /save -history yourPath/customFileName.jsh
* /open yourPath/customFileName.jsh
* /history
* /env --add-modules moduleName
* /edit snippetId
* /set mode/feedback/editor

## Java features touched in this workshop
* ProcessHandle
* StackWalker
* HttpClient
* Serialization
* Local-variable Type Inference

Feel free to use the [JavaDocs](https://docs.oracle.com/javase/10/docs/api/overview-summary.html) in parallel

## Assignments
### ProcessHandle
**_Wouldn’t it be nice if Java had an easy way to check which processes are running on the system?_**

1. I could get my current process information
2. Or list all processes, map them and print forEach the command
3. Or maybe kill a process that is running elsewhere on the machine?

<details><summary>Solution</summary>
	
```java
ProcessHandle.
// tab completion
ProcessHandle.current().info()
// Shift tab v, to convert to variable declaration
ProcessHandle.allProcesses().map(ProcessHandle::info).map(a -> a.command()).filter(x -> x.isPresent()).forEach(b -> System.out.println(b.get()))
ProcessHandle.of($pid).get().destroy()
// check out possible shortcuts with /help shortcuts
```
</details>

See script [processhandle.jsh](../master/src/processhandle.jsh)


### StackWalker
**_Where am I? And if yes, how many?_**
1. How did you know what’s on the stack beneath you prior to Java9? 
2. Compare the code with the new StackWalker API and try to get a similar result as prior to Java9
3. Try to collect all classes involved in the stack.
Hint: When getting an instance you need to pass the option that class references will be retained

<details><summary>Solution</summary>
	
```java
// Prior to Java 9:
new Throwable().getStackTrace()
// Expensive (JVM eagerly captures the stack)
// No convenient methods for filtering, e.g. get class instance
// Not necessarily complete stacktrace as JVM can omit data
StackWalker.getInstance().forEach(System.out::println)
// similar result as with Throwable
StackWalker.getInstance(
	StackWalker.Option.RETAIN_CLASS_REFERENCE).forEach(
	s -> System.out.println(s.getClassName()))
```
</details>
	
See script [stackwalker.jsh](..
n/stackwalker.jsh)

### HttpClient
**_Wouldn’t it be dreamy if you could automatically test your locally running API with a simple script? No hastle with typing out curl. But using real Java with REST calls…_**

Well, you can in Java9. Just use the HttpClient. It’s still WIP, so you’ll find it in the incubator module.

<details><summary>Hints</summary><p>
You can add a module in a running JShell with /env --add-modules moduleName, also remember to import all packages you need)
Make a new client
Setup a GET request to the path where your local API is running (localhost:8080/your-favourite-path/keepalive)
Let the client send the request and save the response
Check the response for statusCode 200
Now let’s put this code in a script file. Check with your help command how you can SAVE and OPEN a self-made script
</p>
</details>

<details><summary>Solution</summary>
	
```java
/env --add-modules jdk.incubator.httpclient
import jdk.incubator.http.*
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder(URI.create(localUrl)).GET().build();
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
System.out.println(response.statusCode())
/save -history /your/absolute/path/customFileName.jsh
/open /your/absolute/path/customFileName.jsh
```
</details>
See script [httpclient.jsh](../master/src/httpclient.jsh)


### Customizing JShell
```java
/set feedback
// shows available modes and which one is used
/set mode dopamine normal –command
// makes a new mode called dopamine, based on normal mode
/set feedback dopamine
// use this new mode dopamine
/set prompt dopamine "\033[1;33mprompt %s> \033[0m" "\033[1;33m          ..."
/set format dopamine result "{name} ==> \033[1;32m{value}{post}" added,modified,replaced-primary-ok
/set editor -retain /usr/bin/vim
// adjust a mode as you like. Here: change the prompt/result colours/text
/set mode dopamine –retain
// remember the mode for the next sessions
```

See script [setMode.jsh](../master/src/setMode.jsh)

Check more possibilities on customization of your JShell in the [JShell tool JavaDocs](https://docs.oracle.com/javase/9/tools/jshell.htm#JSWOR-GUID-C337353B-074A-431C-993F-60C226163F00)

### Serialization
**_Wouldn’t it be dreamy if serialization were somewhat safer? That we could specify that only objects of some specific packages are allowed for deserialization? Or that we could set a maximum size?_**

1. Check out the Configuration of the ObjectInputFilter that now ships with Java 9! 
2. Open the code of script serialization.jsh in JShell and 
3. try to apply a filter for big cats. We only want to deserialize cute small cats with a maximum of 50 bytes.

<details><summary>Solution</summary>
	
```java
/open path/serialization.jsh
// Note: forward references are allowed in JShell!
// Set Jdk.serialFilter system property
// Directly in Java:
ObjectInputFilter.Config.setSerialFilter(ObjectInputFilter.Config.createFilter("maxbytes=50"));
// Example for blacklisting a package in the VM options in IntelliJ: 
// -Djdk.serialFilter=!com.example.demo.**
```
</details>

Good to know: can be used for Java 6/7/8 since backported

See script [serialization.jsh](../master/src/serialization.jsh)

### Serialization with local type inference
**_Wouldn’t it be dreamy if I wouldn't have to type all those stupidly long input/output class names?_**

Guess what, the Java 10 compiler can help you out here! Go ahead and try it out: Write the serialization script with var where possible.

See script [varNotation.jsh](../master/src/varNotation.jsh)
