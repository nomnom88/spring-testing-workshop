# Spring Testing Workshop - Workbook


## Exercise 1: Spies

Open: nl.sourcelabs.workshop.testing.unit.spy.ServiceTest

Remove
```java
    @Disabled
```
from the test class.

Let's initialise our spy.

A spy is a real instance of a class, wrapped in a layer of code that 'spies' on interaction.

Replace 
```java
//BEFORE-EACH
```

with:
```java
@BeforeEach
    public void spyInit() {
        mathUtil = Mockito.spy(new MathUtil());
        sut = new Service(mathUtil);
    }
```
Notice, we are making a real instance of MathUtil by calling the constructor.
We are then passing that instance to Mockito which wraps it in a Spy layer and gives 
it back to us.
Finally we inject that spy into our real instance of Service through its constructor.

Run (or preferably debug and step-through) the test.

### And now with Annotations

Delete your BeforeEach method.

Add
```java
@Spy
```
To our MathUtil field.

And add
```java
@InjectMocks
```
to our SUT. Yeah it says Inject**MOCKS** and we're inject a spy;

Re-run the test.


### Spies are partial mocks
Spies will call the real method unless told otherwise.

Let's break our test by overriding the real doVeryVeryComplicatedCalculation method with a mocked response.

Just before we call 
```java
        final int result = sut.add(a, b);
```
Add the following code
```java
Mockito.doReturn(2).when(mathUtil).doVeryVeryComplicatedCalculation(a, b);
```

You can also use: 
```java
when(mathUtil.doVeryVeryComplicatedCalculation(a, b)).thenReturn(2);
```
but that will introduce a real method call. 
```java
mathUtil.doVeryVeryComplicatedCalculation(a, b)
```
Is called in that code and that's not my intention, I only want to stub behaviour and not run it.

Finally run the test and see how we're no longer getting 735 back but 2.