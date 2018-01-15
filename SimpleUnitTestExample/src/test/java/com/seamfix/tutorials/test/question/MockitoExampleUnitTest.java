package com.seamfix.tutorials.test.question;

import com.seamfix.tutorials.test.question.Bar;
import com.seamfix.tutorials.test.question.Foo;
import static com.seamfix.tutorials.test.question.Foo.*;
import com.seamfix.tutorials.test.question.FooNotAvailable;
import com.seamfix.tutorials.test.question.InvalidQuestion;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MockitoExampleUnitTest {

    private Foo foo;
    private final static ValidQuestions VALID_QUESTIONS = new ValidQuestions();

    @BeforeMethod
    public void setupMock() {
        foo = mock(Foo.class);
        when(foo.greet()).thenReturn(HELLO_WORLD);
    }

    @Test
    public void fooGreets() {
        System.out.println("Foo greets: " + foo.greet());
        assertEquals(HELLO_WORLD, foo.greet());
    }

    @Test
    public void barGreets() {
        Bar bar = new Bar();
        assertEquals(HELLO_WORLD, bar.greet(foo));
    }

    @Test(expectedExceptions = FooNotAvailable.class)
    public void fooNotAvailable() {
        Bar bar = new Bar();
        System.out.println("Foo is down so will not respond");
        when(foo.greet()).thenReturn(null);
        System.out
                .println("Bar sends a question to Foo but since Foo is not avilable will throw FooNotAvailable");
        bar.question(foo, "Hello Foo");
    }

    @Test
    public void barQuestionsFoo() {
        Bar bar = new Bar();
        System.out
                .println("Bar asks Foo 'Any new topics?', it should get a response");
        bar.question(foo, Foo.ANY_NEW_TOPICS);
        System.out.println("Verify that Foo has been asked the question");
        verify(foo, times(1)).question(Foo.ANY_NEW_TOPICS);
    }

    @Test
    public void filterInvalidQuestions() {
        Bar bar = new Bar();
        String invalidQuestion = "Invalid question";
        bar.question(foo, invalidQuestion);
        System.out
                .println("Verify that question was never requested as Foo is unavailable");
        verify(foo, never()).question(invalidQuestion);
    }

    @Test(expectedExceptions = InvalidQuestion.class)
    public void throwExceptionIfInvalidQuestion() throws InvalidQuestion {
        Bar bar = new Bar();
        String invalidQuestion = "Invalid question";
        when(foo.questionStrictly("Invalid question")).thenThrow(
                new InvalidQuestion());
        bar.questionStrictly(foo, invalidQuestion);
    }

    @Test(expectedExceptions = InvalidQuestion.class)
    public void throwExceptionIfAnyInvalidQuestion() throws InvalidQuestion {
        Bar bar = new Bar();
        String invalidQuestion = "Invalid question";
        when(foo.questionStrictly(argThat(new InValidQuestions()))).thenThrow(
                new InvalidQuestion());
        bar.questionStrictly(foo, invalidQuestion);
    }

    @Test
    public void getTodaysTopicPrice() throws InvalidQuestion {
        Bar bar = new Bar();
        when(foo.questionStrictly(argThat(new ValidQuestions()))).thenAnswer(
                new FooAnswers());
        when(foo.getPrice(TOPIC_MOCKITO)).thenReturn(20);

        String answer = bar.questionStrictly(foo, ANY_NEW_TOPICS);
        System.out.println("Answer is: " + answer);
        assertEquals(answer, "Topic is Mockito, price is 20");
        verify(foo, times(1)).questionStrictly(WHAT_IS_TODAYS_TOPIC);
        verify(foo, times(1)).getPrice(TOPIC_MOCKITO);
        verify(foo, times(1)).bye();
    }

    @Test
    public void noNewTopic() throws InvalidQuestion {
        Bar bar = new Bar();
        when(foo.questionStrictly(ANY_NEW_TOPICS)).thenReturn(NO_NEW_TOPIC);

        String answer = bar.questionStrictly(foo, ANY_NEW_TOPICS);
        System.out.println("Answer is: " + answer);
        assertEquals(answer, NO_NEW_TOPIC);
        verify(foo, times(1)).bye();
        verify(foo, never()).questionStrictly(WHAT_IS_TODAYS_TOPIC);
        verify(foo, never()).getPrice(TOPIC_MOCKITO);
    }

    private static class InValidQuestions extends ArgumentMatcher<String> {

        @Override
        public boolean matches(Object argument) {
            return !VALID_QUESTIONS.matches(argument);
        }
    }

    private static class ValidQuestions extends ArgumentMatcher<String> {

        @Override
        public boolean matches(Object argument) {
            return argument.equals(ANY_NEW_TOPICS)
                    || argument.equals(WHAT_IS_TODAYS_TOPIC);
        }
    }

    private static class FooAnswers implements Answer<String> {

        public String answer(InvocationOnMock invocation) throws Throwable {
            String arg = (String) invocation.getArguments()[0];
            if (ANY_NEW_TOPICS.equals(arg)) {
                return YES_NEW_TOPICS_AVAILABLE;
            } else if (WHAT_IS_TODAYS_TOPIC.equals(arg)) {
                return TOPIC_MOCKITO;
            } else {
                throw new InvalidQuestion();
            }
        }
    }

}
