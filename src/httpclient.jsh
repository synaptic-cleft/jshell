//make sure you have added the incubator module to your module path
/env --add-modules jdk.incubator.httpclient

String localUrl = "https://localhost:8080/my-favourite-path/keepalive"

import jdk.incubator.http.*;
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder(URI.create(localUrl)).GET().build();
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
System.out.println(response.statusCode())