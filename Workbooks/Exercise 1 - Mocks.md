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

> @Disabled: Turns off a test.

Replace :
```java
    @Test
    @Disabled
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
To the class declaration. This allows Mockito to scan our test class for Mockito annotations and take necessary actions.

Add
```java
@Mock
``` 
to the two fields we mocked by calling Mockito.mock() in **A2BMapperWithEverythingMockedWithoutExtensionTest**

thus:
```java
@Mock
private A input;

@Mock
private AgeLookupService ageLookupService;
```

If we were to start the test now then Mockito would effectively do the following:
```java
private A input = Mockito.mock(A.class);

private AgeLookupService ageLookupService = Mockito.mock(AgeLookupService.class );
```

Next we can tell Mockito to construct a **real** instance of our SUT but to inject our Mocks as dependencies.

Add this annotation to our SUT:
```java
    @InjectMocks
    private A2BMapper sut;
```

Mockito will look at the constructor of A2BMapper:
```java
public A2BMapper(final AgeLookupService ageLookupService) {
        this.ageLookupService = ageLookupService;
    }
```
and see that we need an AgeLookupService parameter. 
Mockito then looks at which Mocks it has created with @Mock.
We have two: a mocked 'A' and a mocked 'AgeLookupService'.

Bingo.

Mockito will then _effectively_ run the following during test execution:
```java
    private A2BMapper sut = new A2BMapper(ageLookupService);
```

The mocked instance of 'A' is just left alone during this _InjectMocks_ process.

You can probably also start to see what the limitations of Mockito are. It's doing it's best
guess. It sees we need an AgeLookupService parameter sees we have one lying around and just plonks it into the constructor.

But what if we have two mocked instances of AgeLookupService in our test? Then Mockito will just
drunkenly take a stab in the dark and pick one of them. What if the constructor contains two AgeLookupService's ? Then mockito will just reuse whichever mock it finds twice.

When things are not **100% unambiguous** for Mockito then it's best to do things **programmatically**.


Finally remove @Disabled and run the test.