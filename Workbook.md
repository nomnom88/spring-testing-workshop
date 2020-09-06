# Spring Testing Workshop - Workbook


## Exercise 1: Mocks

### 1. Open: A2BMapperWithEverythingMockedWithoutExtensionTest


### 2. Initialise our mocks

Since we only have 1 @Test method we could initialise our mocks at the start of the @Test method.

Instead of that we will initialise them in a _BeforeEach_ method.
The reason for this will become clear when we replace this method with the JUnit Mockito Extension
and related annotations.

Replace
  
```java
//BEFORE-EACH
```
  
with: 
```java
    @BeforeEach
    public void doWhatTheMockitoRunnerDoesForUs() {
    }
```

>@BeforeEach is used to signal that the annotated method should be executed before each @Test method in the current test class.
> @BeforeEach methods must have a void return type, must not be private, and must not be static. 

Next, let's fill it in:

```java
    @BeforeEach
    public void doWhatTheMockitoRunnerDoesForUs() {
        input = Mockito.mock(A.class);
        ageLookupService = mock(AgeLookupService.class);
        sut = new A2BMapper(ageLookupService);
    }
```

Programatically create Mockito mock's by calling this static import
```java
        input = Mockito.mock(A.class);
```
This creates a mocked version of our POJO A class.

I prefer static imports, so this is what you'll see:
```java
        ageLookupService = mock(AgeLookupService.class);
```
Here we mock our dependency, the AgeLookupService, so that we can alter it's behave when our Subject Under Test (sut) interacts with it.

```java
        sut = new A2BMapper(ageLookupService);
```
Finally, we inject the dependency into our SUT.

### 3. Create the test method 

Let's now set up the actual test.

> @Ignore: Turns off a test.

Replace :
```java
    @Test
    @Ignore
    public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
    }
```

With the test variables set up:
```java
@Test
public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final String expectedFullName = firstName + " " + lastName;
        final Integer expectedAge = 123;
    }
```

A mockito mock will give back default values. Most of the time _null_.
We can override this behaviour by using various methods, the most common of which being
the _when()_ method.

Expand the test to include the following:
```java
@Test
public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final String expectedFullName = firstName + " " + lastName;
        final Integer expectedAge = 123;

        System.out.println("Lastname before mocking:" + input.getLastName());

        when(input.getFirstName()).thenReturn(firstName);
        when(input.getLastName()).thenReturn(lastName);
        when(ageLookupService.lookupAge(firstName, lastName)).thenReturn(expectedAge);

        System.out.println("Lastname after mocking:" + input.getLastName());
    }
```
And then run the test.

Look at the console and see how before mocking we get the default null but after mocking
we receive the value we told Mockito to return.

Now let's call our test method and assert our expected behaviour.

```java
@Test
public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final String expectedFullName = firstName + " " + lastName;
        final Integer expectedAge = 123;

        when(input.getFirstName()).thenReturn(firstName);
        when(input.getLastName()).thenReturn(lastName);
        when(ageLookupService.lookupAge(firstName, lastName)).thenReturn(expectedAge);

        final B output = sut.map(input);

        assertThat(output).isNotNull();
        assertThat(output.getFullName()).isEqualTo(expectedFullName);
        assertThat(output.getAge()).isEqualTo(expectedAge);
    }
```

### 4. Let Mockito initialise

Open: **A2BMapperWithEverythingMockedTest**

In order to use Mockito annotations to do our initialisation we need to extend Junit with Mockito.

Add
```java
@ExtendWith(MockitoExtension.class)
``` 
To the class declaration.
