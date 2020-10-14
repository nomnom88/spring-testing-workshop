# Spring Testing Workshop - Workbook


## Exercise 1: Captors

If we look in our Captor package we can see our Service uses a Client instance as a dependency.

If we want to know the value passed to it we can simply use a Mock or a Spy and an argument Captor.

In our case we want to capture the _Request_ given
to the client so that we can make assertions on its state.
```java
public void sendRequest(final String message) {

        final String header = makeHeader();

        final Request input = new Request(header, message);

        client.sendRequest(input);

    }
```

Open the ServiceTest in the captor package.

Create the Mockitor Captor by replacing
```java
//CAPTOR
``` 
with
```java
        final ArgumentCaptor<Request> requestArgumentCaptor = ArgumentCaptor.forClass(Request.class);

```
Our captor is like a little holder that can retroactively capture arguments passed to mocks and spies.
Since our Client is a Mock we can also do this.

Replace

```java
// CAPTURE AND ASSERTIONS
```
With:
```java
 verify(client).sendRequest(requestArgumentCaptor.capture());
```
After this call we can get access to the parameter that was passed to the mock and make assertions.

Add the following:

```java
        final Request sentRequest = requestArgumentCaptor.getValue();

        assertThat(sentRequest).isNotNull();
        assertThat(sentRequest.getMessage()).isEqualTo(message);
        assertThat(sentRequest.getHeader()).startsWith(expectedPrefix);

        final String headerWithoutPrefix = sentRequest.getHeader()
                .replace(expectedPrefix, "");

        assertThatCode(() -> DateTimeFormatter.ISO_DATE.parse(headerWithoutPrefix)).doesNotThrowAnyException();
```

Now we can check that the Request instance being sent to Client has been correctly created.